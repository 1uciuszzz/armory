package org.bigben.armory.swords;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class UndeadSlayerSword implements Listener {
  private final NamespacedKey key;
  private final Random random = new Random();

  public UndeadSlayerSword(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "undead_slayer_sword");
  }

  public ItemStack createSword() {
    ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
    ItemMeta meta = sword.getItemMeta();

    if (meta != null) {
      meta.setDisplayName("§5亡灵杀手");
      meta.setUnbreakable(true);
      meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 8, true);
      meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
      sword.setItemMeta(meta);
    }
    return sword;
  }

  public ShapedRecipe registerRecipe() {
    ShapedRecipe recipe = new ShapedRecipe(key, createSword());
    recipe.shape("AOB", "AOB", "ATB");
    recipe.setIngredient('A', Material.GHAST_TEAR);
    recipe.setIngredient('T', Material.STICK);
    recipe.setIngredient('B', Material.BONE);
    recipe.setIngredient('O', Material.OBSIDIAN);
    return recipe;
  }

  public boolean isUndeadSlayer(ItemStack item) {
    if (item == null || item.getType() != Material.WOODEN_SWORD)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null)
      return false;
    return meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity entity = event.getEntity();
    Player killer = entity.getKiller();
    if (killer == null)
      return;

    ItemStack weapon = killer.getInventory().getItemInMainHand();
    if (!isUndeadSlayer(weapon))
      return;

    // 判断是否是亡灵类生物
    EntityType type = entity.getType();
    if (type == EntityType.ZOMBIE || type == EntityType.SKELETON || type == EntityType.WITHER_SKELETON ||
        type == EntityType.STRAY || type == EntityType.HUSK || type == EntityType.DROWNED
        || type == EntityType.ZOMBIFIED_PIGLIN) {
      if (random.nextDouble() < 0.25) { // 10% 掉落头颅
        Material skull = getSkullForType(type);
        if (skull != null) {
          entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(skull));
          killer.sendMessage("§d你从亡灵身上夺得了它的头颅！");
        }
      }
    }
  }

  private Material getSkullForType(EntityType type) {
    return switch (type) {
      case SKELETON -> Material.SKELETON_SKULL;
      case ZOMBIE, HUSK, DROWNED, ZOMBIFIED_PIGLIN -> Material.ZOMBIE_HEAD;
      case WITHER_SKELETON -> Material.WITHER_SKELETON_SKULL;
      case STRAY -> Material.SKELETON_SKULL;
      default -> null;
    };
  }

  public NamespacedKey getKey() {
    return key;
  }
}
