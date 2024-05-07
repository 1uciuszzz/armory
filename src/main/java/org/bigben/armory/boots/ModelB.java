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
    modelBMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    modelBMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
    modelBMeta.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
    modelBMeta.setDisplayName("§c§l五速鞋");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model B");
    modelBMeta.setLore(loresList);
    AttributeModifier movementSpeedModifier = new AttributeModifier(UUID.randomUUID(), "attribute.movementSpeed", 0.05,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
    modelBMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, movementSpeedModifier);
    AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "attribute.armor", 4,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
    modelBMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    modelB.setItemMeta(modelBMeta);
    return modelB;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelB) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelB);
    recipe.shape("FFF", "FDF", "FFF");
    recipe.setIngredient('D', Material.DIAMOND_BOOTS);
    recipe.setIngredient('F', Material.DIAMOND);
    return recipe;
  }
}
