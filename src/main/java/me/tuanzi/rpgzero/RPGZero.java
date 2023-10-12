package me.tuanzi.rpgzero;

import me.tuanzi.rpgzero.events.DamageEvent;
import me.tuanzi.rpgzero.events.ItemAttributeUpdate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGZero extends JavaPlugin {

    public static JavaPlugin javaPlugin;

    @Override
    public void onEnable() {
        System.out.println("RPGZero已加载!");
        // Plugin startup logic
        javaPlugin = this;
        //event
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ItemAttributeUpdate(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
