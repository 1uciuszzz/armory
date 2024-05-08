package org.bigben.armory.bow;

import java.util.List;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

public class ModelIListener implements Listener {
  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
    EntityType targetType = e.getEntityType();
    if (targetType.equals(EntityType.PLAYER)) {
      return;
    }
    Entity target = e.getEntity();
    Entity damager = e.getDamager();
    if (!(damager instanceof Arrow)) {
      return;
    }
    Arrow arrow = (Arrow) damager;
    ProjectileSource shooter = arrow.getShooter();
    Player player = shooter instanceof Player ? (Player) shooter : null;
    if (player == null) {
      return;
    }
    ItemStack modelI = player.getInventory().getItemInMainHand();
    ItemMeta metaData = modelI.getItemMeta();
    if (metaData == null) {
      return;
    }
    List<String> loresList = metaData.getLore();
    if (loresList == null || loresList.size() == 0) {
      return;
    }
    if (!loresList.get(0).equals("Model I")) {
      return;
    }
    target.getWorld().createExplosion(target.getLocation(), 2.0F, false, false);
  }
}
