package me.harry0198.mailme.utility;

import me.harry0198.mailme.MailMe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Locale {

    private final Map<LANG, YamlConfiguration> yaml = new HashMap<>();
    private final LANG serverLang;

    public Locale() {
        serverLang = LANG.valueOf(MailMe.getInstance().getConfig().getString("lang"));
        System.out.println(serverLang);
        for (LANG lang : LANG.values()) {
            System.out.println(lang);
            System.out.println(lang.toString());
            if (!new File(MailMe.getInstance().getDataFolder() + "/languages", lang.toString() + ".yml").exists()) {
                MailMe.getInstance().saveResource("languages/" + lang.toString() + ".yml", false);
            }
            this.yaml.put(lang,YamlConfiguration.loadConfiguration(new File(MailMe.getInstance().getDataFolder() + "/languages/" + lang.toString() + ".yml")) );
        }
    }

    public String getMessage(String string) {
        return getMessage(serverLang, string);
    }

    public String getMessage(LANG lang, String string) {
        String msg = this.yaml.get(lang).getString(string);
        return Utils.colour(msg.replaceAll("@prefix", this.yaml.get(lang).getString("prefix")));
    }

    public List<String> getMessages(String string) {
        return getMessages(serverLang, string);
    }

    public List<String> getMessages(LANG lang, String string) {
        List<String> msg = this.yaml.get(lang).getStringList(string);
        msg.forEach(e -> e.replaceAll("@prefix", getMessage(lang,"prefix")));
        return Utils.colourList(msg);
    }

    public ConfigurationSection getConfigurationSection(LANG lang, String path) {
        return this.yaml.get(lang).getConfigurationSection(path);
    }

    public enum LANG {
        EN, DE
    }
}
