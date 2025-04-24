package org.bigben.armory.tools;

import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EchoShardBow implements Listener {
  private final NamespacedKey bowKey;

  public EchoShardBow(JavaPlugin plugin) {
    this.bowKey = new NamespacedKey(plugin, "EchoShardBow");
  }

  public ItemStack createBowItem() {
    ItemStack item = new ItemStack(Material.ECHO_SHARD);
    ItemMeta meta = item.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§b§l回响·箭");
      meta.setUnbreakable(true);
      meta.getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 1);
      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack result = createBowItem();
    ShapedRecipe recipe = new ShapedRecipe(bowKey, result);
    recipe.shape("EEE", "EEE", "EEE");
    recipe.setIngredient('E', Material.ECHO_SHARD);
    return recipe;
  }

  private boolean isCustomBow(ItemStack item) {
    if (item == null || item.getType() != Material.ECHO_SHARD)
      return false;
    ItemMeta meta = item.getItemMeta();
    return meta != null && meta.getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE);
  }

  @EventHandler
  public void onRightClick(PlayerInteractEvent event) {
    if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
      return;
    }

    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();

    if (!isCustomBow(item))
      return;

    event.setCancelled(true);

    Arrow arrow = player.launchProjectile(Arrow.class);
    arrow.setVelocity(player.getLocation().getDirection().multiply(4));
    arrow.setShooter(player);
    arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
    arrow.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1), false);
    arrow.addCustomEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 10), false);
    arrow.getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 1);
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isCustomBow(event.getItem())) {
      event.setCancelled(true); // 不损坏
    }
  }

  @EventHandler
  public void onArrowHitDamage(EntityDamageByEntityEvent event) {

    if (!(event.getDamager() instanceof Arrow arrow))
      return;
    if (!(arrow.getShooter() instanceof Player))
      return;
    if (!(event.getEntity() instanceof LivingEntity target))
      return;

    if (!arrow.getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE))
      return;

    double health = target.getHealth();
    if (health > 1.0) {
      double damage = health - 1.0;
      event.setDamage(Math.max(damage, 0.1)); // 最小设置0.1防止一些奇怪的浮点问题
    } else {
      event.setDamage(0.0); // 保证不被杀死
    }

    // 打印日志
    Player shooter = (Player) arrow.getShooter();
    shooter.sendMessage(ChatColor.GOLD + "你用箭矢命中了 " +
        ChatColor.YELLOW + target.getName() +
        ChatColor.GOLD + "，造成了 " +
        ChatColor.RED + String.format("%.1f", event.getDamage()) + " 点伤害！");
  }

  @EventHandler
  public void onMeleeAttack(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      if (isCustomBow(item)) {
        event.setCancelled(true);
      }
    }
  }

  public NamespacedKey getKey() {
    return bowKey;
  }
}
