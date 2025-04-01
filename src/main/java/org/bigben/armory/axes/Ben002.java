package org.bigben.armory.axes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ben002 {
  private final NamespacedKey key;

  public Ben002(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben002"); // 定义唯一标识
  }

  public ItemStack createItem() {
    ItemStack modelH = new ItemStack(Material.GOLDEN_AXE);
    ItemMeta modelHMeta = modelH.getItemMeta();

    if (modelHMeta != null) {
      modelHMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
      modelHMeta.setUnbreakable(true);
      modelHMeta.setDisplayName("§c§lThunder Axe");

      List<String> loreList = new ArrayList<>();
      loreList.add("§7传说中的雷霆战斧！");
      modelHMeta.setLore(loreList);

      // 添加攻击力属性
      AttributeModifier attackDamageModifier = new AttributeModifier(
          UUID.randomUUID(),
          "generic.attackDamage",
          12,
          AttributeModifier.Operation.ADD_NUMBER,
          org.bukkit.inventory.EquipmentSlot.HAND);
      modelHMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);

      // **核心改动：给物品添加自定义唯一标识**
      modelHMeta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

      modelH.setItemMeta(modelHMeta);
    }
    return modelH;
  }

  public ShapedRecipe registerShape() {
    ItemStack item = createItem();
    ShapedRecipe recipe = new ShapedRecipe(key, item);
    recipe.shape("BCN", "BAN", "NAN");
    recipe.setIngredient('N', Material.AIR);
    recipe.setIngredient('A', Material.STICK);
    recipe.setIngredient('B', Material.IRON_INGOT);
    recipe.setIngredient('C', Material.TRIDENT);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }
}
