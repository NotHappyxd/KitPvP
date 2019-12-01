package me.happy.kitpvp.util;

import me.happy.kitpvp.KitPvP;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class RankUtils {

    public static String getRank(Player player) {
        String rank = KitPvP.getInstance().getChat().getPrimaryGroup(player);
        if (rank == null) {
            rank = "Unknown";
        }

        ConfigurationSection rank_color = KitPvP.getInstance().getConfig().getConfigurationSection("rank");
        String color = rank_color.getString(rank) == null ? ChatColor.WHITE.toString() : ChatColor.translateAlternateColorCodes('&', rank_color.getString(rank));

        return color + rank;
    }

    public static String getRankColor(Player player) {
        String rank = KitPvP.getInstance().getChat().getPrimaryGroup(player);
        if (rank == null) {
            rank = "Unknown";
        }

        ConfigurationSection rank_color = KitPvP.getInstance().getConfig().getConfigurationSection("rank");

        return rank_color.getString(rank) == null ? ChatColor.WHITE.toString() : ChatColor.translateAlternateColorCodes('&', rank_color.getString(rank));
    }

    public static String getRankWithoutColor(Player player) {
        String rank = KitPvP.getInstance().getChat().getPrimaryGroup(player);
        if (rank == null) {
            rank = "Unknown";
        }

        player.sendMessage(rank);

        return rank;
    }
}
