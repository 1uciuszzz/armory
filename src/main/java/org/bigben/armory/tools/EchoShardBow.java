package org.bigben.armory.tools;

import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EchoShardBow implements Listener {
  private final NamespacedKey bowKey;
  private final Set<UUID> customArrows = new HashSet<>();

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
    arrow.setVelocity(player.getLocation().getDirection().multiply(2));
    arrow.setShooter(player);
    arrow.setPierceLevel(10);
    arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
    UUID arrowId = arrow.getUniqueId();
    customArrows.add(arrowId);
  }

  @EventHandler
  public void onArrowDamage(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Arrow arrow && customArrows.contains(arrow.getUniqueId())) {
      event.setDamage(100.0);
    }
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isCustomBow(event.getItem())) {
      event.setCancelled(true); // 不损坏
    }
  }

  @EventHandler
  public void onArrowHit(ProjectileHitEvent event) {
    if (event.getEntity() instanceof Arrow arrow) {
      customArrows.remove(arrow.getUniqueId());
    }
  }

  public NamespacedKey getKey() {
    return bowKey;
  }
}
