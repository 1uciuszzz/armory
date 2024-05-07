
package org.bigben.armory.swords;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelG {

  public ItemStack load() {
    ItemStack modelG = new ItemStack(Material.NETHERITE_SWORD);
    ItemMeta modelGMeta = modelG.getItemMeta();
    modelGMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 1, true);
    modelGMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
    modelGMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
    modelGMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
    modelGMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
    modelGMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 1, true);
    modelGMeta.addEnchant(Enchantment.SWEEPING_EDGE, 1, true);
    modelGMeta.setUnbreakable(true);
    modelGMeta.setDisplayName("§c§l火剑");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model G");
    modelGMeta.setLore(loresList);
    AttributeModifier attackDamageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelGMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
    AttributeModifier attackSpeedModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelGMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
    modelG.setItemMeta(modelGMeta);
    return modelG;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelG) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelG);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.GOLDEN_SWORD);
    recipe.setIngredient('R', Material.REDSTONE);
    return recipe;
  }
}