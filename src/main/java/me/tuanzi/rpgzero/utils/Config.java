package me.tuanzi.rpgzero.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;

public class Config {

    static File playerConfigFile = new File(javaPlugin.getDataFolder(), "player.yml");
    public static FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);
    FileConfiguration config = javaPlugin.getConfig();

    public Config() {
    }

    public static void savePlayerConfig() {
        try {
            playerConfig.save(playerConfigFile);
        } catch (IOException ignored) {

        }
    }

    public static Object getPlayerConfig(Player player, String namespace) {
        return playerConfig.get(player.getDisplayName().toLowerCase() + "." + namespace);
    }

    public static void setPlayerConfig(Player player, String namespace, Object value) {
        playerConfig.set(player.getDisplayName().toLowerCase() + "." + namespace, value);
        savePlayerConfig();
    }
    public static void setPlayerConfig(String  playerName, String namespace, Object value) {
        playerConfig.set(playerName.toLowerCase() + "." + namespace, value);
        savePlayerConfig();
    }

}
