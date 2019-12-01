package me.happy.kitpvp.scoreboard;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.util.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.util.Set;

public class ScoreboardListener implements Listener {

    boolean doRenderRank = true;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        KitPvP.getInstance().getScoreboard().scoreboard(e.getPlayer());

        //KitPvP.getInstance().getScoreboard().Owner.addPlayer(e.getPlayer());
    }

}
