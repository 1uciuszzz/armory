package org.bigben.armory.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class LuckyPickaxe {
  private final NamespacedKey key;

  public LuckyPickaxe(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben008");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      // 添加附魔
      meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true); // 时运 III
      meta.setUnbreakable(true);
      meta.setDisplayName("§b§l时运镐子");

      // 存储唯一标识
      meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerShape() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape("DDD", " R ", " R ");
    recipe.setIngredient('D', Material.EMERALD);
    recipe.setIngredient('R', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
