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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Ben005 {
  private final NamespacedKey key;

  public Ben005(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben005");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      // 添加附魔
      meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 1, true);
      meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
      meta.addEnchant(Enchantment.SWEEPING_EDGE, 1, true);
      meta.setUnbreakable(true);
      meta.setDisplayName("§c§lFire Sword");

      // 存储唯一标识
      PersistentDataContainer data = meta.getPersistentDataContainer();
      data.set(key, PersistentDataType.BYTE, (byte) 1);

      // 增加属性
      meta.addAttributeModifier(
          Attribute.GENERIC_ATTACK_DAMAGE,
          new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8,
              AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

      meta.addAttributeModifier(
          Attribute.GENERIC_ATTACK_SPEED,
          new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1,
              AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerShape() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape("RRR", "RDR", "RRR");
    recipe.setIngredient('D', Material.NETHERITE_SWORD); // 由下界合金剑合成
    recipe.setIngredient('R', Material.BLAZE_POWDER); // 用烈焰粉强化火焰属性
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
