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
        Quality quality = null;
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
        if (quality != null) {
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
        } else {
            return itemStack;
        }

        return result;
    }

    /**
     * 刷新品质.
     *
     * @param itemStack 需要刷新的物品
     */
    public static void refreshQuality(ItemStack itemStack) {
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
            return;
        }
        if (quality == null) {
            itemStack = createQuality(itemStack);
            return;
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
        //原来的属性描述个数小于新的描述个数
        if (count < stringArrayList.size()) {
            lore.set(index + 1, quality.getColor() + stringArrayList.get(0));
            for (int i = 1; i < stringArrayList.size(); i++) {
                lore.add(index + 1, quality.getColor() + stringArrayList.get(i));
            }//否则先删除,后添加
        } else {
            for (int i = 0; i < count; i++) {
                lore.remove(index + 1);
            }
            for (String s : stringArrayList) {
                lore.add(index + 1, quality.getColor() + s);
            }
        }
        itemMeta.setLore(lore);
        //设置nbt
        nbtSetString(itemMeta, "Quality", quality.name());
        itemStack.setItemMeta(itemMeta);
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
