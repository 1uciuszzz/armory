package org.bigben.armory.swords;

import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModelAListener implements Listener {

  @EventHandler
  public void onDamageForBlooding(EntityDamageByEntityEvent e) {
    EntityType targetType = e.getEntityType();
    Entity damager = e.getDamager();
    Player player = damager instanceof Player ? (Player) damager : null;
    if (player == null) {
      return;
    }
    if (targetType.equals(EntityType.PLAYER)) {
      return;
    }
    ItemStack modelA = player.getInventory().getItemInMainHand();
    ItemMeta metaData = modelA.getItemMeta();
    if (metaData == null) {
      return;
    }
    List<String> loresList = metaData.getLore();
    if (loresList == null || loresList.size() == 0) {
      return;
    }
    if (!loresList.get(0).equals("Model A")) {
      return;
    }
    double damage = e.getFinalDamage();
    double health = player.getHealth();
    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    double healAmount = Math.floor(damage * 0.5);
    player.setHealth(Math.min(health + healAmount, maxHealth));
  }
}
