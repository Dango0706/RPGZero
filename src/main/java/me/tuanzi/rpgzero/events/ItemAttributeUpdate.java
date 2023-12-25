package me.tuanzi.rpgzero.events;

import me.tuanzi.rpgzero.draw.Rarity;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.List;

import static me.tuanzi.rpgzero.attributes.CreateItemAttributes.updateAttributes;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;

public class ItemAttributeUpdate implements Listener {
    //增加经验值
    static void addExp(ItemStack itemStack, LivingEntity livingEntity, EquipmentSlot slot) {
        if (itemStack != null && itemStack.getItemMeta() != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            int level = nbtGetInteger(itemMeta, "AttributeLevel");
            int exp = nbtGetInteger(itemMeta, "AttributeExp") + 1;
            int maxLevel;
            //lore
            List<String> lore = itemMeta.getLore();
            //有等级
            if (level != 0) {
                //exp+1
                nbtSetInteger(itemMeta, "AttributeExp", exp);
                //设置lore
                for (int i = 0; i < lore.size(); i++) {
                    String a = lore.get(i);
                    if (a.contains("Exp:")) {
                        lore.set(i, "§f=====Exp:" + (exp) + "=====");
                    }
                }
                //设置回去
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
            }
            //设置最大等级
            if (nbtGetString(itemMeta, "Rarity").equals(Rarity.SINGULAR.name())) {
                maxLevel = 20;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.EXQUISITE.name())) {
                maxLevel = 20;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MYTHIC.name())) {
                maxLevel = 25;
            } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MAJESTIC.name())) {
                maxLevel = 30;
            } else {
                maxLevel = 40;
            }
            //是否到达下一级
            if (getNextLevelExp(level + 1) <= exp && level + 1 <= maxLevel) {
                //之前设置回去,重新获取.
                itemMeta = itemStack.getItemMeta();
                lore = itemMeta.getLore();
                //到达则增加
                level = level + 1;
                exp = 0;
                //设置nbt
                nbtSetInteger(itemMeta, "AttributeLevel", level);
                nbtSetInteger(itemMeta, "AttributeExp", exp);
                //设置lore
                for (int i = 0; i < lore.size(); i++) {
                    String a = lore.get(i);
                    if (a.contains("Lv:")) {
                        lore.set(i, "§f=====Lv:" + level + "=====");
                    }
                    if (a.contains("Exp:")) {
                        lore.set(i, "§f=====Exp:0=====");
                    }
                }
                //设置回去
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                //为actionbar服务
                ItemMeta actionMeta = livingEntity.getEquipment().getItem(slot).getItemMeta();
                //发送一个actionBar
                if (livingEntity instanceof Player player) {
                    String name = actionMeta.getDisplayName();
                    if (name.equals("")) {
                        name = actionMeta.getLocalizedName();
                    }
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你的" + name + ChatColor.of(new Color(0x70CFEA)) + "升级了!"));
                }
                //如果等级能除开5,则升级属性
                if (level % 5 == 0) {
                    livingEntity.getEquipment().setItem(slot,updateAttributes(itemStack));
                    if (livingEntity instanceof Player player) {
                        String name = actionMeta.getDisplayName();
                        if (name.equals("")) {
                            name = actionMeta.getLocalizedName();
                        }
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x70CFEA)) + "你的" + name + ChatColor.of(new Color(0x70CFEA)) + "升级了,并获得了一个属性强化!"));
                    }
                }
            }

        }
    }

    //获取下个等级所需经验
    static int getNextLevelExp(int nextLevel) {
        int l;
        if (nextLevel < 20) {
            l = nextLevel * 15;
        } else if (nextLevel < 40) {
            l = nextLevel * 25;
        } else {
            l = nextLevel * (nextLevel - 10);
        }
        return l;
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        //被害者需要为实体
        if (event.getEntity() instanceof LivingEntity victim) {
            //是生物近战攻击
            if (event.getDamager() instanceof LivingEntity attacker) {
                //攻击者主手
                addExp(attacker.getEquipment().getItemInMainHand(), attacker, EquipmentSlot.HAND);
            }
            //是生物远程攻击
            if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof LivingEntity attacker) {
                addExp(attacker.getEquipment().getItemInMainHand(), attacker, EquipmentSlot.HAND);
            }
            //防御者全身
            addExp(victim.getEquipment().getHelmet(), victim, EquipmentSlot.HEAD);
            addExp(victim.getEquipment().getChestplate(), victim, EquipmentSlot.CHEST);
            addExp(victim.getEquipment().getLeggings(), victim, EquipmentSlot.LEGS);
            addExp(victim.getEquipment().getBoots(), victim, EquipmentSlot.FEET);
        }
    }


}
