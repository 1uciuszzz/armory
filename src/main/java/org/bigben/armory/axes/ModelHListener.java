package org.bigben.armory.axes;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModelHListener implements Listener {
  @EventHandler
  public void onPlayerDamageEntity(ProjectileHitEvent e) {
    Projectile projectile = e.getEntity();
    if (projectile instanceof Trident) {
      ItemStack trident = ((Trident) projectile).getItem();
      ItemMeta meta = trident.getItemMeta();
      if (meta == null) {
        return;
      }
      List<String> lores = meta.getLore();
      if (lores == null || lores.isEmpty()) {
        return;
      }
      if (lores.get(0).equals("Model H")) {
        Entity entity = e.getHitEntity();
        if (entity == null) {
          return;
        }
        Location location = entity.getLocation();
        World world = entity.getWorld();
        world.strikeLightning(location);
      }
    }
  }
}
