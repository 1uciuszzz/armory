package org.bigben.armory.axes;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben002Listener implements Listener {
  private final NamespacedKey modelHKey;

  public Ben002Listener(JavaPlugin plugin) {
    this.modelHKey = new NamespacedKey(plugin, "Ben002");
  }

  @EventHandler
  public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
    // 确保攻击者是玩家
    if (!(event.getDamager() instanceof Player player)) {
      return;
    }

    // 获取玩家手中的武器
    ItemStack weapon = player.getInventory().getItemInMainHand();
    if (!isModelH(weapon)) {
      return;
    }

    // 目标实体是否是 Creeper 或 Pig
    Entity target = event.getEntity();
    if (target instanceof Creeper || target instanceof Pig) {
      World world = target.getWorld();
      world.strikeLightning(target.getLocation());
    }
  }

  private boolean isModelH(ItemStack item) {
    if (item == null || !item.hasItemMeta()) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(modelHKey, PersistentDataType.BYTE);
  }
}
