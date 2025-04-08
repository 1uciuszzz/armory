package org.bigben.armory.foods;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GoldenChip implements Listener {
  private final NamespacedKey key;

  public GoldenChip(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "GoldenChip");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.BAKED_POTATO);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      meta.setDisplayName("§6黄金薯片");
      meta.setCustomModelData(1001);

      // 设置物品描述
      List<String> lore = new ArrayList<>();
      lore.add("§7一口下去，神清气爽！");
      meta.setLore(lore);

      // **核心：存储唯一标识**
      PersistentDataContainer data = meta.getPersistentDataContainer();
      data.set(key, PersistentDataType.BYTE, (byte) 1);

      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerRecipe() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape(" G ", " P ", "   ");
    recipe.setIngredient('G', Material.GOLD_NUGGET);
    recipe.setIngredient('P', Material.POTATO);
    return recipe;
  }

  @EventHandler
  public void onPlayerEat(PlayerItemConsumeEvent event) {
    if (event.getItem().getItemMeta() != null &&
        event.getItem().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.BYTE)) {

      event.getPlayer().sendMessage("§e你吃下了黄金薯片，感觉精力充沛！");

      // 添加正向 Buff
      event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1)); // 速度 II，持续 5 秒
      event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 1)); // 生命恢复 II，持续 5 秒
    }
  }
}
