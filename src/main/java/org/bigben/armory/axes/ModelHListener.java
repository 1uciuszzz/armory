package org.bigben.armory.axes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ModelHListener implements Listener {
  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
    EntityType targetType = e.getEntityType();
    if (targetType.equals(EntityType.CREEPER) || targetType.equals(EntityType.PIG)) {
      Entity target = e.getEntity();
      Location targetLocation = target.getLocation();
      World world = target.getWorld();
      world.strikeLightningEffect(targetLocation);
    }
  }
}
