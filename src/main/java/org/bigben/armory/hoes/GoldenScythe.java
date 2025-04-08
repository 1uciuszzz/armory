package org.bigben.armory.hoes;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GoldenScythe implements Listener {
  private final NamespacedKey scytheKey;
  private final JavaPlugin plugin;

  // 支持的单格农作物和对应的种子
  private static final Map<Material, Material> cropsToSeeds = new HashMap<>();
  static {
    cropsToSeeds.put(Material.WHEAT, Material.WHEAT_SEEDS);
    cropsToSeeds.put(Material.CARROTS, Material.CARROT);
    cropsToSeeds.put(Material.POTATOES, Material.POTATO);
    cropsToSeeds.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
    cropsToSeeds.put(Material.NETHER_WART, Material.NETHER_WART);
  }

  // 多格可收割植物（不需要检查成熟度）
  private static final Set<Material> tallPlants = Set.of(
      Material.SUGAR_CANE,
      Material.MELON,
      Material.PUMPKIN);

  public GoldenScythe(JavaPlugin plugin) {
    this.plugin = plugin;
    this.scytheKey = new NamespacedKey(plugin, "GoldenScythe");
  }

  @EventHandler
  public void onRightClick(PlayerInteractEvent event) {
    if (event.getHand() != EquipmentSlot.HAND ||
        !(event.getAction() == Action.RIGHT_CLICK_BLOCK ||
            event.getAction() == Action.RIGHT_CLICK_AIR))
      return;

    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (!isGoldenScythe(item))
      return;

    Block center = event.getClickedBlock();
    if (center == null)
      return;

    event.setCancelled(true); // 防止误触

    // 如果是草地，则将其开垦成耕地
    if (center.getType() == Material.GRASS_BLOCK) {
      center.setType(Material.FARMLAND);
      player.sendMessage("§a你将草地转变为耕地！");
    }

    // 遍历3x3区域
    for (int dx = -1; dx <= 1; dx++) {
      for (int dz = -1; dz <= 1; dz++) {
        Block block = center.getRelative(dx, 0, dz);
        Material type = block.getType();

        if (cropsToSeeds.containsKey(type)) {
          handleCrop(block, player);
        } else if (tallPlants.contains(type)) {
          handleTallPlant(block);
        }
      }
    }

    // 粒子和音效反馈
    center.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, center.getLocation().add(0.5, 0.8, 0.5), 12, 1, 0.5, 1);
    center.getWorld().playSound(center.getLocation(), Sound.ITEM_HOE_TILL, 1f, 1.2f);
  }

  private void handleCrop(Block block, Player player) {
    Material type = block.getType();
    BlockData data = block.getBlockData();
    if (!(data instanceof Ageable ageable))
      return;
    if (ageable.getAge() < ageable.getMaximumAge())
      return;

    // 确保是耕地
    Block belowBlock = block.getRelative(BlockFace.DOWN);
    if (belowBlock.getType() != Material.FARMLAND) {
      player.sendMessage("§c这块地不是耕地，无法种植！");
      return;
    }

    block.breakNaturally(); // 模拟自然破坏

    // 重种种子
    Bukkit.getScheduler().runTaskLater(plugin, () -> {
      block.setType(type);
      BlockData newData = block.getBlockData();
      if (newData instanceof Ageable replanted) {
        replanted.setAge(0);
        block.setBlockData(replanted);
      }
    }, 2L);
  }

  private void handleTallPlant(Block block) {
    Block current = block;
    while (current.getType() == block.getType()) {
      current.breakNaturally();
      current = current.getRelative(BlockFace.UP);
    }
  }

  private boolean isGoldenScythe(ItemStack item) {
    if (item == null || item.getType() != Material.GOLDEN_HOE)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return false;
    return meta.getPersistentDataContainer().has(scytheKey, PersistentDataType.BYTE);
  }

  public ItemStack createGoldenScythe() {
    ItemStack hoe = new ItemStack(Material.GOLDEN_HOE);
    ItemMeta meta = hoe.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§e金镰刀");
      meta.getPersistentDataContainer().set(scytheKey, PersistentDataType.BYTE, (byte) 1);
      meta.setUnbreakable(true);
      hoe.setItemMeta(meta);
    }
    return hoe;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack hoe = createGoldenScythe();
    ShapedRecipe recipe = new ShapedRecipe(scytheKey, hoe);
    recipe.shape(" GG", " S ", " S ");
    recipe.setIngredient('G', Material.DIAMOND);
    recipe.setIngredient('S', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getScytheKey() {
    return scytheKey;
  }
}
