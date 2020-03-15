package me.harry0198.mailme.utility;

import com.google.gson.reflect.TypeToken;
import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import org.bukkit.ChatColor;
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

    public static TreeMap<Date, Mail> readJson(File file) {
        try {
            Type dtoListType = new TypeToken<TreeMap<Date, Mail>>() {}.getType();
            FileReader fr = new FileReader(file);
            TreeMap<Date, Mail> list = MailMe.GSON.fromJson(fr, dtoListType);
            fr.close();

            return list == null ? new TreeMap<>() : list;
        } catch (IOException io) {
            io.printStackTrace();
        }
        return new TreeMap<>();
    }


    public static void writeJson(File file, TreeMap<Date, Mail> list) {
        try {

            FileWriter writer = new FileWriter(file);
            MailMe.GSON.toJson(list, writer);
            writer.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
