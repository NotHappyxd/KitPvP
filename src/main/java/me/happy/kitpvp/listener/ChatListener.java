package me.happy.kitpvp.listener;

import me.happy.kitpvp.KitPvP;
import me.happy.kitpvp.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        e.setFormat(CC.translate(KitPvP.getInstance().getChat().getPlayerPrefix(player) + player.getName() + "&f: ") + ChatColor.RESET + e.getMessage());
    }
}
