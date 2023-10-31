package me.tuanzi.rpgzero.attributes;

import me.tuanzi.rpgzero.draw.Rarity;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.*;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;

public class CreateItemAttributes {


    /**
     * 给物品创建一个Attribute
     * 附属在物品nbt上.
     *
     * @param itemStack1 the item stack 1
     * @param refresh    the refresh
     * @return the item stack
     */
    public static ItemStack createItemAttributes(ItemStack itemStack1, boolean refresh) {
        ItemStack itemStack = itemStack1.clone();
        //物品稀有度
        int rarity;
        ItemMeta itemMeta = itemStack.getItemMeta();
        //lore
        ArrayList<String> lore = new ArrayList<>();
        if (itemMeta != null) {
            if (itemMeta.getLore() != null) {
                lore = (ArrayList<String>) itemMeta.getLore();
            }
            //获取稀有度
            if (nbtGetString(itemMeta, "Rarity").equals(Rarity.SINGULAR.name())) {
                rarity = 1;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.EXQUISITE.name())) {
                rarity = 2;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MYTHIC.name())) {
                rarity = 3;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MAJESTIC.name())) {
                rarity = 4;
            } else if (nbtGetString(itemMeta, "Rarity").equals("null")) {
                nbtSetString(itemMeta, "Rarity", Rarity.SINGULAR.name());
                rarity = 1;
            } else {
                rarity = 5;
            }
            String type;
            //判断是盔甲还是工具
            String itemName = itemStack.getType().name();
            //工具
            if (itemName.contains("SWORD") || itemName.contains("SHOVEL") || itemName.contains("AXE") || itemName.contains("HOE")) {
                type = "TOOL";
                //弓
            } else if (itemName.contains("BOW")) {
                type = "BOW";
                //盔甲
            } else if (itemName.contains("HELMET") || itemName.contains("CHESTPLATE") || itemName.contains("LEGGINGS") || itemName.contains("BOOTS")) {
                type = "ARMOR";
                //都不是则返回原Item
            } else {
                return itemStack;
            }
            //基础属性设定
            //创建池子
            List<ItemAttributes> itemAttributes = new ArrayList<>();
            //向池子里添加
            for (ItemAttributes itemAttribute : ItemAttributes.values()) {
                if (itemAttribute.getType().equals("ALL")) {
                    itemAttributes.add(itemAttribute);
                } else if (itemAttribute.getType().equals(type)) {
                    itemAttributes.add(itemAttribute);
                }
            }
            //从池子中选择3样
            Random random = new Random();
            Set<ItemAttributes> result = new HashSet<>();
            while (result.size() < 3) {
                int totalWeight = itemAttributes.stream().mapToInt(ItemAttributes::getWeight).sum();
                int randomWeight = random.nextInt(totalWeight) + 1;
                int currentWeight = 0;
                ItemAttributes selectedPrize = null;
                for (ItemAttributes prize : itemAttributes) {
                    currentWeight += prize.getWeight();
                    if (currentWeight >= randomWeight) {
                        selectedPrize = prize;
                        break;
                    }
                }
                if (selectedPrize != null) {
                    result.add(selectedPrize);
                }
            }
            int minLocation = -1;
            int maxLocation = -1;
            if (refresh) {
                //清除所有Attribute
                for (ItemAttributes itemAttributes1 : ItemAttributes.values()) {
                    itemMeta.getPersistentDataContainer().remove(new NamespacedKey(javaPlugin, itemAttributes1.name()));
                }
                //重置
                for (int i = 0; i < lore.size(); i++) {
                    if (lore.get(i).contains("Lv:")) {
                        minLocation = i;
                    }
                    if (lore.get(i).contains("Exp:")) {
                        maxLocation = i;
                    }
                }
                if (minLocation != -1 && maxLocation != -1) {
                    for (int i = minLocation; i < maxLocation; i++) {
                        lore.remove(i);
                    }
                } else {
                    return itemStack1;
                }
                lore.add(minLocation, "§f=====Lv:1=====");
            }



            if (!refresh) {
                lore.add("§f=====Lv:1=====");
            }
            //设置lore与nbt
            for (ItemAttributes itemAttribute : result) {
                nbtSetDouble(itemMeta, itemAttribute.name(), calculationUpdateAttributes(itemAttribute));
                //取2位小数
                double value = nbtGetDouble(itemMeta, itemAttribute.name());
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String number = decimalFormat.format(value);
                if (itemAttribute == ItemAttributes.ATTACK_DAMAGE || itemAttribute == ItemAttributes.DEFENSE) {
                    if (!refresh) {
                        lore.add("§a" + itemAttribute.getDisplayName() + "+" + number);
                    } else {
                        lore.add(minLocation, "§a" + itemAttribute.getDisplayName() + "+" + number);
                    }

                } else if (itemAttribute == ItemAttributes.ATTACK_SPEED) {
                    if (!refresh) {
                        lore.add("§a" + itemAttribute.getDisplayName() + "+" + number + "%");
                    } else {
                        lore.add(minLocation, "§a" + itemAttribute.getDisplayName() + "+" + number + "%");

                    }
                } else {
                    number = decimalFormat.format(value * 100);
                    if (!refresh) {
                        lore.add("§a" + itemAttribute.getDisplayName() + "+" + number + "%");
                    } else {
                        lore.add(minLocation, "§a" + itemAttribute.getDisplayName() + "+" + number + "%");
                    }

                }
            }
            nbtSetInteger(itemMeta, "AttributeExp", 0);
            nbtSetInteger(itemMeta, "AttributeLevel", 1);
            if (!refresh) {
                lore.add("§f=====Exp:0=====");
            } else {
                lore.add(minLocation, "§f=====Exp:0=====");
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack updateAttributes(ItemStack itemStack1) {
        ItemStack itemStack = itemStack1.clone();
        if (itemStack.getItemMeta() == null) {
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        int rank = new Random().nextInt(3);
        //3,4,5
        for (ItemAttributes i : ItemAttributes.values()) {
            //相等即为升级
            //todo:根据权重升级
            if (lore.get(3 + rank).contains(i.getDisplayName())) {
                //升级多少计算
                double up = calculationUpdateAttributes(i);
                //设置nbt
                nbtSetDouble(itemMeta, i.name(), nbtGetDouble(itemMeta, i.name()) + up);
                //取2位小数,设置lore
                double value = nbtGetDouble(itemMeta, i.name());
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String number = decimalFormat.format(value);
                if (i == ItemAttributes.ATTACK_DAMAGE || i == ItemAttributes.DEFENSE) {
                    lore.set((3 + rank), "§a" + i.getDisplayName() + "+" + number);
                } else if (i == ItemAttributes.ATTACK_SPEED) {
                    lore.set((3 + rank), "§a" + i.getDisplayName() + "+" + number + "%");
                } else {
                    number = decimalFormat.format(value * 100);
                    lore.set((3 + rank), "§a" + i.getDisplayName() + "+" + number + "%");
                }
            }
        }
        //设置回去
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    static Double calculationUpdateAttributes(ItemAttributes attributes) {
        double a = attributes.getMin();
        Random random = new Random();
        int count = 1;
        double cha = (attributes.getMax() - a) / 4;
        while (a < attributes.getMax()) {
            if (random.nextDouble() < (1 - count * 0.25)) {
                count += 1;
                a += cha;
            } else {
                break;
            }
        }
        return a;
    }


    public static ItemStack refreshAttributes(ItemStack itemStack1) {
        ItemStack itemStack = itemStack1.clone();
//        ItemMeta itemMeta = itemStack.getItemMeta();
//        List<String> lore = itemMeta.getLore();

/*        for (int i = 0; i < 5; i++) {
            if (lore.size() < 3) {
                return itemStack;
            }
            lore.remove(2);
        }*/

/*        for (ItemAttributes itemAttribute : ItemAttributes.values()) {
            nbtSetDouble(itemMeta, itemAttribute.name(), 0);
        }*/

//        itemMeta.setLore(lore);
//        itemStack.setItemMeta(itemMeta);
        //添加上
        itemStack = createItemAttributes(itemStack, true);
        return itemStack;
    }


}
