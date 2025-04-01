package org.bigben.armory.bow;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben003Listener implements Listener {
  private final NamespacedKey modelIKey;

  public Ben003Listener(JavaPlugin plugin) {
    this.modelIKey = new NamespacedKey(plugin, "Ben003");
  }

  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
    // 确保目标不是玩家
    if (event.getEntityType() == EntityType.PLAYER) {
      return;
    }

    // 确保攻击方式是箭
    if (!(event.getDamager() instanceof Arrow arrow)) {
      return;
    }

    // 检查射箭者是否是玩家
    ProjectileSource shooter = arrow.getShooter();
    if (!(shooter instanceof Player player)) {
      return;
    }

    // 获取玩家手中的弓并检查是否是 Model I
    ItemStack bow = player.getInventory().getItemInMainHand();
    if (!isModelI(bow)) {
      return;
    }

    // 在目标位置生成小型爆炸
    Entity target = event.getEntity();
    World world = target.getWorld();
    world.createExplosion(target.getLocation(), 2.0F, false, false);
  }

  private boolean isModelI(ItemStack item) {
    if (item == null || !item.hasItemMeta()) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(modelIKey, PersistentDataType.BYTE);
  }
}
