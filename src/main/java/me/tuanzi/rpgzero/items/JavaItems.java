package me.tuanzi.rpgzero.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;

public class JavaItems {

    public static ItemStack SOUL_GEM;
    public static ItemStack SPECTRAL_FRAGMENT;


    static {
        //灵魂之晶 15210000
        SOUL_GEM = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = SOUL_GEM.getItemMeta();
        itemMeta.setDisplayName("§6灵魂之晶");
        List<String> lore = new ArrayList<>();
        lore.add("§7蕴含无尽智慧，触摸心灵深处。");
        lore.add("§7对着摆放在特定位置的信标前使用,");
        lore.add("§7灵魂之晶会散发出神秘光辉，连接灵魂与命运");
        lore.add("§7引领你探索未知的宇宙奥秘。");
        lore.add("§7还不快去试试看?!");
        itemMeta.setCustomModelData(15210000);
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "weaponDraw"), PersistentDataType.BOOLEAN, true);
        itemMeta.setLore(lore);
        SOUL_GEM.setItemMeta(itemMeta);
        //幽光碎片 15210001
        SPECTRAL_FRAGMENT = addEasyItemMeta(Material.ECHO_SHARD,15210001,"§d幽光碎片",
                "§7蕴含着古老灵魂的能量"
                ,"§7如同星辰之光在黑暗中闪耀"
                ,"§7这些碎片或许是大自然赋予我们的礼物"
                ,"§7握在手中，我们或许能够洞悉生命的奥秘"
                ,"§7感悟灵魂的真谛。");

    }

    static ItemStack addEasyItemMeta(Material material, int customModel, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(customModel);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }


}
