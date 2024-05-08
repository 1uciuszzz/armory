package org.bigben.armory.swords;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelA {

  public ItemStack load() {
    ItemStack modelA = new ItemStack(Material.GOLDEN_SWORD);
    ItemMeta modelAMeta = modelA.getItemMeta();
    modelAMeta.setUnbreakable(true);
    modelAMeta.setDisplayName("§c§l饮血剑");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model A");
    loresList.add("§e生命偷取+50%");
    modelAMeta.setLore(loresList);
    AttributeModifier attackDamageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 10,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelAMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
    AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelAMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
    modelA.setItemMeta(modelAMeta);
    return modelA;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelA) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelA);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.DIAMOND_SWORD);
    recipe.setIngredient('R', Material.DIAMOND);
    return recipe;
  }
}