package me.harry0198.mailme.utility;

import com.google.gson.reflect.TypeToken;
import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static String colour(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> colourList(List<String> string) {
        return string.stream().map(Utils::colour).collect(Collectors.toList());
    }

    public static PlayerData readJson(File file) {
        try {
            Type token = new TypeToken<PlayerData>() {}.getType();
            FileReader fr = new FileReader(file);
            PlayerData data = MailMe.GSON.fromJson(fr, token);
            fr.close();

            return data;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return null;
    }


    public static void writeJson(File file, PlayerData data) {
        try {

            FileWriter writer = new FileWriter(file);
            MailMe.GSON.toJson(data, writer);
            writer.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static ItemStack getItemStack(ConfigurationSection section) {
        ItemBuilder builder = new ItemBuilder(Material.valueOf(section.getString("material")))
                .setName(colour(section.getString("title")))
                .setLore(colourList(section.getStringList("lore")).toArray(new String[]{}))
                .setAmount(section.getInt("amount"));

        if (Material.valueOf(section.getString("material")).equals(Material.PLAYER_HEAD))
            builder.setSkullTexture(section.getString("skull-texture"));
        if (section.getBoolean("glow"))
            builder.glow();

        return builder.build();
    }

    public static String applyPlaceHolders(Mail mail, String string) {
        string = string.replaceAll("@time", mail.getDate().toString());
        string = string.replaceAll("@sender", Bukkit.getOfflinePlayer(mail.getSender()).getName());
        string = string.replaceAll("@type", mail.getMailType().toString());
        string = string.replaceAll("@read", String.valueOf(mail.isRead()));
        return string;
    }

    public static List<String> applyPlaceHolders(Mail mail, List<String> string) {
        return string.stream().map(str -> applyPlaceHolders(mail, str)).collect(Collectors.toList());
    }

}
