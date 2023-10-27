package me.tuanzi.rpgzero.quality;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtSetString;


/**
 * The type Create quality.
 */
public class CreateQuality {

    /**
     * 给物品创建一个品质.
     *
     * @param itemStack 需要被创建的物品
     * @return 创建好的物品
     */
    public static ItemStack createQuality(ItemStack itemStack) {
        ItemStack result = itemStack.clone();
        Quality quality;
        //判断是盔甲还是工具
        String itemName = itemStack.getType().name();
        //工具
        if (itemName.contains("SWORD") || itemName.contains("SHOVEL") || itemName.contains("AXE") || itemName.contains("HOE")) {
            quality = drawQuality("TOOL");
            //弓
        } else if (itemName.contains("BOW")) {
            quality = drawQuality("BOW");
            //盔甲
        } else if (itemName.contains("HELMET") || itemName.contains("CHESTPLATE") || itemName.contains("LEGGINGS") || itemName.contains("BOOTS")) {
            quality = drawQuality("ARMOR");
            //都不是则返回原Item
        } else {
            return itemStack;
        }
        //设置quality
        //设置lore
        ItemMeta itemMeta = result.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore.add(quality.getColor() + "品质:" + quality.getDisplayName());
        String[] b = quality.getSynopsis().split(" ");
        ArrayList<String> a = new ArrayList<>(Arrays.asList(b));
        for (String c : a) {
            lore.add(quality.getColor() + c);
        }
        lore.add("§f==========");
        itemMeta.setLore(lore);
        //设置nbt
        nbtSetString(itemMeta, "Quality", quality.name());
        result.setItemMeta(itemMeta);
        //若没有则返回原stack

        return result;
    }

    /**
     * 刷新品质.
     *
     * @param itemStack1 需要刷新的物品
     */
    public static ItemStack refreshQuality(ItemStack itemStack1) {
        ItemStack itemStack = itemStack1.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        Quality quality = null;
        ArrayList<String> lore;
        int index = 0;
        if (itemMeta != null && itemMeta.getLore() != null) {
            lore = (ArrayList<String>) itemMeta.getLore();
            //获取旧Quality,并获得位置.
            for (int i = 0; i < lore.size(); i++) {
                String b = lore.get(i);
                for (Quality c : Quality.values()) {
                    if (b.equals(c.getColor() + "品质:" + c.getDisplayName())) {
                        quality = c;
                        index = i;
                    }
                }
            }
        } else {
            return itemStack;
        }
        if (quality == null) {
            itemStack = createQuality(itemStack);
            return itemStack;
        }
        int count;
        String[] strings = quality.getSynopsis().split(" ");
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(strings));
        count = stringArrayList.size();

        //获取新quality
        quality = drawQuality(quality.getType());
        //修改lore
        lore.set(index, quality.getColor() + "品质:" + quality.getDisplayName());

        strings = quality.getSynopsis().split(" ");
        stringArrayList = new ArrayList<>(Arrays.asList(strings));
        //删除原本的,添加现有的
        for (int i = 0; i < count; i++) {
            lore.remove(index + 1);
        }
        for (String s : stringArrayList) {
            lore.add(index + 1, quality.getColor() + s);
        }
        itemMeta.setLore(lore);
        //设置nbt
        nbtSetString(itemMeta, "Quality", quality.name());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    /**
     * 抽品质.
     *
     * @param type 工具类型
     * @return 品质
     */
    static Quality drawQuality(String type) {
        ArrayList<Quality> qualities = new ArrayList<>();
        if (Objects.equals(type, "TOOL")) {
            for (Quality quality1 : Quality.values()) {
                if (Objects.equals(quality1.getType(), "TOOL")) {
                    qualities.add(quality1);
                }
            }
        } else if (Objects.equals(type, "ARMOR")) {
            for (Quality quality1 : Quality.values()) {
                if (Objects.equals(quality1.getType(), "ARMOR")) {
                    qualities.add(quality1);
                }
            }
        }
        int totalWeight = 0;
        for (Quality quality : qualities) {
            if (quality.getWeight() < 0) {
                throw new IllegalArgumentException("权重应该是一个正整数");
            }
            totalWeight += quality.getWeight();
        }
        Random rand = new Random();
        int randValue = rand.nextInt(totalWeight);
        int sum = 0;
        for (Quality quality : qualities) {
            sum += quality.getWeight();
            if (randValue < sum) {
                return quality;
            }
        }
        throw new RuntimeException("未正确获取品质!");
    }


}
