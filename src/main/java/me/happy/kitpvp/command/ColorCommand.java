package me.happy.kitpvp.command;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;

public class ColorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Invalid usage. /color <player> <rank>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(CC.RED + "That player is not online!");
                return true;
            }

            Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

            board.getPlayerTeam(target).removePlayer(target);

            Team team = board.getTeam(args[1]);
            team.addPlayer(target);

            sender.sendMessage(CC.GREEN + "You have changed " + target.getName() + "'s color to " + args[1]);

            return true;
        }

        if (!sender.hasPermission("rank.color")) {
            sender.sendMessage(CC.RED + "You do not have permissions to execute this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid Usage. /color <rank>");
            return true;
        }

        String rank = args[0];

        List<String> groups = Arrays.asList(KitPvP.getInstance().getChat().getGroups());
        if (!groups.contains(rank)) {
            sender.sendMessage(ChatColor.RED + rank + " is an invalid rank!");
        }

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        Player player = (Player)sender;
        Team team = board.getPlayerTeam(player);
        if (team == null) {
            board.getTeam(rank).addPlayer(player);
            sender.sendMessage(CC.GREEN + "You have changed your color to " + rank);
            return true;
        }

        team.removePlayer(player);

        Team newColor = board.getTeam(rank);
        newColor.addPlayer(player);
        sender.sendMessage(CC.GREEN + "You have changed your color to " + rank);
        return true;


    }
}
