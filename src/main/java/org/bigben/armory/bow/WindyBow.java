package org.bigben.armory.bow;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class WindyBow implements Listener {
  private final NamespacedKey windyBowKey;
  private final NamespacedKey windyBowWindCharge;

  public WindyBow(JavaPlugin plugin) {
    this.windyBowKey = new NamespacedKey(plugin, "windy_bow");
    this.windyBowWindCharge = new NamespacedKey(plugin, "windy_bow_wind_charge");
  }

  public ItemStack createWand() {
    ItemStack wand = new ItemStack(Material.BREEZE_ROD);
    ItemMeta meta = wand.getItemMeta();
    if (meta != null) {
      meta.setDisplayName("§c§l龙卷风");
      meta.setUnbreakable(true);
      meta.getPersistentDataContainer().set(windyBowKey, PersistentDataType.BYTE, (byte) 1);
      wand.setItemMeta(meta);
    }
    return wand;
  }

  public ShapedRecipe registerRecipe() {
    ItemStack wand = createWand();
    ShapedRecipe recipe = new ShapedRecipe(windyBowKey, wand);
    recipe.shape(" F ", " R ", " R ");
    recipe.setIngredient('F', Material.WIND_CHARGE);
    recipe.setIngredient('R', Material.BREEZE_ROD);
    return recipe;
  }

  private boolean isWindyBow(ItemStack item) {
    if (item == null || item.getType() != Material.BREEZE_ROD)
      return false;
    ItemMeta meta = item.getItemMeta();
    return meta != null && meta.getPersistentDataContainer().has(windyBowKey, PersistentDataType.BYTE);
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isWindyBow(event.getItem())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (!isWindyBow(item))
      return;

    Action action = event.getAction();
    event.setCancelled(true); // 统一取消默认行为

    switch (action) {

      case RIGHT_CLICK_AIR:
      case RIGHT_CLICK_BLOCK: {

        WindCharge windCharge = player.launchProjectile(WindCharge.class);
        windCharge.setVelocity(player.getLocation().getDirection().multiply(2));
        windCharge.setShooter(player);
        windCharge.getPersistentDataContainer().set(windyBowWindCharge, PersistentDataType.BYTE, (byte) 1);
        break;
      }

      default:
        break; // 其他操作不处理
    }
  }

}