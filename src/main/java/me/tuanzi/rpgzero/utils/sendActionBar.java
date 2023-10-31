package me.tuanzi.rpgzero.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class sendActionBar {

    static String version;

    Class craftPlayer;
    static Class iChatBaseComponent;
    static Class chatComponentText;
    static Class packet;
    static Class packetPlayOutTitle;
    static Class enumTitleAction;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
        try {
            iChatBaseComponent = getNmsClass("IChatBaseComponent");
            chatComponentText = getNmsClass("ChatComponentText");
            packet = getNmsClass("Packet");
            packetPlayOutTitle = getNmsClass("PacketPlayOutTitle");
            // 因为EnumTitleAction这个枚举是PacketPlayOutTitle中的内部枚举，如果你看过内部类编译出来的class文件的名字的话，应该会知道
            enumTitleAction = getNmsClass("PacketPlayOutTitle$EnumTitleAction");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void sendActionBar(Player player, String s){
//        player.sendTitle("2333","",0,20,0);

        try {
            // 1. 获取ChatComponentText对象
            Object chatComponentTextInstance = chatComponentText.getConstructor(String.class).newInstance(s);
            // 2. 获取EnumTitleAction枚举TITLE
            Object enumTITLE = enumTitleAction.getMethod("a", String.class).invoke(enumTitleAction.getConstructor().newInstance(), "TITLE");
            // 3. 创建PacketPlayOutTitle对象
            Object packetPlayOutTitleInstance = packetPlayOutTitle.getConstructor(enumTitleAction, iChatBaseComponent, int.class, int.class, int.class).newInstance(enumTITLE, chatComponentTextInstance, 0, 40, 0);
            // 4. 使用CraftPlayer.class.cast(p) 把p对象强转一下
            Object craftPlayer = getCbClass("CraftPlayer").cast(player);
            // 5. 调用CraftPlayer中的getHandle方法获取EntityPlayer
            Object entityPlayer = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
            // 6. 获取EntityPlayer中的成员变量PlayerConnection对象
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            // 7. 调用sendPacket方法，传进去PacketPlayOutTitle对象
            playerConnection.getClass().getMethod("sendPacket",packet).invoke(playerConnection,packetPlayOutTitleInstance);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }


    }


    public static void setReach(Player player ,float reach){

    }


    public static Class getNmsClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + version + "." + name);
    }

    public static Class getCbClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
    }


}
