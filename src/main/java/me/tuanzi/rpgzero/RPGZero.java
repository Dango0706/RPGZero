package me.tuanzi.rpgzero;

import me.tuanzi.rpgzero.command.mainCommander;
import me.tuanzi.rpgzero.events.*;
import me.tuanzi.rpgzero.gui.ChestGUI;
import me.tuanzi.rpgzero.skills.ItemSkills;
import me.tuanzi.rpgzero.test.TestEvent;
import me.tuanzi.rpgzero.utils.Initialize;
import me.tuanzi.rpgzero.utils.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.geyser.api.GeyserApi;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static me.tuanzi.rpgzero.utils.Config.savePlayerConfig;

public final class RPGZero extends JavaPlugin {

    public static JavaPlugin javaPlugin;

    public static boolean hasGeyser;
    public static boolean hasFloodgate;

    public static Logger logger;

    public static GeyserApi geyserApi;
    public static FloodgateApi floodgateApi;

    public static boolean debug = false;
    /*
     * customModel:15210000~15219999
     *
     * */

    @Override
    public void onEnable() {
        // Plugin startup logic
        javaPlugin = this;
        //启用debug?
        debug = getConfig().getBoolean("debug");
        //检测有无Geyser
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
        logger = getLogger();
        //初始化
        new Initialize();
        savePlayerConfig();
        //log相关
        if (getConfig().getBoolean("log")) {
            getLogger().setLevel(Level.ALL);
            FileHandler fileHandler;
            //创建文件夹
            if(!new File(this.getDataFolder() + "/debug").exists())
                new File(this.getDataFolder() + "/debug").mkdirs();
            //增加文件目录
            try {
                fileHandler = new FileHandler(this.getDataFolder() + "/debug/debug.log");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 创建一个SimpleFormatter对象
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            // 将SimpleFormatter对象设置为FileHandler对象的格式化器
            fileHandler.setFormatter(simpleFormatter);
            // add handler
            logger.addHandler(fileHandler);
        }

        //event
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ItemAttributeUpdate(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSwapItem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDrawEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSetDefaultConfigEvent(), this);
        Bukkit.getPluginManager().registerEvents(new dropFragment(), this);
        Bukkit.getPluginManager().registerEvents(new EntitySpawnEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ChestGUI(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEatFoodEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ItemSkills(), this);
        if(debug){
            Bukkit.getPluginManager().registerEvents(new TestEvent(), this);
        }
        //recipe
        new Recipe();
        //command
        if (Bukkit.getPluginCommand("rpg") != null) {
            Bukkit.getPluginCommand("rpg").setExecutor(new mainCommander());
        }
        Objects.requireNonNull(Bukkit.getPluginCommand("rpg")).setTabCompleter(new mainCommander());
        logger.info("RPGZero已加载!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.resetRecipes();
        logger.info("RPGZero已关闭!");
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

}
