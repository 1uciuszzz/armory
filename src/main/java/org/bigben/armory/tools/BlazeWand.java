package org.bigben.armory.tools;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlazeWand implements Listener {
  private final NamespacedKey wandKey;
  private final Set<UUID> firedArrows = new HashSet<>();
  private final Set<UUID> firedFireballs = new HashSet<>();

  public BlazeWand(JavaPlugin plugin) {
    this.wandKey = new NamespacedKey(plugin, "BlazeWand");
  }

  public ItemStack createWand() {
    ItemStack wand = new ItemStack(Material.BLAZE_ROD);
    ItemMeta meta = wand.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§c§l烈焰权杖");
      meta.setUnbreakable(true);
      meta.getPersistentDataContainer().set(wandKey, PersistentDataType.BYTE, (byte) 1);
      wand.setItemMeta(meta);
    }
    return wand;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack wand = createWand();
    ShapedRecipe recipe = new ShapedRecipe(wandKey, wand);
    recipe.shape(" F ", " R ", " R ");
    recipe.setIngredient('F', Material.FIRE_CHARGE);
    recipe.setIngredient('R', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getWandKey() {
    return wandKey;
  }

  private boolean isBlazeWand(ItemStack item) {
    if (item == null || item.getType() != Material.BLAZE_ROD)
      return false;
    ItemMeta meta = item.getItemMeta();
    return meta != null && meta.getPersistentDataContainer().has(wandKey, PersistentDataType.BYTE);
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isBlazeWand(event.getItem())) {
      event.setCancelled(true); // 不损坏
    }
  }

  @EventHandler
  public void onUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();

    if (!isBlazeWand(item))
      return;

    event.setCancelled(true);

    if (event.getAction().name().contains("LEFT")) {
      // 发射箭矢
      Arrow arrow = player.launchProjectile(Arrow.class);
      arrow.setVelocity(player.getLocation().getDirection().multiply(2));
      arrow.setShooter(player);
      arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
      firedArrows.add(arrow.getUniqueId());
    } else if (event.getAction().name().contains("RIGHT")) {
      // 发射火焰弹
      SmallFireball fireball = player.launchProjectile(SmallFireball.class);
      fireball.setVelocity(player.getLocation().getDirection().multiply(1.5));
      fireball.setShooter(player);
      firedFireballs.add(fireball.getUniqueId());
    }
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    UUID uuid = projectile.getUniqueId();

    // 处理箭矢命中
    if (projectile instanceof Arrow && firedArrows.contains(uuid)) {
      Entity hitEntity = event.getHitEntity();
      if (hitEntity != null) {
        // 命中实体时触发雷电
        Location loc = hitEntity.getLocation();
        loc.getWorld().strikeLightning(loc); // 召唤雷电
      }
      firedArrows.remove(uuid); // 清除箭矢的 UUID
    }

    // 处理火焰弹命中
    if (projectile instanceof SmallFireball && firedFireballs.contains(uuid)) {
      Entity hitEntity = event.getHitEntity();
      if (hitEntity != null) {
        // 命中实体时触发爆炸
        Location loc = hitEntity.getLocation();
        loc.getWorld().createExplosion(loc, 2.0f); // 小型爆炸
      }
      firedFireballs.remove(uuid); // 清除火焰弹的 UUID
    }
  }
}
