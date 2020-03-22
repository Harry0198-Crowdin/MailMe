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

package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailBuilder;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public final class SoundInput extends StringPrompt {

    public String getPromptText(ConversationContext context) {
        PlayerData data = MailMe.getInstance().getPlayerDataHandler().getPlayerData((Player) context.getForWhom());
        return MailMe.getInstance().getLocale().getMessage(data.getLang(), "mail.sound-input");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String s) {
        if (s.equals("cancel")) {
            return Prompt.END_OF_CONVERSATION;
        }
        Sound sound;
        try {
            sound = Sound.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new SoundInput();
        }

        Player player = (Player) context.getForWhom();
        try {
            Mail mail = MailBuilder.getMailDraft(player).setSound(sound).build();
            assert mail != null;
            context.setSessionData("mail", mail);
        } catch (IncompleteBuilderException ignored) { }
        return Prompt.END_OF_CONVERSATION;
    }
}
