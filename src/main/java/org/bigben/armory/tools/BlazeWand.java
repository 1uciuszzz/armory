package org.bigben.armory.tools;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class BlazeWand implements Listener {

  // 用于识别烈焰权杖与它发射的投射物的 key
  private final NamespacedKey blazeWandKey;
  private final NamespacedKey blazeArrowKey;
  private final NamespacedKey blazeFireballKey;

  public BlazeWand(JavaPlugin plugin) {
    this.blazeWandKey = new NamespacedKey(plugin, "blaze_wand");
    this.blazeArrowKey = new NamespacedKey(plugin, "blaze_wand_arrow");
    this.blazeFireballKey = new NamespacedKey(plugin, "blaze_wand_fireball");
  }

  /**
   * 创建烈焰权杖物品
   */
  public ItemStack createWand() {
    ItemStack wand = new ItemStack(Material.BLAZE_ROD);
    ItemMeta meta = wand.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§c§l烈焰权杖");
      meta.setUnbreakable(true);
      meta.getPersistentDataContainer().set(blazeWandKey, PersistentDataType.BYTE, (byte) 1);
      wand.setItemMeta(meta);
    }
    return wand;
  }

  /**
   * 注册配方
   */
  public ShapedRecipe registerRecipe() {
    ItemStack wand = createWand();
    ShapedRecipe recipe = new ShapedRecipe(blazeWandKey, wand);
    recipe.shape(" F ", " R ", " R ");
    recipe.setIngredient('F', Material.FIRE_CHARGE);
    recipe.setIngredient('R', Material.BLAZE_ROD);
    return recipe;
  }

  private boolean isBlazeWand(ItemStack item) {
    if (item == null || item.getType() != Material.BLAZE_ROD)
      return false;
    ItemMeta meta = item.getItemMeta();
    return meta != null && meta.getPersistentDataContainer().has(blazeWandKey, PersistentDataType.BYTE);
  }

  /**
   * 禁止烈焰权杖损坏
   */
  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isBlazeWand(event.getItem())) {
      event.setCancelled(true);
    }
  }

  /**
   * 玩家使用烈焰权杖
   */
  @EventHandler
  public void onUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (!isBlazeWand(item))
      return;

    Action action = event.getAction();
    event.setCancelled(true); // 统一取消默认行为

    switch (action) {
      case LEFT_CLICK_AIR:
      case LEFT_CLICK_BLOCK: {
        // 发射带雷电效果的箭
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setVelocity(player.getLocation().getDirection().multiply(2));
        arrow.setShooter(player);
        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        arrow.getPersistentDataContainer().set(blazeArrowKey, PersistentDataType.BYTE, (byte) 1);
        break;
      }

      case RIGHT_CLICK_AIR:
      case RIGHT_CLICK_BLOCK: {
        // 发射带爆炸效果的火焰弹
        SmallFireball fireball = player.launchProjectile(SmallFireball.class);
        fireball.setVelocity(player.getLocation().getDirection().multiply(2));
        fireball.setShooter(player);
        fireball.getPersistentDataContainer().set(blazeFireballKey, PersistentDataType.BYTE, (byte) 1);
        break;
      }

      default:
        break; // 其他操作不处理
    }
  }

  /**
   * 投射物命中处理：雷击 / 爆炸
   */
  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    PersistentDataContainer container = projectile.getPersistentDataContainer();
    Entity hitEntity = event.getHitEntity();

    // 命中目标的位置
    Location hitLocation = (hitEntity != null) ? hitEntity.getLocation() : projectile.getLocation();
    World world = hitLocation.getWorld();
    if (world == null)
      return;

    if (container.has(blazeArrowKey, PersistentDataType.BYTE)) {
      world.strikeLightning(hitLocation); // 召唤雷电
    }

    if (container.has(blazeFireballKey, PersistentDataType.BYTE)) {
      world.createExplosion(hitLocation, 1.0f, false, false); // 小型爆炸
    }
  }
}
