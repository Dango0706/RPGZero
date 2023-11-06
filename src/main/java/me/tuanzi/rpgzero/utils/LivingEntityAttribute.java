package me.tuanzi.rpgzero.utils;

import me.tuanzi.rpgzero.attributes.ItemAttributes;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Objects;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.utils.ItemStackUtils.getEquipments;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetDouble;

/**
 * The type Living entity attribute.
 */
public class LivingEntityAttribute {


    /**
     * 获取装备对生物加成的防御力
     *
     * @param entity 获取的生物
     * @return 装备加成部分的防御力
     */

    public static double getCalculationLivingEntityDefense(LivingEntity entity) {
        double amount = 0.0;
        //添加
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.DEFENSE.name());
            }
        }
        //增加盔甲和韧性
        amount += Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_ARMOR)).getValue() * 2;
        amount += Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS)).getValue() * 4;

        return amount;
    }

    /**
     * 同上,获取的为物理抗性
     *
     * @param entity the entity
     * @return the calculation living entity physical resistance
     */
    public static double getCalculationLivingEntityPhysicalResistance(LivingEntity entity) {
        double amount = 0.0;
        //增加装备
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.PHYSICAL_RESISTANCE.name());
            }
        }
        return amount;
    }

    /**
     * 同上,获取的为法术防御
     *
     * @param entity the entity
     * @return the calculation living entity magic resistance
     */
//计算装备提供的魔法抗性
    public static double getCalculationLivingEntityMagicResistance(LivingEntity entity) {
        double amount = 0.0;
        //增加装备
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.MAGIC_RESISTANCE.name());
            }
        }
        return amount;
    }

    /**
     * 同上,获取的是暴击率(包含手持物品)
     *
     * @param entity the entity
     * @return the calculation living entity crit rate
     */
