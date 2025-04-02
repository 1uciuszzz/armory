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
    Material cropType = block.getType();
    if (cropType == Material.WHEAT) {
      block.setType(Material.WHEAT);
    } else if (cropType == Material.CARROTS) {
      block.setType(Material.CARROTS);
    } else if (cropType == Material.POTATOES) {
      block.setType(Material.POTATOES);
    } else if (cropType == Material.BEETROOTS) {
      block.setType(Material.BEETROOTS);
    } else if (cropType == Material.MELON_STEM) {
      block.setType(Material.MELON_STEM);
    } else if (cropType == Material.PUMPKIN_STEM) {
      block.setType(Material.PUMPKIN_STEM);
    } else if (cropType == Material.NETHER_WART) {
      block.setType(Material.NETHER_WART);
    }
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
