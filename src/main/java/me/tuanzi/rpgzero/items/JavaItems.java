package me.tuanzi.rpgzero.items;

import me.tuanzi.rpgzero.draw.Rarity;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.draw.CreateItemStack.createMiscItemStack;

public class JavaItems {

    public static ItemStack SOUL_GEM;
    public static ItemStack SPECTRAL_FRAGMENT;


    static {
        //灵魂之晶 15210000
        SOUL_GEM =createMiscItemStack(Material.EMERALD,Rarity.SUPREME,15210000,1,"§6灵魂之晶","§7蕴含无尽智慧，触摸心灵深处。",
                "§7对着摆放在特定位置的信标前使用,",
                "§7灵魂之晶会散发出神秘光辉，连接灵魂与命运",
                "§7引领你探索未知的宇宙奥秘。",
                "§7还不快去试试看?!");
        ItemMeta itemMeta = SOUL_GEM.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "weaponDraw"), PersistentDataType.BOOLEAN, true);
        SOUL_GEM.setItemMeta(itemMeta);
        //幽光碎片 15210001
        SPECTRAL_FRAGMENT = createMiscItemStack(Material.ECHO_SHARD, Rarity.MAJESTIC,15210001,1,"§d幽光碎片",
                "§7蕴含着古老灵魂的能量"
                ,"§7如同星辰之光在黑暗中闪耀"
                ,"§7这些碎片或许是大自然赋予我们的礼物"
                ,"§7握在手中，我们或许能够洞悉生命的奥秘"
                ,"§7感悟灵魂的真谛。");

    }

}
