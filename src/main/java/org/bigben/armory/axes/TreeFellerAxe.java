package org.bigben.armory.axes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class TreeFellerAxe implements Listener {
  private final NamespacedKey axeKey;

  public TreeFellerAxe(JavaPlugin plugin) {
    this.axeKey = new NamespacedKey(plugin, "TreeFellerAxe");
  }

  @EventHandler
  public void onTreeFellerUse(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
      return;
    Block block = event.getClickedBlock();
    if (block == null || !isLog(block.getType()))
      return;

    ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
    if (!isTreeFellerAxe(item))
      return;

    breakTree(block);
  }

  private boolean isLog(Material material) {
    return material.name().endsWith("_LOG");
  }

  private boolean isTreeFellerAxe(ItemStack item) {
    if (item == null || item.getType() != Material.GOLDEN_AXE)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null || !meta.getPersistentDataContainer().has(axeKey, PersistentDataType.BYTE))
      return false;
    return meta.getPersistentDataContainer().get(axeKey, PersistentDataType.BYTE) == (byte) 1;
  }

  private void breakTree(Block start) {
    Set<Block> visited = new HashSet<>();
    Stack<Block> stack = new Stack<>();
    stack.push(start);

    while (!stack.isEmpty()) {
      Block block = stack.pop();
      if (visited.contains(block) || !isLog(block.getType()))
        continue;
      visited.add(block);
      block.breakNaturally();

      for (Block neighbor : getNearbyLogs(block)) {
        if (!visited.contains(neighbor)) {
          stack.push(neighbor);
        }
      }
    }
  }

  private Set<Block> getNearbyLogs(Block block) {
    Set<Block> logs = new HashSet<>();
    int[][] directions = { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 }, { 0, -1, 0 }, { 0, 0, 1 }, { 0, 0, -1 } };

    for (int[] dir : directions) {
      Block relative = block.getRelative(dir[0], dir[1], dir[2]);
      if (isLog(relative.getType())) {
        logs.add(relative);
      }
    }
    return logs;
  }

  public ItemStack createTreeFellerAxe() {
    ItemStack axe = new ItemStack(Material.GOLDEN_AXE);
    ItemMeta meta = axe.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§6伐木斧头");
      meta.getPersistentDataContainer().set(axeKey, PersistentDataType.BYTE, (byte) 1);
      meta.setUnbreakable(true);
      axe.setItemMeta(meta);
    }
    return axe;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack axe = createTreeFellerAxe();
    ShapedRecipe recipe = new ShapedRecipe(axeKey, axe);
    recipe.shape(" LL", " LL", " L ");
    recipe.setIngredient('L', Material.GOLDEN_AXE);
    return recipe;
  }

  public NamespacedKey getAxeKey() {
    return axeKey;
  }
}
