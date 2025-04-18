package org.bigben.armory.tools;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
      firedArrows.add(arrow.getUniqueId());
      player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1f, 1f);
    } else if (event.getAction().name().contains("RIGHT")) {
      // 发射火焰弹
      SmallFireball fireball = player.launchProjectile(SmallFireball.class);
      fireball.setVelocity(player.getLocation().getDirection().multiply(1.5));
      fireball.setShooter(player);
      firedFireballs.add(fireball.getUniqueId());
      player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1f, 1f);
    }
  }

  @EventHandler
  public void onMeleeAttack(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      if (isBlazeWand(item)) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onArrowHit(EntityDamageByEntityEvent event) {
    Entity damager = event.getDamager();
    if (damager instanceof Arrow && firedArrows.contains(damager.getUniqueId())) {
      Location loc = event.getEntity().getLocation();
      loc.getWorld().strikeLightning(loc); // 召唤雷电
      firedArrows.remove(damager.getUniqueId());
    }

    if (damager instanceof SmallFireball && firedFireballs.contains(damager.getUniqueId())) {
      Location loc = event.getEntity().getLocation();
      loc.getWorld().createExplosion(loc, 2.0f); // 小型爆炸
      firedFireballs.remove(damager.getUniqueId());
    }
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();
    UUID uuid = projectile.getUniqueId();

    if (projectile instanceof Arrow && firedArrows.contains(uuid)) {
      firedArrows.remove(uuid);
    }

    if (projectile instanceof SmallFireball && firedFireballs.contains(uuid)) {
      firedFireballs.remove(uuid);
    }
  }
}
