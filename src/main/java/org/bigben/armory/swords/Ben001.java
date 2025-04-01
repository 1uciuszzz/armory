package org.bigben.armory.swords;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Ben001 {
  private final NamespacedKey key;

  public Ben001(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben001");
  }

  public ItemStack createItem() {
    ItemStack modelA = new ItemStack(Material.GOLDEN_SWORD);
    ItemMeta modelAMeta = modelA.getItemMeta();

    if (modelAMeta != null) {
      modelAMeta.setUnbreakable(true);
      modelAMeta.setDisplayName("§c§l偷子剑");

      // 存储唯一标识
      modelAMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

      AttributeModifier attackDamageModifier = new AttributeModifier(
          UUID.randomUUID(), "generic.attackDamage", 10,
          AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
      modelAMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);

      AttributeModifier attackSpeedModifier = new AttributeModifier(
          UUID.randomUUID(), "generic.attackSpeed", 1,
          AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
      modelAMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);

      modelA.setItemMeta(modelAMeta);
    }
    return modelA;
  }

  public ShapedRecipe registerShape() {
    ItemStack modelA = createItem();
    ShapedRecipe recipe = new ShapedRecipe(key, modelA);
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.DIAMOND_SWORD);
    recipe.setIngredient('R', Material.DIAMOND);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
