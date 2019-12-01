package me.happy.kitpvp.listener;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.scoreboard.ScoreboardListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KillstreaksListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Profile playerProf = Profile.getProfile(e.getEntity());
        if (e.getEntity().getKiller() != null) {
            Profile killerProf = Profile.getProfile(e.getEntity().getKiller());
            killerProf.setKillStreak(killerProf.getKillStreak() + 1);
            Bukkit.broadcastMessage(ChatColor.YELLOW + killerProf.getPlayer().getName() + " has ended " + e.getEntity().getName() + " killstreak of " + playerProf.getKillStreak());
        }
        playerProf.setKillStreak(0);
        //--------------------------------------------------------------------------------------------------------------------------------------------------------------
        if (e.getEntity().getKiller() != null) {
            Profile killerProf = Profile.getProfile(e.getEntity().getKiller());
            Player killer = killerProf.getPlayer();
            switch (killerProf.getKillStreak()) {
                case 5:
                    for (ItemStack is : killer.getInventory().addItem(new ItemStack(Material.POTION, 1, (byte) 16388)).values()) {
                        killer.getWorld().dropItem(killer.getLocation(), is);
                    }
                    break;
                case 10:
                    for (ItemStack is : killer.getInventory().addItem(new ItemStack(Material.POTION, 1, (byte) 8290)).values()) {
                        killer.getWorld().dropItem(killer.getLocation(), is);
                    }
                    break;
                case 20:
                    for (ItemStack is : killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1)).values()) {
                        killer.getWorld().dropItem(killer.getLocation(), is);
                    }
                    break;
                case 50:
                    for (ItemStack is : killer.getInventory().addItem(new ItemStack(Material.POTION, 3, (byte) 1)).values()) {
                        killer.getWorld().dropItem(killer.getLocation(), is);
                    }

                    //TODO: GIVE KEY
                    break;
                case 100:
                    for (ItemStack is : killer.getInventory().addItem(new ItemStack(Material.POTION, 10, (byte) 1)).values()) {
                        killer.getWorld().dropItem(killer.getLocation(), is);
                    }
                    //TODO: GIVE KEY && RANK
                    break;
            }
        }
    }
}
