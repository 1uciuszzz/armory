package org.bigben.armory.foods;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ben007Listener implements Listener {
  private final NamespacedKey key;

  public Ben007Listener(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben007");
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();

    if (isModelX(item) && event.getAction().toString().contains("RIGHT_CLICK")) {
      event.setCancelled(true);

      double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      double newHealth = Math.min(player.getHealth() + 10, maxHealth);
      player.setHealth(newHealth);
      player.sendMessage("§a你使用了急救包，恢复了10点生命值！");

      // 给予短暂的再生效果
      player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));

      // 消耗物品
      item.setAmount(item.getAmount() - 1);
    }
  }

  private boolean isModelX(ItemStack item) {
    if (item == null || !item.hasItemMeta())
      return false;
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(key, PersistentDataType.BYTE);
  }
}
