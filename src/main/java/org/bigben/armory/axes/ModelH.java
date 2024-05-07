package org.bigben.armory.axes;

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

public class ModelH {
  public ItemStack load() {
    ItemStack modelH = new ItemStack(Material.TRIDENT);
    ItemMeta modelHMeta = modelH.getItemMeta();
    modelHMeta.addEnchant(Enchantment.LOYALTY, 3, true);
    modelHMeta.setUnbreakable(true);
    modelHMeta.setDisplayName("§c§l暴风战戟");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model H");
    modelHMeta.setLore(loresList);
    AttributeModifier attackDamageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 12,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelHMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
    modelH.setItemMeta(modelHMeta);
    return modelH;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelH) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelH);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.IRON_AXE);
    recipe.setIngredient('R', Material.LAPIS_LAZULI);
    return recipe;
  }
}
