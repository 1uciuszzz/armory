package org.bigben.armory.hoes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
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

import java.util.Set;

public class Ben006Listener implements Listener {
  private final NamespacedKey modelSKey;
  private final Set<Material> crops = Set.of(
      Material.WHEAT, Material.CARROTS, Material.POTATOES,
      Material.BEETROOTS, Material.NETHER_WART);

  public Ben006Listener(JavaPlugin plugin) {
    this.modelSKey = new NamespacedKey(plugin, "Ben006");
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    Block block = event.getClickedBlock();
    if (block == null || !crops.contains(block.getType())) {
      return;
    }

    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (!isModelS(item)) {
      return;
    }

    // 确保作物已成熟才收获
    if (isFullyGrown(block)) {
      Material cropType = block.getType();

      // 破坏作物并掉落物品
      block.breakNaturally();

      // 重新种植相同的作物
      block.setType(cropType);
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

  private boolean isFullyGrown(Block block) {
    if (block.getBlockData() instanceof Ageable ageable) {
      return ageable.getAge() == ageable.getMaximumAge();
    }
    return false;
  }
}
