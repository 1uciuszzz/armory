package org.bigben.armory.swords;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben005Listener implements Listener {
  private final NamespacedKey modelGKey;

  public Ben005Listener(JavaPlugin plugin) {
    this.modelGKey = new NamespacedKey(plugin, "Ben005");
  }

  @EventHandler
  public void onFireSwordHit(EntityDamageByEntityEvent event) {
    // 确保攻击者是玩家
    if (!(event.getDamager() instanceof Player player)) {
      return;
    }

    // 获取武器
    ItemStack weapon = player.getInventory().getItemInMainHand();
    if (!isModelG(weapon)) {
      return;
    }

    // 确保目标是生物（不影响方块、盔甲架）
    Entity target = event.getEntity();
    if (target instanceof LivingEntity livingTarget && target.getType() != EntityType.PLAYER) {
      // 让目标燃烧 5 秒
      livingTarget.setFireTicks(100);
    }
  }

  private boolean isModelG(ItemStack item) {
    if (item == null || !item.hasItemMeta()) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(modelGKey, PersistentDataType.BYTE);
  }
}
