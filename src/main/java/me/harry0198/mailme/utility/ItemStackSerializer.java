package me.harry0198.mailme.utility;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.harry0198.mailme.MailMe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class ItemStackSerializer implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        if (!json.isJsonPrimitive())
        {
            return null;
        }

        try (final ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(json.getAsString())); final BukkitObjectInputStream ois = new BukkitObjectInputStream(bis))
        {
            return (ItemStack) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {

        if (src == null)
        {
            return JsonNull.INSTANCE;
        }

        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(); final BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos))
        {
            oos.writeObject(src);
            return new JsonPrimitive(Base64.getEncoder().encodeToString(bos.toByteArray()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return JsonNull.INSTANCE;
    }

//    public static byte[] toByteArrayItemStackArray(ItemStack[] items) throws IOException {
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        BukkitObjectOutputStream out = new BukkitObjectOutputStream(bout);
//
//        for (ItemStack item : items) {
//            out.writeObject(item);
//        }
//
//        out.flush();
//        out.close();
//        return bout.toByteArray();
//    }
//
//    public static ItemStack[] fromByteArrayItemStackArray(byte[] data, int numItemstacks) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream bin = new ByteArrayInputStream(data);
//        BukkitObjectInputStream in = new BukkitObjectInputStream(bin);
//        ItemStack[] itemArray = new ItemStack[numItemstacks];
//
//        for (int i = 0;i < numItemstacks;i++) {
//            itemArray[i] = (ItemStack) in.readObject();
//        }
//
//        in.close();
//        return itemArray;
//    }
}
