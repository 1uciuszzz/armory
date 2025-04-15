package org.bigben.armory;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.List;

public class LightningScope implements Listener {

    private final JavaPlugin plugin;
    private final ItemStack item;

    public LightningScope(JavaPlugin plugin) {
        this.plugin = plugin;
        this.item = createLightningScope();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private ItemStack createLightningScope() {
        ItemStack scope = new ItemStack(Material.SPYGLASS);
        ItemMeta meta = scope.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Lightning Scope");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "Right-click to summon lightning!");
            meta.setLore(lore);
            scope.setItemMeta(meta);
        }
        return scope;
    }

    public ItemStack getItem() {
        return item;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().isSimilar(item) || player.getInventory().getItemInOffHand().isSimilar(item)) {
                event.setCancelled(true);

                RayTraceResult rayTrace = player.rayTraceBlocks(200);
                if (rayTrace != null && rayTrace.getHitBlock() != null) {
                    Location strikeLocation = rayTrace.getHitBlock().getLocation().add(0.5, 1, 0.5);
                    strikeLocation.getWorld().strikeLightning(strikeLocation);
                    player.sendMessage(ChatColor.YELLOW + "Lightning strike initiated!");
                } else {
                    player.sendMessage(ChatColor.RED + "No target found for lightning strike.");
                }
            }
        }
    }
}