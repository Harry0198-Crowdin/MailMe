package me.harry0198.mailme.utility;

import org.bukkit.configuration.file.YamlConfiguration;

public final class Locale {

    private final YamlConfiguration yaml;
    private final String language;

    public Locale(YamlConfiguration yaml) {
        this.yaml = yaml;
        this.language = yaml.getString("language");
    }

    public String getMessage(String string) {
        return Utils.colour(yaml.getString(string));
    }
}
