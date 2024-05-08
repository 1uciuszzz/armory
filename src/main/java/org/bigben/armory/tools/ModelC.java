package org.bigben.armory.tools;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ModelC {
  public ItemStack load() {
    ItemStack modelC = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta modelCMeta = modelC.getItemMeta();
    modelCMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
    modelCMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
    modelCMeta.setUnbreakable(true);
    modelCMeta.setDisplayName("§c§lMagic Pickaxe");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model C");
    modelCMeta.setLore(loresList);
    modelC.setItemMeta(modelCMeta);
    return modelC;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelC) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelC);
    recipe.shape("DDD", "NRN", "NRN");
    recipe.setIngredient('D', Material.DIAMOND_PICKAXE);
    recipe.setIngredient('R', Material.STICK);
    recipe.setIngredient('N', Material.AIR);
    return recipe;
  }
}
