package me.harry0198.mailme.utility;

import me.harry0198.mailme.mail.Mail;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.Map;

public class Utils {

    public static String colour(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static Mail mailObjFromString(String s ) {
        try {
            byte[] data = Base64.getDecoder().decode(s);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Mail o = (Mail) ois.readObject();
            ois.close();
            return o;
        } catch (IOException | ClassNotFoundException io) {
            io.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> serializeItemStack(ItemStack stack) {
        return stack.serialize();
    }

    public static ItemStack deserializeItemStack(Map<String, Object> serializedMap) {
        return ItemStack.deserialize(serializedMap);
    }
}
