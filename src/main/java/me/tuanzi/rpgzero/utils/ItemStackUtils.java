package me.tuanzi.rpgzero.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackUtils {

    public static ArrayList<ItemStack> getEquipments(LivingEntity livingEntity) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        ItemStack helmet = livingEntity.getEquipment().getHelmet();
        ItemStack chest = livingEntity.getEquipment().getChestplate();
        ItemStack leg = livingEntity.getEquipment().getLeggings();
        ItemStack boot = livingEntity.getEquipment().getBoots();
        itemStacks.add(helmet);
        itemStacks.add(chest);
        itemStacks.add(leg);
        itemStacks.add(boot);
        return itemStacks;
    }


    public static ItemStack setItemStackCount(ItemStack itemStack, int count) {
        ItemStack itemStack1 = itemStack.clone();
        itemStack1.setAmount(count);
        return itemStack1;
    }

    public static void removeItemStackLore(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
    }

    public static void setItemStackDisplayName(ItemStack itemStack, String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
    }

    public static void addItemStackLore(ItemStack itemStack, String... lore) {
        addItemStackLore(itemStack, Arrays.asList(lore));
    }

    public static void addItemStackLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> list;
        if (itemMeta.getLore() != null)
            list = itemMeta.getLore();
        else
            list = new ArrayList<>();
        list.addAll(lore);
        itemMeta.setLore(list);
        itemStack.setItemMeta(itemMeta);
    }
    //lore里有String吗?
    public static boolean isStringInTheLore(ItemStack itemStack,String str){
        if(itemStack.hasItemMeta()){
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if(lore==null)return false;
            return lore.contains(str);
        }
        return false;
    }




}
