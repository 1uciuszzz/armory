package org.bigben.armory.bow;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class ModelI {
  public ItemStack load() {
    ItemStack modelI = new ItemStack(Material.BOW);
    ItemMeta modelIMeta = modelI.getItemMeta();
    modelIMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
    modelIMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
    modelIMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    modelIMeta.setUnbreakable(true);
    modelIMeta.setDisplayName("§c§lMagic Bow");
    List<String> loresList = new ArrayList<String>();
    loresList.add("Model I");
    modelIMeta.setLore(loresList);
    AttributeModifier attackDamageModifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8,
        AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
    modelIMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
    modelI.setItemMeta(modelIMeta);
    return modelI;
  }

  public ShapedRecipe registerShape(NamespacedKey recipeKey, ItemStack modelI) {
    ShapedRecipe recipe = new ShapedRecipe(recipeKey, modelI);
    recipe.shape("CBN", "CAB", "CBN");
    recipe.setIngredient('N', Material.AIR);
    recipe.setIngredient('A', Material.NETHER_STAR);
    recipe.setIngredient('B', Material.DIAMOND);
    recipe.setIngredient('C', Material.STRING);
    return recipe;
  }
}
