/*
 *   Copyright [2020] [Harry0198]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
