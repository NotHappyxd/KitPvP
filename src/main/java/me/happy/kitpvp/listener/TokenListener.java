package me.happy.kitpvp.listener;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.scoreboard.ScoreboardListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class TokenListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            Profile playerProfile = Profile.getProfile(player);
            Profile killerProfile = Profile.getProfile(killer);

            if (playerProfile.getBounty() > 0) {
                killerProfile.setTokens(killerProfile.getTokens() + playerProfile.getBounty());
                Bukkit.broadcastMessage(ChatColor.YELLOW + killer.getName() + " has claimed the bounty of " + player.getName() + " for $" + playerProfile.getBounty());
                playerProfile.setBounty(0);
            }

            killerProfile.setTokens(killerProfile.getTokens() + 4);

        }

    }

}
