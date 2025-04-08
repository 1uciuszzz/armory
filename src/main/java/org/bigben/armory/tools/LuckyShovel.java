package org.bigben.armory.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyShovel implements Listener {
  private final NamespacedKey shovelKey;
  private final Random random = new Random();
  private final List<Material> possibleDrops;

  public LuckyShovel(JavaPlugin plugin) {
    this.shovelKey = new NamespacedKey(plugin, "LuckyShovel");

    // 初始化奖励列表，包含所有合适的Material
    this.possibleDrops = new ArrayList<>();
    for (Material material : Material.values()) {
      // 排除不适合作为掉落物的类型
      if (isValidDropMaterial(material)) {
        possibleDrops.add(material);
      }
    }

  }

  /**
   * 检查Material是否适合作为掉落物
   * 我们排除空气、不可获取的物品和一些特定类型
   */
  private boolean isValidDropMaterial(Material material) {
    // 排除空气和不可获取的物品
    if (material == Material.AIR || material == Material.CAVE_AIR || material == Material.VOID_AIR) {
      return false;
    }

    // 排除不是物品的材料（如方块类型）
    if (!material.isItem()) {
      return false;
    }

    // 排除一些特殊类型
    String name = material.name();

    // 排除命令方块等不应该掉落的物品
    if (name.contains("COMMAND") || name.contains("BARRIER") ||
        name.contains("STRUCTURE") || name.contains("JIGSAW") ||
        name.contains("LIGHT") || name.equals("DEBUG_STICK") ||
        name.contains("SPAWN_EGG")) {
      return false;
    }

    return true;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();

    // 检查是否使用幸运铲子
    if (!isLuckyShovel(item))
      return;

    // 只有当方块被破坏时才会生效
    Block block = event.getBlock();
    if (block == null)
      return;

    // 随机决定是否掉落物品（50%的概率）
    if (random.nextDouble() < 0.5) {
      // 从可能的掉落物中随机选择一个
      Material dropMaterial = possibleDrops.get(random.nextInt(possibleDrops.size()));
      ItemStack drop = new ItemStack(dropMaterial, 1);

      // 在破坏的方块位置掉落物品
      block.getWorld().dropItemNaturally(block.getLocation(), drop);

      // 通知玩家（可选）
      player.sendMessage("§a幸运铲子给了你一个 " + formatMaterialName(dropMaterial) + "！");
    }
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    // 防止铲子受到伤害
    if (isLuckyShovel(event.getItem())) {
      event.setCancelled(true);
    }
  }

  private boolean isLuckyShovel(ItemStack item) {
    if (item == null || item.getType() != Material.GOLDEN_SHOVEL)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null || !meta.getPersistentDataContainer().has(shovelKey, PersistentDataType.BYTE))
      return false;
    return meta.getPersistentDataContainer().get(shovelKey, PersistentDataType.BYTE) == (byte) 1;
  }

  private String formatMaterialName(Material material) {
    String name = material.toString().toLowerCase().replace('_', ' ');
    StringBuilder builder = new StringBuilder();
    boolean capitalizeNext = true;

    for (char c : name.toCharArray()) {
      if (capitalizeNext && Character.isLetter(c)) {
        builder.append(Character.toUpperCase(c));
        capitalizeNext = false;
      } else {
        builder.append(c);
        if (c == ' ') {
          capitalizeNext = true;
        }
      }
    }

    return builder.toString();
  }

  public ItemStack createLuckyShovel() {
    ItemStack shovel = new ItemStack(Material.GOLDEN_SHOVEL);
    ItemMeta meta = shovel.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§6幸运铲子");
      meta.getPersistentDataContainer().set(shovelKey, PersistentDataType.BYTE, (byte) 1);
      meta.setUnbreakable(true);
      shovel.setItemMeta(meta);
    }
    return shovel;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack shovel = createLuckyShovel();
    ShapedRecipe recipe = new ShapedRecipe(shovelKey, shovel);
    recipe.shape(" G ", " S ", " S ");
    recipe.setIngredient('G', Material.EMERALD);
    recipe.setIngredient('S', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getShovelKey() {
    return shovelKey;
  }
}