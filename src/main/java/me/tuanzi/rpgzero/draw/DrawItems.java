package me.tuanzi.rpgzero.draw;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.attributes.CreateItemAttributes.refreshAttributes;
import static me.tuanzi.rpgzero.quality.CreateQuality.refreshQuality;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetBoolean;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetString;

public class DrawItems {


    public DrawItems() {

    }


    //抽卡  五星ItemStack组 四星ItemStack组
    public static ItemStack drawItem(Player player) {
        ItemStack result = new ItemStack(Material.AIR);
        //总抽数
        int totalCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "TotalDrawCount"), PersistentDataType.INTEGER, 1) + 1;
        //紫色抽数
        int purpleCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 1) + 1;
        //金色抽数
        int goldenCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 1) + 1;
        //是紫保底吗
        boolean isListPurple = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, false);
        //是金保底吗
        boolean isListGolden = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, false);

        //所有常驻三星四星五星
        List<ItemStack> allBlue = new ArrayList<>();
        List<ItemStack> allPurple = new ArrayList<>();
        List<ItemStack> allGolden = new ArrayList<>();
        //up
        List<ItemStack> upPurple = new ArrayList<>();
        List<ItemStack> upGolden = new ArrayList<>();
        //添加卡池
        Class<DrawPools> poolsClass = DrawPools.class;
        Field[] fields = poolsClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == ItemStack.class
                    && java.lang.reflect.Modifier.isStatic(field.getModifiers())
                    && java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                try {
                    ItemStack itemStack = (ItemStack) field.get(null);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    String rarity = nbtGetString(itemMeta, "Rarity");
                    //todo:使用配置文件?
                    if (rarity.equals(Rarity.MYTHIC.name())) {
                        allBlue.add(itemStack);
                    } else if (rarity.equals(Rarity.MAJESTIC.name())) {
                        if (nbtGetBoolean(itemMeta, "IsUP") && nbtGetBoolean(itemMeta, "Uping")) {
                            upPurple.add(itemStack);
                        } else {
                            allPurple.add(itemStack);
                        }
                    } else if (rarity.equals(Rarity.SUPREME.name())) {
                        if (nbtGetBoolean(itemMeta, "IsUP") && nbtGetBoolean(itemMeta, "Uping")) {
                            upGolden.add(itemStack);
                        } else {
                            allGolden.add(itemStack);
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }

        //抽卡
        Random random = new Random();
        double rank = random.nextDouble();
        rank *= 100;
        //设置
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, goldenCount);
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, purpleCount);
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "TotalDrawCount"), PersistentDataType.INTEGER, totalCount);
        //保底计算
        if (goldenCount >= 90) {
            return getGoldenItemStack(player, isListGolden, allGolden, upGolden, random);
        } else {
            //不是金保底 计算紫保底
            if (purpleCount >= 10) {
                return getPurpleItemStack(player, isListPurple, allPurple, upPurple, upGolden, random);
            } else {
                double goldenRank = 0.6;
                if (goldenCount >= 60) {
                    goldenRank += (goldenCount - 60) * 0.2;
                    if (goldenCount >= 70) {
                        goldenRank += (goldenCount - 70) * 0.75;
                    }
                }
                //不是金也不是紫保底
                if (rank <= goldenRank) {
                    return getGoldenItemStack(player, isListGolden, allGolden, upGolden, random);
                } else if (rank <= 10 + goldenRank) {
                    return getPurpleItemStack(player, isListPurple, allPurple, upPurple, upGolden, random);
                } else {
                    ItemStack itemStack;
                    itemStack = allBlue.get(random.nextInt(allBlue.size()));
                  itemStack=  refreshQuality(itemStack);
                  itemStack=  refreshAttributes(itemStack);
                    //金紫都没出
                    return itemStack;
                }

            }

        }

    }

    private static ItemStack getPurpleItemStack(Player player, boolean isListPurple, List<ItemStack> allPurple, List<ItemStack> upPurple, List<ItemStack> upGolden, Random random) {
        int ran = random.nextInt(upPurple.size());
        ItemStack itemStack;
        if (isListPurple) {
            //设置为0
            player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 0);
            //up设为false
            player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, false);
            itemStack = upPurple.get(ran);
        } else {
            if (random.nextBoolean()) {
                //设置为0
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 0);
                //up设为false
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, false);
                itemStack = upPurple.get(ran);
            } else {
                ran = random.nextInt(allPurple.size());
                //设置为0
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 0);
                //up设为true
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, true);
                itemStack = allPurple.get(ran);
            }
        }
       itemStack = refreshQuality(itemStack);
      itemStack=  refreshAttributes(itemStack);

        return itemStack;
    }

    private static ItemStack getGoldenItemStack(Player player, boolean isListGolden, List<ItemStack> allGolden, List<ItemStack> upGolden, Random random) {
        int ran = random.nextInt(upGolden.size());
        ItemStack itemStack;
        if (isListGolden) {
            //设置为0
            player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 0);
            //up设为false
            player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, false);
            itemStack = upGolden.get(ran);
        } else {
            if (random.nextBoolean()) {
                //设置为0
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 0);
                //up设为false
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, false);
                itemStack = upGolden.get(ran);
            } else {
                ran = random.nextInt(allGolden.size());
                //设置为0
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 0);
                //up设为true
                player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, true);
                itemStack = allGolden.get(ran);
            }
        }
        itemStack = refreshQuality(itemStack);
        itemStack =  refreshAttributes(itemStack);

        return itemStack;
    }


}
