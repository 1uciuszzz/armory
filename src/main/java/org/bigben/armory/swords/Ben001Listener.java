package org.bigben.armory.swords;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben001Listener implements Listener {
  private final NamespacedKey modelAKey;

  public Ben001Listener(JavaPlugin plugin) {
    this.modelAKey = new NamespacedKey(plugin, "Ben001");
  }

  @EventHandler
  public void onDamageForBlooding(EntityDamageByEntityEvent event) {
    // 确保攻击者是玩家
    if (!(event.getDamager() instanceof Player player)) {
      return;
    }

    // 确保攻击目标不是玩家（防止PVP吸血）
    if (event.getEntityType() == EntityType.PLAYER) {
      return;
    }

    // 获取玩家手中的武器
    ItemStack weapon = player.getInventory().getItemInMainHand();
    if (!isModelA(weapon)) {
      return;
    }

    // 计算吸血量（30% 伤害）
    double damage = event.getFinalDamage();
    double healAmount = damage * 0.3;

    // 限制玩家生命值不能超出最大血量
    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    double newHealth = Math.min(player.getHealth() + healAmount, maxHealth);

    player.setHealth(newHealth);
  }

  private boolean isModelA(ItemStack item) {
    if (item == null || !item.hasItemMeta()) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(modelAKey, PersistentDataType.BYTE);
  }
}
