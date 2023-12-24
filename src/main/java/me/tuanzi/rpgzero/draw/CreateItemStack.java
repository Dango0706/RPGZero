package me.tuanzi.rpgzero.draw;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static me.tuanzi.rpgzero.attributes.CreateItemAttributes.createItemAttributes;
import static me.tuanzi.rpgzero.quality.CreateQuality.createQuality;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;
import static me.tuanzi.rpgzero.utils.utils.formatNumber;

/**
 * The type Create item stack.
 */
public class CreateItemStack {



    public static ItemStack createDisplayItemStack(Material material, Integer customModel, String name, String... description) {
        return createDisplayItemStack(material, customModel, name, Arrays.asList(description));
    }

    public static ItemStack createDisplayItemStack(Material material, Integer customModel, String name, List<String> description) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (name != null)
                itemMeta.setDisplayName(name);
            if (customModel != 0)
                itemMeta.setCustomModelData(customModel);
            //将lore还给Item
            if (description != null)
                itemMeta.setLore(description);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static ItemStack createFoodItemStack(Material material, Rarity rarity, Integer customModel, Integer count, Integer foodLevel, float saturation, String name, List<String> description) {
        ItemStack itemStack = new ItemStack(material);
        if (count > 0)
            itemStack.setAmount(count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            //设置nbt
            //稀有度
            nbtSetString(itemMeta, "Rarity", rarity.name());
            //是新的吗?
            nbtSetBoolean(itemMeta, "isNew", true);
            //设置饱食度与饱和度
            nbtSetInteger(itemMeta, "FoodLevel", foodLevel);
            nbtSetFloat(itemMeta, "Saturation", saturation);
            //设置名字
            if (name != null)
                itemMeta.setDisplayName(rarity.getColor() + name);
            if (customModel != 0)
                itemMeta.setCustomModelData(customModel);
            //设置Lore
            List<String> lore = itemMeta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            if (description != null)
                lore.addAll(description);
            //将lore还给Item
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createFoodItemStack(Material material, Rarity rarity, Integer customModel, Integer count, Integer foodLevel, float saturation, String name, String... description) {
        return createFoodItemStack(material, rarity, customModel, count, foodLevel, saturation, name, Arrays.asList(description));
    }

    public static ItemStack createMiscItemStack(Material material, Rarity rarity, Integer customModel, Integer count, String name, String... description) {
        return createMiscItemStack(material, rarity, customModel, count, name, Arrays.asList(description));
    }

    public static ItemStack createMiscItemStack(Material material, Rarity rarity, boolean isUP, boolean uping, Integer customModel, Integer count, String name, String... description) {
        ItemStack itemStack = createMiscItemStack(material, rarity, customModel, count, name, (String) null);
        ItemMeta itemMeta = itemStack.getItemMeta();
        //是否是up
        nbtSetBoolean(itemMeta, "IsUP", isUP);
        //正在up?
        nbtSetBoolean(itemMeta, "Uping", uping);
        //添加属性(词条)
        itemStack = createItemAttributes(itemStack, false).clone();
        //添加quality
        itemStack = createQuality(itemStack).clone();

        itemMeta = itemStack.getItemMeta();
        //添加简介
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        if (description != null)
            lore.addAll(List.of(description));
        //将lore还给Item
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack.clone();
    }


    public static ItemStack createMiscItemStack(Material material, Rarity rarity, Integer customModel, Integer count, String name, List<String> description) {
        ItemStack itemStack = new ItemStack(material);
        if (count > 0)
            itemStack.setAmount(count);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            //设置nbt
            //稀有度
            nbtSetString(itemMeta, "Rarity", rarity.name());
            //是新的吗?
            nbtSetBoolean(itemMeta, "isNew", true);
            //设置名字
            if (name != null)
                itemMeta.setDisplayName(rarity.getColor() + name);
            if (customModel != 0)
                itemMeta.setCustomModelData(customModel);
            //设置Lore
            List<String> lore = itemMeta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add("§f稀有度:" + rarity.getColor() + rarity.getDisplayName());
            if (description != null)
                lore.addAll(description);
            //将lore还给Item
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createSwordItemStack(Rarity rarity, ItemType itemType, boolean isUP, boolean uping, Integer customModel, double attackDamage, double attackSpeed, String name, String... description) {
        return createSwordItemStack(rarity, itemType, isUP, uping, customModel, attackDamage, attackSpeed, name, Arrays.asList(description));
    }


    /**
     * 便捷生成剑的物品
     *
     * @param rarity       稀有度
     * @param itemType     物品类型
     * @param name         物品名称
     * @param customModel  自定义模型
     * @param attackDamage 攻击力
     * @param attackSpeed  攻击速度
     * @return 生成的物品
     */
    public static ItemStack createSwordItemStack(Rarity rarity, ItemType itemType, boolean isUP, boolean uping, Integer customModel, double attackDamage, double attackSpeed, String name, List<String> description) {
        ItemStack itemStack = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            //设置nbt
            //稀有度
            nbtSetString(itemMeta, "Rarity", rarity.name());
            //是否是up
            nbtSetBoolean(itemMeta, "IsUP", isUP);
            //正在up?
            nbtSetBoolean(itemMeta, "Uping", uping);
            //是新的吗?
            nbtSetBoolean(itemMeta, "isNew", true);
            //设置名字
            itemMeta.setDisplayName(rarity.getColor() + name);
            //修改伤害与攻速
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", attackDamage - 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", attackSpeed - 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            //设置自定义模型
            itemMeta.setCustomModelData(customModel);
            //设置Lore
            List<String> lore = itemMeta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add("§f稀有度:" + rarity.getColor() + rarity.getDisplayName());
            lore.add("§f装备类型:§b" + itemType.getDisplayName());
            //将lore还给Item
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            //添加属性(词条)
            itemStack = createItemAttributes(itemStack, false);
            //添加quality
            itemStack = createQuality(itemStack);
            itemMeta = itemStack.getItemMeta();
            lore = itemStack.getItemMeta().getLore();
            //添加简介/介绍
            lore.addAll(description);
            //添加attribute
            lore.add("");
            lore.add("§7在主手时:");
            lore.add("§2 " + formatNumber(attackDamage) + " 攻击伤害");
            lore.add("§2 " + attackSpeed + " 攻击速度");
            //清除显示
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setLore(lore);
            //还给ItemStack
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }


    public static ItemStack createArmorItemStack(Rarity rarity, String name, Integer customModel, double armor, double toughness, ItemType itemType, List<String> description) {
        ItemStack itemStack;
        ItemMeta itemMeta;

        if (itemType == ItemType.HELMET) {
            itemStack = new ItemStack(Material.NETHERITE_HELMET);
            itemMeta = itemStack.getItemMeta();
            //修改伤害与攻速
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Weapon modifier", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        } else if (itemType == ItemType.CHESTPLATE) {
            itemStack = new ItemStack(Material.NETHERITE_CHESTPLATE);
            itemMeta = itemStack.getItemMeta();
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Weapon modifier", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
        } else if (itemType == ItemType.LEGGINGS) {
            itemStack = new ItemStack(Material.NETHERITE_LEGGINGS);
            itemMeta = itemStack.getItemMeta();
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Weapon modifier", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
        } else if (itemType == ItemType.BOOTS) {
            itemStack = new ItemStack(Material.NETHERITE_BOOTS);
            itemMeta = itemStack.getItemMeta();
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "Weapon modifier", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        } else {
            throw new RuntimeException("itemType不正确!");
        }

        //设置nbt
        //稀有度
        nbtSetString(itemMeta, "Rarity", rarity.name());
        //设置名字
        itemMeta.setDisplayName(rarity.getColor() + name);
        //设置自定义模型
        itemMeta.setCustomModelData(customModel);
        //是新的吗?
        nbtSetBoolean(itemMeta, "isNew", true);
        //设置Lore
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add("§f稀有度:" + rarity.getColor() + rarity.getDisplayName());
        lore.add("§f装备类型:§b盔甲");
        //将lore还给Item
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        //添加属性(词条)
        itemStack = createItemAttributes(itemStack, false);
        //添加quality
        itemStack = createQuality(itemStack);
        itemMeta = itemStack.getItemMeta();
        lore = itemStack.getItemMeta().getLore();
        //添加简介/介绍
        lore.addAll(description);
        itemMeta.setLore(lore);
        //还给ItemStack
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack refreshOldItem(ItemStack oldItem) {
        ItemStack itemStack = oldItem.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (nbtGetBoolean(itemMeta, "isNew")) {
                return oldItem;
            } else {
                //摇一个Rarity
                Rarity rarity;
                double rank = new Random().nextDouble();
                if (rank <= 0.06) {
                    rarity = Rarity.SUPREME;
                } else if (rank <= 0.16) {
                    rarity = Rarity.MAJESTIC;
                } else {
                    rarity = Rarity.MYTHIC;
                }
                //设置nbt
                //稀有度
                nbtSetString(itemMeta, "Rarity", rarity.name());
                itemMeta.setLocalizedName(rarity.getColor() + itemMeta.getLocalizedName());
                //是新的吗?
                nbtSetBoolean(itemMeta, "isNew", true);
                //设置Lore
                List<String> lore = itemMeta.getLore();
                if (lore == null) {
                    lore = new ArrayList<>();
                }
                lore.add("§f稀有度:" + rarity.getColor() + rarity.getDisplayName());
                String itemName = itemStack.getType().name();
                if (itemName.contains("HELMET") || itemName.contains("CHESTPLATE") || itemName.contains("LEGGINGS") || itemName.contains("BOOTS")) {
                    lore.add("§f装备类型:§b盔甲");
                    //都不是则返回原Item
                } else if (itemName.contains("BOW")) {
                    lore.add("§f装备类型:§b弓");
                } else if (itemName.contains("SHOVEL") || itemName.contains("AXE") || itemName.contains("HOE")) {
                    lore.add("§f装备类型:§b工具");
                } else if (itemName.contains("SWORD")) {
                    lore.add("§f装备类型:§b剑");
                } else {
                    lore.add("§f装备类型:§b其他");
                    return oldItem;
                }
                //将lore还给Item
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                //添加属性(词条)
                itemStack = createItemAttributes(itemStack, false);
                //添加quality
                itemStack = createQuality(itemStack);
            }
        }
        return itemStack;
    }


}
