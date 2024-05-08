package org.bigben.armory.axes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class ModelHListener implements Listener {
  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
    EntityType targetType = e.getEntityType();
    Entity damager = e.getDamager();
    Player player = damager instanceof Player ? (Player) damager : null;
    if (player == null) {
      return;
    }
    ItemStack modelH = player.getInventory().getItemInMainHand();
    ItemMeta metaData = modelH.getItemMeta();
    if (metaData == null) {
      return;
    }
    List<String> loresList = metaData.getLore();
    if (loresList == null || loresList.size() == 0) {
      return;
    }
    if (!loresList.get(0).equals("Model H")) {
      return;
    }
    if (targetType.equals(EntityType.CREEPER) || targetType.equals(EntityType.PIG)) {
      Entity target = e.getEntity();
      Location targetLocation = target.getLocation();
      World world = target.getWorld();
      world.strikeLightning(targetLocation);
    }
  }
}
