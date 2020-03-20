package me.harry0198.mailme.utility;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;

public class ItemStackSerializer implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (!json.isJsonPrimitive()) {
            return null;
        }

        try (final ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(json.getAsString())); final BukkitObjectInputStream ois = new BukkitObjectInputStream(bis)) {
            return (ItemStack) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {

        if (src == null) {
            return JsonNull.INSTANCE;
        }

        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(); final BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos)) {
            oos.writeObject(src);
            return new JsonPrimitive(Base64.getEncoder().encodeToString(bos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonNull.INSTANCE;
    }
}