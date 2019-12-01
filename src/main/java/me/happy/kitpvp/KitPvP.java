package me.happy.kitpvp;

import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.happy.kitpvp.command.BountyCommand;
import me.happy.kitpvp.command.ColorCommand;
import me.happy.kitpvp.command.TokensCommand;
import me.happy.kitpvp.listener.ChatListener;
import me.happy.kitpvp.listener.TokenListener;
import me.happy.kitpvp.listener.WorldListener;
import me.happy.kitpvp.profile.ProfileManager;
import me.happy.kitpvp.scoreboard.ScoreboardLink;
import me.happy.kitpvp.scoreboard.ScoreboardListener;
import me.happy.kitpvp.util.CC;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class KitPvP extends JavaPlugin {

    private static KitPvP instance;
    @Getter private ProfileManager profileManager;
    @Getter private ScoreboardLink scoreboard;
    @Getter private Chat chat;

    public void onEnable() {
        instance = this;

        this.registerManagers();
        this.registerListeners();
        this.generateMessage();
        this.registerCommands();
        // this.setUpConfig();
        loadResource(this, "config.yml");

        this.getServer().getScheduler().runTaskTimer(this, () -> {
            String players = Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("rank.donor")).map(Player::getName).collect(Collectors.joining(", "));
            if (players.isEmpty()) {
                players = "None";
            }
            this.getServer().broadcastMessage(CC.translate("&6Online Player&7: &f" + players));
        }, 0L, 20 * 2 * 60);


        this.getServer().getScheduler().runTaskTimer(this, () -> {
            ScoreboardLink link = new ScoreboardLink();
            if (getServer().getOnlinePlayers().size() != 0) {
                for (Player player : getServer().getOnlinePlayers()) {
                    link.updateScoreboard(player);
                }
            }
        }, 0L, 2L);


        setupChat();
    }

    private void registerManagers() {
        this.profileManager = new ProfileManager();
        this.scoreboard = new ScoreboardLink();
    }

    private void registerCommands() {
        getCommand("bounty").setExecutor(new BountyCommand());
        getCommand("tokens").setExecutor(new TokensCommand());
        getCommand("color").setExecutor(new ColorCommand());
    }

    public void onDisable() {

        instance = null;
    }

    private void generateMessage() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + "  _     _  ___ _   _____        _____  ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + " | |   | |/ (_) | |  __ \\      |  __ \\ ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + " | |__ | ' / _| |_| |__) |_   _| |__) |");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + " | '_ \\|  < | | __|  ___/\\ \\ / /  ___/ ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + " | | | | . \\| | |_| |     \\ V /| |     ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + " |_| |_|_|\\_\\_|\\__|_|      \\_/ |_|     ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.GOLD  + "                                       ");
    }

    private void registerListeners() {
        Arrays.asList(
                new TokenListener(),
                new WorldListener(),
                new ProfileManager(),
                new ScoreboardListener(),
                new ChatListener()
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

    public static KitPvP getInstance() {
        return instance;
    }

    private void setUpConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);

        try {
            if (!resourceFile.exists() && resourceFile.createNewFile()) {
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception ignored) {}
        return resourceFile;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
}
