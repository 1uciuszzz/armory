package org.bigben.armory.swords;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class LuckySword implements Listener {
  private final NamespacedKey swordKey;
  private final Random random = new Random();

  public LuckySword(JavaPlugin plugin) {
    this.swordKey = new NamespacedKey(plugin, "LuckySword");
  }

  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    if (!(event.getDamager() instanceof Player))
      return;

    Player player = (Player) event.getDamager();
    ItemStack item = player.getInventory().getItemInMainHand();

    if (!isLuckySword(item))
      return;

    Entity entity = event.getEntity();
    if (!(entity instanceof LivingEntity))
      return;

    LivingEntity target = (LivingEntity) entity;

    // 秒杀：10% 概率
    if (random.nextDouble() < 0.1) {
      player.sendMessage("§c绝命木剑爆发威力，一击秒杀了目标！");
      target.setHealth(0);
      return;
    }

  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isLuckySword(event.getItem())) {
      event.setCancelled(true);
    }
  }

  private boolean isLuckySword(ItemStack item) {
    if (item == null || item.getType() != Material.WOODEN_SWORD)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return false;
    return meta.getPersistentDataContainer().has(swordKey, PersistentDataType.BYTE)
        && meta.getPersistentDataContainer().get(swordKey, PersistentDataType.BYTE) == (byte) 1;
  }

  public ItemStack createLuckySword() {
    ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
    ItemMeta meta = sword.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§6绝命木剑");
      meta.getPersistentDataContainer().set(swordKey, PersistentDataType.BYTE, (byte) 1);
      meta.setUnbreakable(true);
      meta.addEnchant(Enchantment.LOOTING, 3, true);
      sword.setItemMeta(meta);
    }
    return sword;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack sword = createLuckySword();
    ShapedRecipe recipe = new ShapedRecipe(swordKey, sword);
    recipe.shape(" D ", " D ", " S ");
    recipe.setIngredient('D', Material.EMERALD);
    recipe.setIngredient('S', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getSwordKey() {
    return swordKey;
  }
}
