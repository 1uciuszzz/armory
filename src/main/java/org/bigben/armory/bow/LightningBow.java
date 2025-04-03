package org.bigben.armory.bow;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
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

public class LightningBow implements Listener {
  private final NamespacedKey bowKey;

  public LightningBow(JavaPlugin plugin) {
    this.bowKey = new NamespacedKey(plugin, "LightningBow");
  }

  @EventHandler
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    // 检查是否由箭矢造成伤害
    if (!(event.getDamager() instanceof Arrow))
      return;

    Arrow arrow = (Arrow) event.getDamager();

    // 检查是否由玩家射出
    if (!(arrow.getShooter() instanceof Player))
      return;

    Player shooter = (Player) arrow.getShooter();
    ItemStack mainHand = shooter.getInventory().getItemInMainHand();
    ItemStack offHand = shooter.getInventory().getItemInOffHand();

    // 检查是否使用雷霆弓
    if (!isLightningBow(mainHand) && !isLightningBow(offHand))
      return;

    // 如果目标是生物实体，则召唤闪电
    if (event.getEntity() instanceof LivingEntity) {
      Entity target = event.getEntity();
      target.getWorld().strikeLightning(target.getLocation());
    }
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    // 防止弓受到伤害
    if (isLightningBow(event.getItem())) {
      event.setCancelled(true);
    }
  }

  private boolean isLightningBow(ItemStack item) {
    if (item == null || item.getType() != Material.BOW)
      return false;
    ItemMeta meta = item.getItemMeta();
    if (meta == null || !meta.getPersistentDataContainer().has(bowKey, PersistentDataType.BYTE))
      return false;
    return meta.getPersistentDataContainer().get(bowKey, PersistentDataType.BYTE) == (byte) 1;
  }

  public ItemStack createLightningBow() {
    ItemStack bow = new ItemStack(Material.BOW);
    ItemMeta meta = bow.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§6杨永信的箭");
      meta.getPersistentDataContainer().set(bowKey, PersistentDataType.BYTE, (byte) 1);
      meta.setUnbreakable(true);
      bow.setItemMeta(meta);
    }
    return bow;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack bow = createLightningBow();
    ShapedRecipe recipe = new ShapedRecipe(bowKey, bow);
    recipe.shape("BSD", "BDS", "BSD");
    recipe.setIngredient('B', Material.BLAZE_ROD);
    recipe.setIngredient('S', Material.STRING);
    recipe.setIngredient('D', Material.DIAMOND);
    return recipe;
  }

  public NamespacedKey getBowKey() {
    return bowKey;
  }
}