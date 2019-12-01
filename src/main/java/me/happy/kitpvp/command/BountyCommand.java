package me.happy.kitpvp.command;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.profile.Profile;
import me.happy.kitpvp.scoreboard.ScoreboardListener;
import me.happy.kitpvp.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(CC.RED + "Invalid usage! /" + label + " <player> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(CC.RED + "That player is not online!");
            return true;
        }

        Integer bounty = Integer.parseInt(args[1]);

        if (bounty <= 0) {
            sender.sendMessage(CC.RED + args[1] + " has to be greater than 0.");
            return true;
        }

        Profile targetProfile = Profile.getProfile(target);
        Profile senderProfile = Profile.getProfile((Player)sender);

        if (senderProfile.getTokens() < bounty) {
            sender.sendMessage(ChatColor.RED + "You do not have enough tokens to set the bounty.");
            return true;
        }
        senderProfile.setTokens(senderProfile.getTokens() - bounty);
        targetProfile.setBounty(targetProfile.getBounty() + bounty);
        Bukkit.broadcastMessage(CC.GOLD + sender.getName() + CC.DARKAQUA + " has placed a bounty on " + CC.GOLD + target.getName() + CC.DARKAQUA + " for " + bounty + " tokens");
        return true;
    }
}
