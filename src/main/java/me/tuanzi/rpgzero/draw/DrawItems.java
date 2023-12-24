package me.tuanzi.rpgzero.draw;

import org.bukkit.*;
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
import static me.tuanzi.rpgzero.command.mainCommander.sendPlayerDrawCount;
import static me.tuanzi.rpgzero.quality.CreateQuality.refreshQuality;
import static me.tuanzi.rpgzero.utils.Config.playerConfig;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetBoolean;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetString;

public class DrawItems {

    //所有常驻三星四星五星
    public static List<ItemStack> allBlue = new ArrayList<>();
    public static List<ItemStack> allPurple = new ArrayList<>();
    public static List<ItemStack> allGolden = new ArrayList<>();
    //up
    public static List<ItemStack> upPurple = new ArrayList<>();
    public static List<ItemStack> upGolden = new ArrayList<>();


    public DrawItems() {
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
    }

    //抽卡
    public static ItemStack drawItem(Player player, Location location) {
        ItemStack result = new ItemStack(Material.AIR);
        //总抽数
        int totalCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "TotalDrawCount"), PersistentDataType.INTEGER, 0) + 1;
        //紫色抽数
        int purpleCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 0) + 1;
        //金色抽数
        int goldenCount = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 0) + 1;
        //是紫保底吗
        boolean isListPurple = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, false);
        //是金保底吗
        boolean isListGolden = player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, false);
        //抽卡
        Random random = new Random();
        double rank = random.nextDouble();
        rank *= 100;
        //设置
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, goldenCount);
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, purpleCount);
        player.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "TotalDrawCount"), PersistentDataType.INTEGER, totalCount);
        if (playerConfig.getBoolean(player.getName().toLowerCase() + ".isDrawCountToast", true))
            sendPlayerDrawCount(player);
        //保底计算
        if (goldenCount >= 90) {
            spawnO(location, 3);
            return getGoldenItemStack(player, isListGolden, allGolden, upGolden, random);
        } else {
            //不是金保底 计算紫保底
            if (purpleCount >= 10) {
                spawnO(location, 2);
                return getPurpleItemStack(player, isListPurple, allPurple, upPurple, upGolden, random);
            } else {
                double goldenRank = 0.6;
                if (goldenCount >= 60) {
                    goldenRank += (goldenCount - 60) * 0.5;
                    if (goldenCount >= 70) {
                        goldenRank += (goldenCount - 70) * 4;
                        if (goldenCount >= 77 && goldenCount <= 82) {
                            goldenRank += (goldenCount - 77) * 7;
                        }
                    }
                }
                //不是金也不是紫保底
                if (rank <= goldenRank) {
                    spawnO(location, 3);
                    return getGoldenItemStack(player, isListGolden, allGolden, upGolden, random);
                } else if (rank <= 10 + goldenRank) {
                    spawnO(location, 2);
                    return getPurpleItemStack(player, isListPurple, allPurple, upPurple, upGolden, random);
                } else {
//                    spawnO(location, 1);
                    drawShape(location);

                    ItemStack itemStack;
                    itemStack = allBlue.get(random.nextInt(allBlue.size()));
                    itemStack = refreshQuality(itemStack);
                    itemStack = refreshAttributes(itemStack);
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
        itemStack = refreshAttributes(itemStack);

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
        itemStack = refreshAttributes(itemStack);

        return itemStack;
    }

    //画圆
    static void spawnO(Location location, int result) {
        final Location location1 = location.add(0.5, -0.25, 0.5);
        Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
            for (double angle = 0; angle < 360; angle += 0.5) {
                double x = location1.getX() + 2 * Math.cos(angle);
                double y = location1.getY();
                double z = location1.getZ() + 2 * Math.sin(angle);

                Location newLocation = new Location(location1.getWorld(), x, y, z);
                if (result == 1) {
                    location1.getWorld().spawnParticle(Particle.REDSTONE, newLocation, 1, 0, 0, 0, new Particle.DustOptions(Color.BLUE, 1.0f));
                } else if (result == 2) {
                    location1.getWorld().spawnParticle(Particle.REDSTONE, newLocation, 1, 0, 0, 0, new Particle.DustOptions(Color.PURPLE, 1.0f));
                } else {
                    location1.getWorld().spawnParticle(Particle.REDSTONE, newLocation, 1, 0, 0, 0, new Particle.DustOptions(Color.YELLOW, 1.0f));
                }
            }
            if (result == 1) {
                location.getWorld().playSound(location1, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
            } else if (result == 2) {
                location.getWorld().playSound(location1, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            } else {
                location.getWorld().playSound(location1, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
            }
        }, 1L);
    }

    //画圆+内部五角星
    public static void drawShape(Location location) {
        final Location loc = location.add(0.5, -0.25, 0.5);
        // 获取世界对象
        World world = loc.getWorld();
        // 定义粒子的颜色和大小
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.BLUE, 1.0F);
        // 定义圆圈的半径和角度间隔
        double circleRadius = 2.0;
        double circleInterval = Math.PI / 180.0;
        // 定义五角星的半径和圆心角
        double starRadius = 1.5;
        double starAngle = Math.PI / 5.0;
        // 定义粒子的数量和偏移量
        int count = 1;
        double offsetX = 0.0;
        double offsetY = 0.0;
        double offsetZ = 0.0;
        // 定义五角星的偏移量
        double starOffset = 0.0;
        // 定义线条的点数
        int linePoints = 10;
        // 计算并绘制五角星的线条
        for (int i = 0; i < 5; i++) {
            // 计算线条的起点和终点的坐标
            double x1 = loc.getX() + starOffset + starRadius * Math.sin(i * 2 * starAngle);
            double y1 = loc.getY();
            double z1 = loc.getZ() + starOffset + starRadius * Math.cos(i * 2 * starAngle);
            double x2 = loc.getX() + starOffset + starRadius * Math.sin((i + 2) % 5 * 2 * starAngle);
            double y2 = loc.getY();
            double z2 = loc.getZ() + starOffset + starRadius * Math.cos((i + 2) % 5 * 2 * starAngle);
            // 计算线条上每个点的坐标增量
            double dx = (x2 - x1) / linePoints;
            double dy = (y2 - y1) / linePoints;
            double dz = (z2 - z1) / linePoints;
            // 在线条上的每个点上生成粒子
            for (int j = 0; j <= linePoints; j++) {
                double x = x1 + j * dx;
                double y = y1 + j * dy;
                double z = z1 + j * dz;
                world.spawnParticle(Particle.REDSTONE, x, y, z, count, offsetX, offsetY, offsetZ, dustOptions);
            }
        }
        // 计算并绘制圆圈的点
        for (double angle = 0.0; angle < 2 * Math.PI; angle += circleInterval) {
            // 计算点的坐标
            double x = loc.getX() + circleRadius * Math.sin(angle);
            double y = loc.getY();
            double z = loc.getZ() + circleRadius * Math.cos(angle);
            // 在点上生成粒子
            world.spawnParticle(Particle.REDSTONE, x, y, z, count, offsetX, offsetY, offsetZ, dustOptions);
        }
    }


}
