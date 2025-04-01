package org.bigben.armory.foods;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ben004Listener implements Listener {
  private final NamespacedKey key;

  public Ben004Listener(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben004");
  }

  @EventHandler
  public void onPlayerEat(PlayerItemConsumeEvent event) {
    if (event.getItem().getItemMeta() != null &&
        event.getItem().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {

      event.getPlayer().sendMessage("§e你吃下了黄金薯片，感觉精力充沛！");

      // 添加正向 Buff
      event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1)); // 速度 II，持续 10 秒
      event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 1)); // 生命恢复 II，持续 5 秒
    }
  }
}
