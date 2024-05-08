package org.bigben.armory.helmets;

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

public class ModelD {
  public ItemStack load() {
    ItemStack modelD = new ItemStack(Material.CHAINMAIL_HELMET);
    ItemMeta modelDMeta = modelD.getItemMeta();
    modelDMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    modelDMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
    modelDMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
    modelDMeta.setUnbreakable(true);
    modelDMeta.setDisplayName("§c§lMagic Helmet");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model D");
    modelDMeta.setLore(loresList);
    AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 5,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
    modelDMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    modelD.setItemMeta(modelDMeta);
    return modelD;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelD) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelD);
    recipe.shape("DDD", "DND", "NNN");
    recipe.setIngredient('D', Material.DIAMOND_HELMET);
    recipe.setIngredient('N', Material.AIR);
    return recipe;
  }
}
