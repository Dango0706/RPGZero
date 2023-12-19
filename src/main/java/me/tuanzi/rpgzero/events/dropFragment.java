package me.tuanzi.rpgzero.events;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.awt.*;
import java.util.Random;

import static me.tuanzi.rpgzero.items.JavaItems.SPECTRAL_FRAGMENT;


public class dropFragment implements Listener {
    @EventHandler
    public void playerFish(PlayerFishEvent e) {
        //成功钓到鱼 : 6%的概率
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player p = e.getPlayer();
            if (new Random().nextDouble() <= 0.06) {
                p.getWorld().dropItem(e.getPlayer().getLocation(), SPECTRAL_FRAGMENT);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));
            }
        }
    }

    @EventHandler
    public void useItem(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        //使用物品(所有含有耐久度的物品) 0.05%的概率
        if (new Random().nextDouble() <= 0.0005) {
            p.getWorld().dropItem(e.getPlayer().getLocation(), SPECTRAL_FRAGMENT);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

        }
    }

    @EventHandler
    public void levelUp(PlayerLevelChangeEvent e) {
        Player p = e.getPlayer();
        //升级有 0.015% * 新等级 的概率掉落
        if (e.getNewLevel() > e.getOldLevel()) {
            if (new Random().nextDouble() <= 0.00015 * e.getNewLevel()) {
                p.getWorld().dropItem(e.getPlayer().getLocation(), SPECTRAL_FRAGMENT);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

            }
        }
    }

    @EventHandler
    public void useSomething(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        //吃东西/喝奶/使用药水/水桶呈东西 : 1.05%
        if (new Random().nextDouble() <= 0.0105) {
            p.getWorld().dropItem(e.getPlayer().getLocation(), SPECTRAL_FRAGMENT);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

        }
    }


    @EventHandler
    public void hurtOrAttackMob(EntityDamageByEntityEvent e) {
        //攻击生物: 0.005% * 伤害
        if (e.getDamager() instanceof Player player) {
            if (new Random().nextDouble() <= 0.00005 * e.getFinalDamage()) {
                player.getWorld().dropItem(player.getLocation(), SPECTRAL_FRAGMENT);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

            }
            //杀死生物: 0.00025% * 杀死生物的最大血量
            if (e.getEntity() instanceof LivingEntity entity && entity.getHealth() <= 0) {
                if (new Random().nextDouble() <= 0.000025 * entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
                    player.getWorld().dropItem(player.getLocation(), SPECTRAL_FRAGMENT);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
        }
        //受到伤害: 0.003% * 伤害
        if (e.getEntity() instanceof Player player) {
            if (new Random().nextDouble() <= 0.00003 * e.getFinalDamage()) {
                player.getWorld().dropItem(player.getLocation(), SPECTRAL_FRAGMENT);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

            }
        }
    }


    @EventHandler
    public void online(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        //每移动一次,有0.00005%的概率获得
        if (new Random().nextDouble() <= 0.0000005) {
            p.getWorld().dropItem(e.getPlayer().getLocation(), SPECTRAL_FRAGMENT);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

        }
    }


    @EventHandler
    public void mineOre(BlockBreakEvent e) {
        Player p = e.getPlayer();
        String name = e.getBlock().getType().name();
        //挖矿(挖到矿物) : 根据矿物不同概率不同 精准采集无效
        //煤: 0.5% 铜:0.5% 铁: 0.8% 金: 2%  下届金矿石:0.05% 红石:1%
        //钻石:1.35% 远古残骸: 1.5% 青金石:0.8%  绿宝石: 2.4% 石英:0.5%
        //其他均为 0.05%
        if (p.getEquipment().getItemInMainHand().hasItemMeta() && !p.getEquipment().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            if (name.contains("COAL_ORE")) {
                if (new Random().nextDouble() <= 0.005) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("IRON_ORE")) {
                if (new Random().nextDouble() <= 0.008) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("COPPER_ORE")) {
                if (new Random().nextDouble() <= 0.005) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));
                }
            }
            if (name.contains("GOLD_ORE")) {
                if (name.equals("NETHER_GOLD_ORE")) {
                    if (new Random().nextDouble() <= 0.0005) {
                        p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));
                    }
                } else {
                    if (new Random().nextDouble() <= 0.02) {
                        p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));
                    }
                }
            }
            if (name.contains("DIAMOND_ORE")) {
                if (new Random().nextDouble() <= 0.0135) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));
                }
            }
            if (name.contains("ANCIENT_DEBRIS")) {
                if (new Random().nextDouble() <= 0.015) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("LAPIS_ORE")) {
                if (new Random().nextDouble() <= 0.008) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("EMERALD_ORE")) {
                if (new Random().nextDouble() <= 0.024) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("NETHER_QUARTZ_ORE")) {
                if (new Random().nextDouble() <= 0.005) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
            if (name.contains("REDSTONE_ORE")) {
                if (new Random().nextDouble() <= 0.01) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

                }
            }
        }
        //其他方块(除了雪顶)
        if (!name.equals("SNOW")) {
            if (new Random().nextDouble() <= 0.0005) {
                p.getWorld().dropItem(e.getBlock().getLocation(), SPECTRAL_FRAGMENT);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你获得了古老的馈赠!"));

            }
        }
    }


}
