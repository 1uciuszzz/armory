package org.bigben.armory.leegings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ModelF {
  public ItemStack load() {
    ItemStack modelF = new ItemStack(Material.CHAINMAIL_LEGGINGS);
    ItemMeta modelFMeta = modelF.getItemMeta();
    modelFMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
    modelFMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
    modelFMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
    modelFMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    modelFMeta.setUnbreakable(true);
    modelFMeta.setDisplayName("§c§l泰克护腿");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model F");
    modelFMeta.setLore(loresList);
    AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 4,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
    modelFMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    modelF.setItemMeta(modelFMeta);
    return modelF;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelF) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelF);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.DIAMOND_LEGGINGS);
    recipe.setIngredient('R', Material.DIAMOND);
    return recipe;
  }
}
