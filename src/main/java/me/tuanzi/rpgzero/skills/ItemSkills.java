package me.tuanzi.rpgzero.skills;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static me.tuanzi.rpgzero.utils.GeyserUtils.isBedrockPlayer;
import static me.tuanzi.rpgzero.utils.ItemStackUtils.useItems;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetBoolean;

public class ItemSkills implements Listener {

    //雷霆之怒的技能
    private static void thunderWrathSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Entity entity = getTargetEntity(player, 10);
        Location target;
        if (entity == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x1FD2FF)) + "你好像没有指向某个实体呢~"));
            return;
        } else {
            target = entity.getLocation();
        }
        for (int i = 0; i < 3; i++) {
            player.getWorld().spawnEntity(target, EntityType.LIGHTNING);
        }
        //可以攻击到,做一个attackRange?
//        player.attack(entity);
        player.setCooldown(Material.NETHERITE_SWORD, 20 * 10);

    }

    //获取玩家所指的实体
    public static Entity getTargetEntity(Player player, double maxDistance) {
        // 获取玩家的位置和方向
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        // 创建一个射线追踪结果对象，用于存储玩家所指的实体
        RayTraceResult result = null;
        // 循环检测玩家所指的方向上的每个方块，直到找到一个实体或者超过最大距离
        for (int i = 0; i < maxDistance; i++) {
            // 计算当前方块的位置
            location = location.add(direction);
            // 获取当前方块上的所有实体
            Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5);
            // 如果有实体存在，就尝试获取玩家所指的实体
            if (!entities.isEmpty()) {
                // 调用BukkitAPI中的rayTraceEntities方法，传入玩家的位置和方向，以及最大距离和实体过滤器
                result = location.getWorld().rayTraceEntities(location, direction, 0.5/*, entity -> entity != player*/);
                //所指实体不能是自己.
                if (result != null) {
                    if (result.getHitEntity() == player) {
                        result = null;
                    }
                }
                // 如果找到了玩家所指的实体，就跳出循环
                if (result != null) {
                    break;
                }
            }
        }
        // 如果找到了玩家所指的实体，就返回它，否则返回null
        if (result != null) {
            return result.getHitEntity();
        } else {
            return null;
        }
    }

    // 获取指定方块周围的相同材料的方块的数量的方法，使用递归算法
    private void findSameBlocks(Block block, java.util.List<Block> list, Material material, int maxCount) {
        // 判断列表是否已经填满
        if (list.size() >= maxCount) {
            // 如果填满了，就停止递归，直接返回
            return;
        }
        // 遍历指定方块周围的6个方向的方块
        for (BlockFace face : new BlockFace[]{BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST}) {
            // 获取相邻的方块
            Block adjacent = block.getRelative(face);
            // 判断相邻的方块的材料是否和指定的材料相同
            if (adjacent.getType() == material) {
                // 判断列表是否已经包含了相邻的方块
                if (!list.contains(adjacent)) {
                    // 如果没有包含，就将相邻的方块添加到列表中
                    list.add(adjacent);
                    // 递归地调用本方法，将相邻的方块和列表作为参数传递
                    findSameBlocks(adjacent, list, material, maxCount);
                }
            }
        }
    }

    //连锁挖矿
    private void veinMine(BlockBreakEvent event, int maxCount, Material material) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        // 创建一个固定大小的列表，用来存储相同材料的方块
        List<Block> list = new ArrayList<>(maxCount);
        // 调用递归方法，将被破坏的方块和列表作为参数传递
        findSameBlocks(event.getBlock(), list, material, maxCount);
        //设置最大挖掘数量
        int count = Math.min(maxCount, list.size());
        //检测方块掉落的经验值
        int dropXp = 0;
        if (material == Material.COAL_ORE || material == Material.DEEPSLATE_COAL_ORE) {
            for (int i = 0; i < count; i++) {
                dropXp += new Random().nextInt(0, 2);
            }
        } else if (material == Material.DIAMOND_ORE || material == Material.DEEPSLATE_DIAMOND_ORE || material == Material.EMERALD_ORE || material == Material.DEEPSLATE_EMERALD_ORE) {
            for (int i = 0; i < count; i++) {
                dropXp += new Random().nextInt(3, 7);
            }
        } else if (material == Material.REDSTONE_ORE || material == Material.DEEPSLATE_REDSTONE_ORE) {
            for (int i = 0; i < count; i++) {
                dropXp += new Random().nextInt(1, 5);
            }
        } else if (material == Material.NETHER_QUARTZ_ORE || material == Material.LAPIS_ORE || material == Material.DEEPSLATE_LAPIS_ORE) {
            for (int i = 0; i < count; i++) {
                dropXp += new Random().nextInt(2, 5);
            }
        }
        //生成一个对应的经验球
        if (dropXp > 0 && player.getGameMode() != GameMode.CREATIVE) {
            ExperienceOrb orb = (ExperienceOrb) list.get(0).getWorld().spawnEntity(list.get(0).getLocation(), EntityType.EXPERIENCE_ORB);
            orb.setExperience(dropXp);
        }
        // 遍历列表中的方块，将它们破坏
        for (int i = 0; i < count; i++) {
            Block block = list.get(i);
            block.breakNaturally(item);
            //是创造则不消耗耐久
            if (player.getGameMode() != GameMode.CREATIVE) {
                useItems(item, 1);
            }
        }
        // 给玩家发送一条消息，告诉他连锁挖矿的效果触发了
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x1FD2FF)) + "你触发了连锁挖矿的效果，一共挖掘了" + (count + 1) + "个" + material));
    }

    @EventHandler
    public void rightClickSkills(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta;
        if (itemStack != null && itemStack.hasItemMeta()) {
            itemMeta = itemStack.getItemMeta();
        } else {
            return;
        }
        //先看是不是插件内的物品
        if (itemMeta.hasCustomModelData() && nbtGetBoolean(itemMeta, "isNew")) {
            //再看是不是基岩版玩家
            if (isBedrockPlayer(player)) {

            } else {
                //再看触发方式
                //Java版为右键.
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    int cooldown = player.getCooldown(Material.NETHERITE_SWORD);
                    //再看冷却到不到
                    if (cooldown <= 0) {
                        //对应武器的技能
                        if (itemMeta.getCustomModelData() == 15213000) {
                            thunderWrathSkill(event);
                        }
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x1FD2FF)) + "你的武器冷却中,冷却还有:" + (cooldown / 20) + "秒"));
                    }
                }

            }
        }
    }

    // 处理方块破坏事件的方法，实现连锁挖矿的效果
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        // 获取玩家手持的物品
        ItemStack itemStack = player.getEquipment().getItemInMainHand();
        ItemMeta itemMeta;
        if (itemStack.hasItemMeta()) {
            itemMeta = itemStack.getItemMeta();
        } else {
            return;
        }
        //先看是不是插件内的物品
        if (itemMeta.hasCustomModelData() && nbtGetBoolean(itemMeta, "isNew")) {
            if (itemMeta.getCustomModelData() == 15212006 && player.isSneaking()) {
                // 获取被破坏的方块的材料
                Material material = event.getBlock().getType();
                // 判断被破坏的方块的材料是否是矿石
                if (material.name().contains("_ORE")) {
                    veinMine(event, 63, material);
                } else {
                    veinMine(event, 9, material);
                }
            }
        }
    }


}
