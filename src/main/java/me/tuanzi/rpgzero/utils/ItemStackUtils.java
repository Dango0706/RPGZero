package me.tuanzi.rpgzero.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemStackUtils {

    //获取身上穿的装备集合.
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

    //消耗物品耐久度,计算耐久附魔.
    public static void useItems(ItemStack itemStack, int useCount) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta instanceof Damageable damageable) {
                int level = itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
                //耐久等级
                if (level > 0) {
                    //循环损伤次数
                    for (int i = 0; i < useCount; i++) {
                        //盔甲
                        if (itemStack.getType().name().contains("_HELMET")
                                || itemStack.getType().name().contains("_CHESTPLATE")
                                || itemStack.getType().name().contains("_LEGGINGS")
                                || itemStack.getType().name().contains("_BOOTS")
                        ) {
                            if (new Random().nextDouble() <= (0.6 + 0.4 / (level + 1))) {
                                damageable.setDamage(damageable.getDamage() + 1);
                            }
                            //其他
                        } else {
                            //尝试添加损伤
                            if (new Random().nextDouble() <= (1.0 / (level + 1))) {
                                damageable.setDamage(damageable.getDamage() + 1);
                            }
                        }
                    }
                    //没有等级则添加损伤.
                } else {
                    damageable.setDamage(damageable.getDamage() + useCount);
                }
                itemStack.setItemMeta(damageable);
            }
        }
    }

    //设置ItemStack的数量
    public static ItemStack setItemStackCount(ItemStack itemStack, int count) {
        ItemStack itemStack1 = itemStack.clone();
        itemStack1.setAmount(count);
        return itemStack1;
    }

    //清除Lore
    public static void removeItemStackLore(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setLore(null);
            itemStack.setItemMeta(itemMeta);
        }
    }

    //设置显示名称
    public static void setItemStackDisplayName(ItemStack itemStack, String displayName) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(displayName);
            itemStack.setItemMeta(itemMeta);
        }
    }

    //添加Lore
    public static void addItemStackLore(ItemStack itemStack, String... lore) {
        addItemStackLore(itemStack, Arrays.asList(lore));
    }

    //同上
    public static void addItemStackLore(ItemStack itemStack, List<String> lore) {
        if (itemStack.hasItemMeta()) {
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
    }

    //lore里有String str吗?
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
