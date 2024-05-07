package org.bigben.armory.chestplates;

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

public class ModelE {
  public ItemStack load() {
    ItemStack modelE = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
    ItemMeta modelEMeta = modelE.getItemMeta();
    modelEMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1, true);
    modelEMeta.addEnchant(Enchantment.PROTECTION_FIRE, 1, true);
    modelEMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
    modelEMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
    modelEMeta.addEnchant(Enchantment.THORNS, 1, true);
    modelEMeta.setUnbreakable(true);
    modelEMeta.setDisplayName("§c§l荆棘胸甲");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model E");
    modelEMeta.setLore(loresList);
    AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 8,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
    modelEMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    modelE.setItemMeta(modelEMeta);
    return modelE;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelE) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelE);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.DIAMOND_CHESTPLATE);
    recipe.setIngredient('R', Material.DIAMOND);
    return recipe;
  }
}
