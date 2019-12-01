package me.happy.kitpvp.profile;

import me.happy.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ProfileManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Profile profile = new Profile(e.getPlayer());

        if (!profile.getFile().exists()) {
            profile.createUser(e.getPlayer());
        }

        Profile.getPlayerProfileMap().put(e.getPlayer(), profile);

        if (!e.getPlayer().hasPlayedBefore()) {
            Profile.getProfile(e.getPlayer()).setTokens(100);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Profile.getProfile(e.getPlayer()).saveFile();
        Profile.getPlayerProfileMap().remove(e.getPlayer());
    }
}
