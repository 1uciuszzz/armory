package org.bigben.armory.boots;

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

public class ModelB {
  public ItemStack load() {
    ItemStack modelB = new ItemStack(Material.CHAINMAIL_BOOTS);
    ItemMeta modelBMeta = modelB.getItemMeta();
    modelBMeta.setUnbreakable(true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
    modelBMeta.setDisplayName("§c§lMagic Boots");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model B");
    modelBMeta.setLore(loresList);
    AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "attribute.armor", 4,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
    modelBMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    modelB.setItemMeta(modelBMeta);
    return modelB;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelB) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelB);
    recipe.shape("FNF", "FNF", "NNN");
    recipe.setIngredient('N', Material.AIR);
    recipe.setIngredient('F', Material.DIAMOND_BOOTS);
    return recipe;
  }
}
