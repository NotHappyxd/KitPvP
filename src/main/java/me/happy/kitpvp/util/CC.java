package me.happy.kitpvp.util;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String BOLD = ChatColor.BOLD.toString();
    public static String STRIKETHROUGH = ChatColor.STRIKETHROUGH.toString();
    public static String UNDERLINE = ChatColor.UNDERLINE.toString();
    public static String ITALICS = ChatColor.ITALIC.toString();
    public static String RESET = ChatColor.RESET.toString();
    public static String AQUA = ChatColor.AQUA.toString();
    public static String BLACK = ChatColor.BLACK.toString();
    public static String BLUE = ChatColor.BLUE.toString();
    public static String DARKAQUA = ChatColor.DARK_AQUA.toString();
    public static String DARKBLUE = ChatColor.DARK_BLUE.toString();
    public static String GRAY = ChatColor.GRAY.toString();
    public static String DARKGRAY = ChatColor.DARK_GRAY.toString();
    public static String DARKGREEN = ChatColor.DARK_GREEN.toString();
    public static String DARKPURPLE = ChatColor.DARK_PURPLE.toString();
    public static String DARKRED = ChatColor.DARK_RED.toString();
    public static String GOLD = ChatColor.GOLD.toString();
    public static String GREEN = ChatColor.GREEN.toString();
    public static String PURPLE = ChatColor.LIGHT_PURPLE.toString();
    public static String RED = ChatColor.RED.toString();
    public static String WHITE = ChatColor.WHITE.toString();
    public static String YELLOW = ChatColor.YELLOW.toString();

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

}

