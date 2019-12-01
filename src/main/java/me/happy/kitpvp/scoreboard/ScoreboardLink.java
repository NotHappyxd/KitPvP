package me.happy.kitpvp.scoreboard;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.util.CC;
import me.happy.kitpvp.util.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardLink {

    public void scoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = board.registerNewObjective("kitpvp", "dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.AQUA + "KoralPvP");
        Team kills = board.registerNewTeam("kills");
        Team deaths = board.registerNewTeam("deaths");
        Team tokens = board.registerNewTeam("tokens");
        Team bounty = board.registerNewTeam("bounty");
        Team killStreak = board.registerNewTeam("killStreak");

        // test =  board.registerNewTeam("test");
        Profile profile = Profile.getProfile(player);

        //kills = o.getScore(ChatColor.AQUA + "Kills" + ChatColor.GRAY + ": " + ChatColor.WHITE + player.getStatistic(Statistic.PLAYER_KILLS));
        //kills.setScore(4);
        kills.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        kills.setPrefix(ChatColor.AQUA + "Kills" + ChatColor.GRAY + ": " + ChatColor.WHITE + player.getStatistic(Statistic.PLAYER_KILLS));
        o.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(4);

        deaths.addEntry(ChatColor.DARK_RED + "" + ChatColor.WHITE);
        deaths.setPrefix(ChatColor.AQUA + "Deaths" + ChatColor.GRAY + ": " + ChatColor.WHITE + player.getStatistic(Statistic.DEATHS));
        o.getScore(ChatColor.DARK_RED + "" + ChatColor.WHITE).setScore(3);

        tokens.addEntry(ChatColor.GOLD + "" + ChatColor.WHITE);
        tokens.setPrefix(ChatColor.AQUA + "Tokens" + ChatColor.GRAY + ": ");
        tokens.setSuffix(ChatColor.WHITE + String.valueOf(profile.getTokens()));

        o.getScore(ChatColor.GOLD + "" + ChatColor.WHITE).setScore(2);


        if (profile.getBounty() != 0) {
            bounty.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
            bounty.setPrefix(ChatColor.AQUA + "Bounty" + ChatColor.GRAY + ": " + ChatColor.WHITE + profile.getBounty());
            o.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(1);
        }

        if (profile.getKillStreak() != 0) {
            killStreak.addEntry(ChatColor.GRAY + "" + ChatColor.WHITE);
            killStreak.setPrefix(ChatColor.AQUA + "Kilstreak" + ChatColor.GRAY + ": " + ChatColor.WHITE + profile.getKillStreak());
            o.getScore(ChatColor.GRAY + "" + ChatColor.WHITE).setScore(0);
        }

        Team team;
        if (board.getTeam(RankUtils.getRankWithoutColor(player)) == null) {
            team = board.registerNewTeam(RankUtils.getRankWithoutColor(player));
        }else {
            team = board.getTeam(RankUtils.getRankWithoutColor(player));
        }

        team.setPrefix(RankUtils.getRankColor(player));
        team.addPlayer(player);




        player.setScoreboard(board);
    }

    public void updateScoreboard(Player player) {
        Profile profile = Profile.getProfile(player);
        Scoreboard board = player.getScoreboard();
        board.getTeam("kills").setPrefix(ChatColor.AQUA + "Kills" + ChatColor.GRAY + ": ");
        board.getTeam("kills").setSuffix(ChatColor.WHITE + String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)));

        board.getTeam("deaths").setPrefix(ChatColor.AQUA + "Deaths" + ChatColor.GRAY + ": ");
        board.getTeam("deaths").setSuffix(ChatColor.WHITE + String.valueOf(player.getStatistic(Statistic.DEATHS)));

        board.getTeam("tokens").setPrefix(ChatColor.AQUA + "Tokens" + ChatColor.GRAY + ": ");
        board.getTeam("tokens").setSuffix(ChatColor.WHITE + String.valueOf(profile.getTokens()));

        if (profile.getBounty() != 0) {
            board.getTeam("bounty").setPrefix(ChatColor.AQUA + "Bounty" + ChatColor.GRAY + ": " + ChatColor.WHITE + profile.getBounty());
        }
        if (profile.getKillStreak() != 0) {
            board.getTeam("killStreak").setPrefix(ChatColor.AQUA + "Kilstreak" + ChatColor.GRAY + ": " + ChatColor.WHITE + profile.getKillStreak());

        }
    }

    public void registerRanks() {

        // List<String> test = new ArrayList<>(KitPvP.getInstance().getConfig().getConfigurationSection("rank").getKeys(false));
        ConfigurationSection cs = KitPvP.getInstance().getConfig().getConfigurationSection("rank");
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        for (String s : cs.getKeys(false)) {
            board.registerNewTeam(s).setPrefix(ChatColor.translateAlternateColorCodes('&', cs.getString(s)));
            board.getTeam(s).setPrefix(CC.translate(cs.getString(s)));
        }



    }



}
