package me.tuanzi.rpgzero;

import me.tuanzi.rpgzero.command.mainCommander;
import me.tuanzi.rpgzero.events.*;
import me.tuanzi.rpgzero.gui.ChestGUI;
import me.tuanzi.rpgzero.utils.Initialize;
import me.tuanzi.rpgzero.utils.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.geyser.api.GeyserApi;

import java.util.Objects;

import static me.tuanzi.rpgzero.utils.Config.savePlayerConfig;

public final class RPGZero extends JavaPlugin {

    public static JavaPlugin javaPlugin;

    public static boolean hasGeyser;
    public static boolean hasFloodgate;


    public static GeyserApi geyserApi;
    public static FloodgateApi floodgateApi;

    /*
     * customModel:15210000~15219999
     *
     * */

    @Override
    public void onEnable() {
        // Plugin startup logic
        javaPlugin = this;
        if (Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null && Bukkit.getPluginManager().getPlugin("Geyser-Spigot").isEnabled()) {
            hasGeyser = true;
            geyserApi = GeyserApi.api();
            if (Bukkit.getPluginManager().getPlugin("floodgate") != null && Bukkit.getPluginManager().getPlugin("floodgate").isEnabled()) {
                hasFloodgate = true;
                floodgateApi = FloodgateApi.getInstance();
            } else {
                hasFloodgate = false;
                getLogger().info("Floodgate未加载...");
            }

        } else {
            hasGeyser = false;
            getLogger().warning("间歇泉未加载!无法使用基岩版等相关服务!");
        }

        new Initialize();

        savePlayerConfig();
        //event
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ItemAttributeUpdate(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSwapItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDrawEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSetDefaultConfigEvent(), this);
        Bukkit.getPluginManager().registerEvents(new dropFragment(), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChestGUI(), this);
        //recipe
        new Recipe();
        //command
        if (Bukkit.getPluginCommand("rpg") != null) {
            Bukkit.getPluginCommand("rpg").setExecutor(new mainCommander());
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("rpg")).setTabCompleter(new mainCommander());
        //geyser

        getLogger().info("RPGZero已加载!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.resetRecipes();
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }





}
