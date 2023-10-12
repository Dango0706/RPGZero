package me.tuanzi.rpgzero.draw;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;

public class DrawItems {

    //抽卡  五星ItemStack组 四星ItemStack组
    public static ItemStack drawItem(ArrayList<ItemStack> GoldenItems , ArrayList<ItemStack> PurpleItems, Player player){
        ItemStack result = new ItemStack(Material.AIR);
        //总抽数
        int totalCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin,"TotalDrawCount"), PersistentDataType.INTEGER,0);
        //紫色抽数
        int purpleCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin,"PurpleDrawCount"), PersistentDataType.INTEGER,0);
        //金色抽数
        int goldenCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin,"GoldenDrawCount"), PersistentDataType.INTEGER,0);
        //是紫保底吗
        boolean isListPurple = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin,"isListPurple"), PersistentDataType.BOOLEAN,false);
        //是金保底吗
        boolean isListGolden = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin,"isListGolden"), PersistentDataType.BOOLEAN,false);






        return result;
    }





}
