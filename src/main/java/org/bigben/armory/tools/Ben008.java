package org.bigben.armory.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Ben008 {
  private final NamespacedKey key;

  public Ben008(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben008");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      // 添加附魔
      meta.addEnchant(Enchantment.DIG_SPEED, 5, true); // 效率 V
      meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true); // 时运 III
      meta.addEnchant(Enchantment.MENDING, 1, true); // 经验修补
      meta.setUnbreakable(true);
      meta.setDisplayName("§b§lMagic Pickaxe");

      // 存储唯一标识
      meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

      // 设置描述
      meta.setLore(List.of(
          "§7强化型钻石镐",
          "§e更快的挖掘速度与更多的矿物收益"));

      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerShape() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape(" D ", " R ", " R ");
    recipe.setIngredient('D', Material.DIAMOND_PICKAXE);
    recipe.setIngredient('R', Material.BLAZE_ROD); // 使用烈焰棒象征强化工具
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
