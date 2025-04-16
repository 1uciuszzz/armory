package org.bigben.armory.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ShapedRecipe;

public class AquaVisionHelmet implements Listener {
  private final NamespacedKey helmetKey;

  public AquaVisionHelmet(JavaPlugin plugin) {
    this.helmetKey = new NamespacedKey(plugin, "deep_dive_helmet");
  }

  public ItemStack createHelmet() {
    ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
    ItemMeta meta = helmet.getItemMeta();

    if (meta != null) {
      meta.setDisplayName("§3深潜头盔");
      meta.setUnbreakable(true);
      meta.getPersistentDataContainer().set(helmetKey, PersistentDataType.BYTE, (byte) 1);
      helmet.setItemMeta(meta);
    }

    return helmet;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack helmet = createHelmet();
    ShapedRecipe recipe = new ShapedRecipe(helmetKey, helmet);
    recipe.shape("GGG", "G G");
    recipe.setIngredient('G', Material.PRISMARINE_CRYSTALS);
    return recipe;
  }

  private boolean isDeepDiveHelmet(ItemStack item) {
    if (item == null || item.getType() != Material.DIAMOND_HELMET)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return false;
    return meta.getPersistentDataContainer().has(helmetKey, PersistentDataType.BYTE);
  }

  // 给戴着头盔的玩家持续加药水
  private void applyHelmetEffects(Player player) {
    ItemStack helmet = player.getInventory().getHelmet();
    if (isDeepDiveHelmet(helmet)) {
      player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 220, 0, true, false));
      player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 220, 0, true, false));
    }
  }

  // 玩家移动时刷新效果
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    applyHelmetEffects(event.getPlayer());
  }

  // 玩家上线时刷新效果
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    applyHelmetEffects(event.getPlayer());
  }

  // 防止头盔损坏
  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isDeepDiveHelmet(event.getItem())) {
      event.setCancelled(true);
    }
  }

  public NamespacedKey getHelmetKey() {
    return helmetKey;
  }
}
