package me.harry0198.mailme.utility;

import me.harry0198.mailme.MailMe;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Locale {

    private final Map<LANG, YamlConfiguration> yaml = new HashMap<>();
    private static final String[] LANGUAGES = {"EN", "DE"};
    private LANG serverLang;

    public Locale() {
        serverLang = LANG.valueOf(MailMe.getInstance().getConfig().getString("lang"));
        System.out.println(serverLang);
        for (String lang : LANGUAGES) {
            if (!new File(MailMe.getInstance().getDataFolder() + "/languages", lang + ".yml").exists()) {
                MailMe.getInstance().saveResource("languages/" + lang + ".yml", false);
            }
            this.yaml.put(LANG.valueOf(lang),YamlConfiguration.loadConfiguration(new File(MailMe.getInstance().getDataFolder() + "/languages/" + lang + ".yml")) );
        }
    }

    public String getMessage(String string) {
        return Utils.colour(this.yaml.get(serverLang).getString(string));
    }

    public List<String> getMessages(String string) {
        return Utils.colourList(this.yaml.get(serverLang).getStringList(string));
    }

    public LANG getServerLang() {
        return serverLang;
    }

    public enum LANG {
        EN, DE, ES, FR, PT
    }
}
