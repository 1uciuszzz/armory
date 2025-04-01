package org.bigben.armory.foods;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Ben004 {
  private final NamespacedKey key;

  public Ben004(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben004");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.ROTTEN_FLESH); // 物品外形为腐肉
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      meta.setDisplayName("§6黄金薯片");
      meta.setCustomModelData(1001);

      // 设置物品描述
      List<String> lore = new ArrayList<>();
      lore.add("§7一口下去，神清气爽！");
      meta.setLore(lore);

      // **核心：存储唯一标识**
      PersistentDataContainer data = meta.getPersistentDataContainer();
      data.set(key, PersistentDataType.BYTE, (byte) 1);

      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerShape() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape(" G ", " P ", "   ");
    recipe.setIngredient('G', Material.GOLD_NUGGET);
    recipe.setIngredient('P', Material.BAKED_POTATO);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