//计算装备提供的暴击率
    public static double getCalculationLivingEntityCritRate(LivingEntity entity) {
        double amount = 0.0;
        //增加装备
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        itemStacks.add(entity.getEquipment().getItemInMainHand());
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.CRIT_RATE.name());
            }
        }
        return amount;
    }

    public static double getCalculationLivingEntityAttackDamage(LivingEntity entity) {
        double amount = 0.0;
        //增加装备
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        itemStacks.add(entity.getEquipment().getItemInMainHand());
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.ATTACK_DAMAGE.name());
            }
        }
        return amount;
    }

    /**
     * 同上,获取的是暴击伤害(包含手持物品)
     *
     * @param entity the entity
     * @return the calculation living entity crit damage
     */

    public static double getCalculationLivingEntityCritDamage(LivingEntity entity) {
        double amount = 0.0;
        //增加装备
        ArrayList<ItemStack> itemStacks = getEquipments(entity);
        itemStacks.add(entity.getEquipment().getItemInMainHand());
        for (ItemStack itemStack : itemStacks) {
            if (itemStack != null && itemStack.hasItemMeta()) {
                amount += nbtGetDouble(itemStack.getItemMeta(), ItemAttributes.CRIT_DAMAGE.name());
            }
        }
        return amount;
    }

    /**
     * 获取生物的基础防御力
     *
     * @param entity 需要获取的生物
     * @return 此生物的基础防御力
     */

    public static double getLivingEntityBaseDefense(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "Defense"), PersistentDataType.DOUBLE, 0.0);
    }

    public static double getLivingEntityBaseAttackDamage(LivingEntity entity) {
        return entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() - 1.0;
    }

    public static double getLivingEntityBaseAttackSpeed(LivingEntity entity) {
        return entity.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue() - 1.0;
    }

    /**
     * 同上,获取的是物理抗性
     *
     * @param entity the entity
     * @return the living entity base physical resistance
     */
    public static double getLivingEntityBasePhysicalResistance(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PhysicalResistance"), PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * 同上,获取的是魔法抗性
     *
     * @param entity the entity
     * @return the living entity base magic resistance
     */
    public static double getLivingEntityBaseMagicResistance(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "MagicResistance"), PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * 同上,获取的是暴击率
     *
     * @param entity the entity
     * @return the living entity base crit rate
     */
    public static double getLivingEntityBaseCritRate(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "CritRate"), PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * 同上,获取的是暴击伤害
     *
     * @param entity the entity
     * @return the living entity base crit damage
     */
    public static double getLivingEntityBaseCritDamage(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "CritDamage"), PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * 设置生物的基础防御力
     *
     * @param entity 需要设置的生物
     * @param amount 设置的值
     */

    public static void setLivingEntityBaseDefense(LivingEntity entity, double amount) {
        entity.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "Defense"), PersistentDataType.DOUBLE, amount);
    }

    /**
     * 同上,设置的是物理抗性
     *
     * @param entity the entity
     * @param amount the amount
     */
    public static void setLivingEntityBasePhysicalResistance(LivingEntity entity, double amount) {
        entity.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PhysicalResistance"), PersistentDataType.DOUBLE, amount);
    }

    /**
     * 同上,设置的是魔法抗性
     *
     * @param entity the entity
     * @param amount the amount
     */
    public static void setLivingEntityBaseMagicResistance(LivingEntity entity, double amount) {
        entity.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "MagicResistance"), PersistentDataType.DOUBLE, amount);
    }

    /**
     * 同上,设置的是暴击率
     *
     * @param entity the entity
     * @param amount the amount
     */
    public static void setLivingEntityBaseCritRate(LivingEntity entity, double amount) {
        entity.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "CritRate"), PersistentDataType.DOUBLE, amount);
    }

    /**
     * 同上,设置的是物理抗性暴击伤害
     *
     * @param entity the entity
     * @param amount the amount
     */
    public static void setLivingEntityBaseCritDamage(LivingEntity entity, double amount) {
        entity.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "CritDamage"), PersistentDataType.DOUBLE, amount);
    }

    /**
     * 获取生物除buff外防御力总和
     *
     * @param entity 需要获取的生物
     * @return 此生物的防御力总和
     */

    //防御力
    public static double getLivingEntityTotalDefense(LivingEntity entity) {
        return getLivingEntityBaseDefense(entity) + getCalculationLivingEntityDefense(entity);
    }

    /**
     * 同上,获取的是物理抗性
     *
     * @param entity the entity
     * @return the living entity total physical resistance
     */
    public static double getLivingEntityTotalPhysicalResistance(LivingEntity entity) {
        return getLivingEntityBasePhysicalResistance(entity) + getCalculationLivingEntityPhysicalResistance(entity);
    }

    /**
     * 同上,获取的是魔法抗性
     *
     * @param entity the entity
     * @return the living entity total magic resistance
     */
    public static double getLivingEntityTotalMagicResistance(LivingEntity entity) {
        return getLivingEntityBaseMagicResistance(entity) + getCalculationLivingEntityMagicResistance(entity);
    }

    /**
     * 同上,获取的是暴击率
     *
     * @param entity the entity
     * @return the living entity total crit rate
     */
    public static double getLivingEntityTotalCritRate(LivingEntity entity) {
        return getLivingEntityBaseCritRate(entity) + getCalculationLivingEntityCritRate(entity);
    }

    /**
     * 同上,获取的是暴击伤害
     *
     * @param entity the entity
     * @return the living entity total crit damage
     */
    public static double getLivingEntityTotalCritDamage(LivingEntity entity) {
        return getLivingEntityBaseCritDamage(entity) + getCalculationLivingEntityCritDamage(entity);
    }

    public static double getLivingEntityTotalAttackDamage(LivingEntity entity) {
        return getLivingEntityBaseAttackDamage(entity) + getCalculationLivingEntityAttackDamage(entity);
    }

    /**
     * 获取生物的护盾值
     *
     * @param entity 需要获取的生物
     * @return 此生物的护盾值
     */
    public static double getLivingEntityTotalShieldAmount(LivingEntity entity) {
        return entity.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "Shields"), PersistentDataType.DOUBLE, 0.0);
    }

}
