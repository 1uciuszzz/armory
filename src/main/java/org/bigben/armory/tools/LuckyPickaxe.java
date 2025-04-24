package org.bigben.armory.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class LuckyPickaxe implements Listener {
  private final NamespacedKey key;
  private final Random random = new Random();

  // 定义“矿石方块”类型集合
  private static final Set<Material> ORES = EnumSet.of(
      Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
      Material.COPPER_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE,
      Material.DIAMOND_ORE, Material.EMERALD_ORE,
      Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE,
      Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_COPPER_ORE,
      Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_LAPIS_ORE,
      Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE,
      Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE);

  // 可额外掉落的矿石种类
  private static final List<Material> EXTRA_DROPS = Arrays.asList(
      Material.DIAMOND, Material.EMERALD, Material.LAPIS_LAZULI,
      Material.REDSTONE, Material.COAL, Material.IRON_INGOT,
      Material.GOLD_INGOT, Material.COPPER_INGOT, Material.QUARTZ);

  public LuckyPickaxe(JavaPlugin plugin) {
    this.key = new NamespacedKey(plugin, "Ben008");
  }

  public ItemStack createItem() {
    ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
    ItemMeta meta = item.getItemMeta();

    if (meta != null) {
      meta.addEnchant(Enchantment.FORTUNE, 3, true);
      meta.setUnbreakable(true);
      meta.setDisplayName("§b§l幸运镐子");
      meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
      item.setItemMeta(meta);
    }
    return item;
  }

  public ShapedRecipe registerShape() {
    ShapedRecipe recipe = new ShapedRecipe(key, createItem());
    recipe.shape("DDD", " R ", " R ");
    recipe.setIngredient('D', Material.EMERALD);
    recipe.setIngredient('R', Material.BLAZE_ROD);
    return recipe;
  }

  public NamespacedKey getKey() {
    return key;
  }

  private boolean isLuckyPickaxe(ItemStack item) {
    if (item == null || item.getType() != Material.DIAMOND_PICKAXE)
      return false;
    ItemMeta meta = item.getItemMeta();
    return meta != null &&
        meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
  }

  @EventHandler
  public void onItemDamage(PlayerItemDamageEvent event) {
    if (isLuckyPickaxe(event.getItem())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    Block block = event.getBlock();

    if (!isLuckyPickaxe(item))
      return;
    if (!ORES.contains(block.getType()))
      return;

    // 50% 概率生成额外掉落
    if (random.nextDouble() < 0.5) {
      // 随机矿物，数量 1~3 个
      Material extra = EXTRA_DROPS.get(random.nextInt(EXTRA_DROPS.size()));
      int amount = random.nextInt(3) + 1;
      ItemStack drop = new ItemStack(extra, amount);

      block.getWorld().dropItemNaturally(block.getLocation(), drop);
      player.sendMessage("§a你的时运镐子额外掉落了 " + amount + " 个 " + formatName(extra) + "！");
    }
  }

  private String formatName(Material mat) {
    return mat.name().toLowerCase().replace("_", " ");
  }
}
