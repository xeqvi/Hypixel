package net.hypixel.utils;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String color(String message) {
        if (message == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
