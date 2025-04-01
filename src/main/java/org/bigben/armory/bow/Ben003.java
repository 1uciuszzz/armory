package org.bigben.armory.bow;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ben003 {
  private final NamespacedKey key;

  public Ben003(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben003");
  }

  public ItemStack createItem() {
    ItemStack modelI = new ItemStack(Material.BOW);
    ItemMeta modelIMeta = modelI.getItemMeta();

    if (modelIMeta != null) {
      modelIMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
      modelIMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
      modelIMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
      modelIMeta.setUnbreakable(true);
      modelIMeta.setDisplayName("§c§lMagic Bow");

      List<String> loreList = new ArrayList<>();
      loreList.add("§7神秘的魔法弓，带来毁灭之力！");
      modelIMeta.setLore(loreList);

      // 直接用 PersistentDataContainer 存自定义标识
      PersistentDataContainer data = modelIMeta.getPersistentDataContainer();
      data.set(key, PersistentDataType.BYTE, (byte) 1);

      AttributeModifier attackDamageModifier = new AttributeModifier(
          UUID.randomUUID(), "generic.attackDamage", 8,
          AttributeModifier.Operation.ADD_NUMBER,
          org.bukkit.inventory.EquipmentSlot.HAND);
      modelIMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);

      modelI.setItemMeta(modelIMeta);
    }
    return modelI;
  }

  public ShapedRecipe registerShape() {
    ItemStack modelI = createItem(); // 确保每次创建新物品
    ShapedRecipe recipe = new ShapedRecipe(key, modelI);
    recipe.shape("CBN", "CAB", "CBN");
    recipe.setIngredient('N', Material.AIR);
    recipe.setIngredient('A', Material.NETHER_STAR);
    recipe.setIngredient('B', Material.DIAMOND);
    recipe.setIngredient('C', Material.STRING);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
