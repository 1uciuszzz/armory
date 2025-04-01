package org.bigben.armory.foods;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben007 {
  private final NamespacedKey key;

  public Ben007(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben007"); // 定义唯一标识
  }

  public ItemStack createItem() {
    ItemStack modelX = new ItemStack(Material.PAPER);
    ItemMeta modelXMeta = modelX.getItemMeta();

    if (modelXMeta != null) {
      modelXMeta.setDisplayName("§a§l急救包");
      List<String> loreList = new ArrayList<>();
      loreList.add("§7使用后恢复 §c10 §7生命值");
      modelXMeta.setLore(loreList);

      // 添加唯一标识
      modelXMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
      modelX.setItemMeta(modelXMeta);
    }
    return modelX;
  }

  public ShapedRecipe registerShape() {
    ItemStack modelX = createItem();
    ShapedRecipe recipe = new ShapedRecipe(key, modelX);
    recipe.shape(" P ", "GRG", " P ");
    recipe.setIngredient('P', Material.PAPER);
    recipe.setIngredient('G', Material.GOLDEN_APPLE);
    recipe.setIngredient('R', Material.REDSTONE);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
