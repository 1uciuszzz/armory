package org.bigben.armory.hoes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Ben006 {
  private final NamespacedKey key;

  public Ben006(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben006");
  }

  public ItemStack createItem() {
    ItemStack modelS = new ItemStack(Material.IRON_HOE);
    ItemMeta modelSMeta = modelS.getItemMeta();

    if (modelSMeta != null) {
      modelSMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
      modelSMeta.setUnbreakable(true);
      modelSMeta.setDisplayName("§a§l农夫镰刀");

      List<String> loreList = new ArrayList<>();
      loreList.add("§7右键点击收割作物");
      modelSMeta.setLore(loreList);

      modelSMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
      modelS.setItemMeta(modelSMeta);
    }
    return modelS;
  }

  public ShapedRecipe registerShape() {
    ItemStack modelS = createItem();
    ShapedRecipe recipe = new ShapedRecipe(key, modelS);
    recipe.shape(" WW", " SW", " S ");
    recipe.setIngredient('W', Material.WHEAT);
    recipe.setIngredient('S', Material.STICK);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
