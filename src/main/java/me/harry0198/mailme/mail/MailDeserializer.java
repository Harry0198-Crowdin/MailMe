package me.harry0198.mailme.mail;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MailDeserializer implements JsonDeserializer<Mail> {

    @Override
    public Mail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String className =  this.getClass().getPackage().getName() + ".types." + json.getAsJsonObject().get("type").getAsString();

        try {
            return context.deserialize(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
