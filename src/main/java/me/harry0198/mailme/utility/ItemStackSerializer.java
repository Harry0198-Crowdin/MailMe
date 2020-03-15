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
import java.util.Map;

public class ItemStackSerializer implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        byte[] stack = MailMe.GSON.fromJson(json, byte[].class);
        try {
            return fromByteArrayItemStackArray(stack, 1)[0];
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//        Map<String, Object> map = MailMe.GSON.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        return null;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {

        try {
            byte[] is = toByteArrayItemStackArray(new ItemStack[]{src});

            return new JsonPrimitive(MailMe.GSON.toJson(is));
        } catch (IOException io ) {

        }

        return null;
        //return new JsonPrimitive(MailMe.GSON.toJson(src.serialize(), new TypeToken<Map<String, Object>>(){}.getType()));
    }

    public static byte[] toByteArrayItemStackArray(ItemStack[] items) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        BukkitObjectOutputStream out = new BukkitObjectOutputStream(bout);

        for (ItemStack item : items) {
            out.writeObject(item);
        }

        out.flush();
        out.close();
        return bout.toByteArray();
    }

    public static ItemStack[] fromByteArrayItemStackArray(byte[] data, int numItemstacks) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        BukkitObjectInputStream in = new BukkitObjectInputStream(bin);
        ItemStack[] itemArray = new ItemStack[numItemstacks];

        for (int i = 0;i < numItemstacks;i++) {
            itemArray[i] = (ItemStack) in.readObject();
        }

        in.close();
        return itemArray;
    }
}
