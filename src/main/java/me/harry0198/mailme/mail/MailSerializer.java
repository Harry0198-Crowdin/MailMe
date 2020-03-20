package me.harry0198.mailme.mail;

import com.google.gson.*;
import me.harry0198.mailme.MailMe;

import java.lang.reflect.Type;

public class MailSerializer implements JsonDeserializer<Mail>, JsonSerializer<Mail> {

    @Override
    public Mail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String className =  this.getClass().getPackage().getName() + ".types." + json.getAsJsonObject().get("type").getAsString();

        try {
            return context.deserialize(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(Mail src, Type typeOfSrc, JsonSerializationContext context) {
        return MailMe.GSON.toJsonTree(src);
    }
}
