package me.happy.kitpvp.command;

import com.google.common.collect.ImmutableList;
import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.scoreboard.ScoreboardListener;
import me.happy.kitpvp.util.BukkitUtils;
import me.happy.kitpvp.util.CC;
import me.happy.kitpvp.util.CommandUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TokensCommand implements CommandExecutor, TabCompleter {

    private static final List<String> COMPLETIONS = ImmutableList.of("add", "subtract", "set");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2 && args[0].equalsIgnoreCase("check")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(CC.RED + "That player is not online!");
                return true;
            }

            Profile targetProfile = Profile.getProfile(target);

            sender.sendMessage(CC.GREEN + target.getName() + " has " + targetProfile.getTokens() + " tokens!");
        }

        if (args.length != 3 && !(args.length == 2 && args[0].equalsIgnoreCase("check"))) {
            sender.sendMessage("/token set <player> <amount>");
            sender.sendMessage("/token add <player> <amount>");
            sender.sendMessage("/token subtract <player> <amount>");
            sender.sendMessage("/token check <player>");
            return true;
        }

        if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(CC.RED + "That player is not online!");
                return true;
            }


            if (!CommandUtilities.isInt(args[2])) {
                sender.sendMessage(CC.RED + args[2] + " is an invalid number.");
                return true;
            }

            Integer amount = Integer.parseInt(args[2]);

            if (amount < 0) {
                sender.sendMessage(CC.RED + args[2] + " is less than 0");
                return true;
            }

            Profile targetProfile = Profile.getProfile(target);

            if (args[0].equalsIgnoreCase("set")) {
                setTokens(targetProfile, amount);
                sender.sendMessage(CC.GREEN + target.getName() + " now has " + targetProfile.getTokens() + " tokens");
                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                setTokens(targetProfile, targetProfile.getTokens() + amount);
                sender.sendMessage(CC.GREEN + "Gave " + target.getName() + " " + amount + " tokens!");
                return true;
            }

            if (args[0].equalsIgnoreCase("subtract") || args[0].equalsIgnoreCase("minus")) {
                // targetProfile.setTokens(targetProfile.getTokens() + amount);
                setTokens(targetProfile, targetProfile.getTokens() - amount);
                sender.sendMessage(CC.GREEN + "Took away " + amount + " tokens from " + target.getName());
                return true;
            }
        }

        return true;
    }

    private void setTokens(Profile profile, int amount) {
        profile.setTokens(amount);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> toReturn = new ArrayList<>();

        if(args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                toReturn.add(player.getName());
            }
            return toReturn;
        }else if (args.length == 1) {
            return BukkitUtils.getCompletions(args, COMPLETIONS);
        }
        return Collections.emptyList();
    }
}
