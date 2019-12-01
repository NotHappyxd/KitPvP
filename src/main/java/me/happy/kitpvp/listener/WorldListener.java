package me.happy.kitpvp.listener;

import me.happy.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class WorldListener implements Listener {

    private int sponge;


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission("kitpvp.build") && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission("kitpvp.build") && !e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
            isMatBelow(player, Material.SPONGE, 100);
            player.sendMessage("Sponge amount: " + sponge);
            if (sponge > 20) {
                player.setVelocity(player.getVelocity().setY(0.3 * sponge));
                player.sendMessage("Velocity: " + 0.3 * sponge);
            }else {
                player.setVelocity(player.getVelocity().setY(0.5 * sponge));
                player.sendMessage("Velocity: " + 0.5 * sponge);
            }
            sponge = 0;
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(e.getNewExp() >= 0){
            /*
            new BukkitRunnable(){
                public void run() {
                    if(player.isOnline()) {
                        plugin.getTimerManager().spawnTagTimer.clearCooldown(player);
                        ((CraftPlayer) player).getHandle().setHidden(true);
                        player.spigot().respawn();
                    }
                }
            }.runTaskLater(plugin, 1);

             */
            Bukkit.getScheduler().runTaskLater(KitPvP.getInstance(), () -> {
                e.getEntity().spigot().respawn();
            }, 1L);
        }

    }

    private void isMatBelow(Player player, Material material, int depth) {
        Location location = player.getLocation().clone(); // Cloned location
        for (int blocks = 1; blocks <= depth; blocks++) { // From 1 to depth
            location.subtract(0, 1, 0); // Move one block down
            if (location.getBlock().getType() == material) {
                sponge++;
            }
        }
    }

}
