package me.tuanzi.rpgzero.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;

/**
 * The type Persistent data container utils.
 */
public class PersistentDataContainerUtils {

    /**
     * 设置物品的自定义nbt,类型为String
     *
     * @param itemMeta 修改的物品meta
     * @param Key      key值
     * @param value    变量值
     */
    public static void nbtSetString(ItemMeta itemMeta, String Key, String value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, Key), PersistentDataType.STRING, value);
    }

    /**
     * 同上,类型为Int
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @param value    the value
     */
    public static void nbtSetInteger(ItemMeta itemMeta, String Key, int value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, Key), PersistentDataType.INTEGER, value);
    }

    /**
     * 同上,类型为double
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @param value    the value
     */
    public static void nbtSetDouble(ItemMeta itemMeta, String Key, double value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, Key), PersistentDataType.DOUBLE, value);
    }

    /**
     * 同上,类型为Boolean
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @param value    the value
     */
    public static void nbtSetBoolean(ItemMeta itemMeta, String Key, boolean value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, Key), PersistentDataType.BOOLEAN, value);
    }

    /**
     * 获取物品的自定义nbt,类型为String
     *
     * @param itemMeta 获取物品的ItemMeta
     * @param Key      key值
     * @return 获取的nbt值, 若无返回"Null"
     */
    public static String nbtGetString(ItemMeta itemMeta, String Key) {
        if (itemMeta == null) {
            return "Null";
        }
        String a;
        a = itemMeta.getPersistentDataContainer().get(new NamespacedKey(javaPlugin, Key), PersistentDataType.STRING);
        if (a == null) {
            return "Null";
        }
        return a;
    }

    /**
     * 同上,类型为Int,若无返回0
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @return the integer
     */
    public static Integer nbtGetInteger(ItemMeta itemMeta, String Key) {
        if (itemMeta == null) {
            return 0;
        }
        Integer a;
        a = itemMeta.getPersistentDataContainer().get(new NamespacedKey(javaPlugin, Key), PersistentDataType.INTEGER);
        if (a == null) {
            return 0;
        }
        return a;
    }

    /**
     * 同上,类型为double,若无返回0.0
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @return the double
     */
    public static Double nbtGetDouble(ItemMeta itemMeta, String Key) {
        if (itemMeta == null) {
            return 0.0;
        }
        Double a;
        a = itemMeta.getPersistentDataContainer().get(new NamespacedKey(javaPlugin, Key), PersistentDataType.DOUBLE);
        if (a == null) {
            return 0.0;
        }
        return a;
    }
    /**
     * 同上,类型为boolean,null则返回false
     *
     * @param itemMeta the item meta
     * @param Key      the key
     * @return the double
     */
    public static Boolean nbtGetBoolean(ItemMeta itemMeta, String Key) {
        if (itemMeta == null) {
            return false;
        }
        Boolean a;
        a = itemMeta.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, Key), PersistentDataType.BOOLEAN, false);
        return a;
    }

    //获取物品上的





}
