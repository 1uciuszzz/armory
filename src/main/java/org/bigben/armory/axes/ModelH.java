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
    ItemStack modelH = new ItemStack(Material.GOLDEN_AXE);
    ItemMeta modelHMeta = modelH.getItemMeta();
    modelHMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
    modelHMeta.setUnbreakable(true);
    modelHMeta.setDisplayName("§c§lThunder Axe");
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
    recipe.shape("BCN", "BAN", "NAN");
    recipe.setIngredient('N', Material.AIR);
    recipe.setIngredient('A', Material.STICK);
    recipe.setIngredient('B', Material.IRON_INGOT);
    recipe.setIngredient('C', Material.CONDUIT);
    return recipe;
  }
}
