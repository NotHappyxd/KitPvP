package me.happy.kitpvp.profile;

import lombok.Getter;
import lombok.Setter;
import me.happy.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {

    @Getter private Player player;
    @Getter private File file;
    @Getter private FileConfiguration config;

    @Getter @Setter private int tokens;
    @Getter @Setter private int bounty;
    @Getter @Setter private int killStreak;

    @Getter private static Map<Player, Profile> playerProfileMap = new HashMap<>();

    public Profile(Player player) {
        this.player = player;

        this.tokens = 100;
        this.bounty = 0;
        this.killStreak = 0;

        this.file = new File(KitPvP.getInstance().getDataFolder() + File.separator + "playerdata" + File.separator + player.getUniqueId().toString() + ".yml");
        config = YamlConfiguration.loadConfiguration(file);

        this.load();
    }

    public void createUser(final Player player){

        if (!(file.exists())) {
            try {

                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[hKitPvP] Created a new File for " + player.getName() + "(" + player.getUniqueId() + ")");

                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("name", player.getName());
                config.set("tokens", this.tokens);
                config.set("bounty", this.bounty);
                config.set("killStreak", this.killStreak);

                // yamls.add(UserConfig);
                //UserConfig.save(UserFile);
            } catch (Exception e) {
                e.printStackTrace();
                player.kickPlayer(ChatColor.RED + "We could not create a file for your account!"); // THE PLAYERS CONFIG NEEDS TO BE CREATED!!!!!!!!
            }

        }else{
            try {
                if (!this.config.getString("name").equals(player.getName())) {
                    Bukkit.getConsoleSender().sendMessage(
                            ChatColor.YELLOW +
                                    "[Name]"
                                    + this.config.get("name")
                                    + " has changed his name to " + player.getName());
                    this.config.set("name", player.getName());
                    this.config.save(this.file);

                }
                // UserConfig.load(UserFile);
            }   catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    public void saveFile(){
        this.config.set("tokens", this.tokens);
        this.config.set("bounty", this.bounty);
        this.config.set("killStreak", this.killStreak);
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save file!");
        }
    }

    private void load() {
        this.tokens = this.config.getInt("tokens");
        this.bounty = this.config.getInt("bounty");
        this.killStreak = this.config.getInt("killStreak");
    }

    public static Profile getProfile(Player player) {
        return playerProfileMap.get(player);
    }

}