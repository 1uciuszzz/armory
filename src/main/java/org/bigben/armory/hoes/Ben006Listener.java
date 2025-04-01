package org.bigben.armory.hoes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben006Listener implements Listener {
  private final NamespacedKey modelSKey;

  public Ben006Listener(JavaPlugin plugin) {
    this.modelSKey = new NamespacedKey(plugin, "Ben006");
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    Block block = event.getClickedBlock();
    if (block == null || (block.getType() != Material.WHEAT && block.getType() != Material.CARROTS
        && block.getType() != Material.POTATOES)) {
      return;
    }
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (!isModelS(item)) {
      return;
    }
    block.breakNaturally();
    block.setType(Material.WHEAT); // 重新种植（可改进为动态检测作物）
  }

  private boolean isModelS(ItemStack item) {
    if (item == null || !item.hasItemMeta()) {
      return false;
    }
    ItemMeta meta = item.getItemMeta();
    PersistentDataContainer data = meta.getPersistentDataContainer();
    return data.has(modelSKey, PersistentDataType.BYTE);
  }
}
