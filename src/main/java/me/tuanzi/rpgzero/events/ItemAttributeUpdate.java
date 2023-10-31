package me.tuanzi.rpgzero.events;

import me.tuanzi.rpgzero.draw.Rarity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static me.tuanzi.rpgzero.attributes.CreateItemAttributes.updateAttributes;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;

public class ItemAttributeUpdate implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity victim) {
            //是玩家
            if (attacker instanceof Player player) {

            }
            ItemStack itemStack = attacker.getEquipment().getItemInMainHand();
            //有ItemMeta
            if (itemStack.getItemMeta() != null) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                int level = nbtGetInteger(itemMeta, "AttributeLevel");
                int exp = nbtGetInteger(itemMeta, "AttributeExp") + 1;
                int maxLevel;
                //lore
                List<String> lore = itemMeta.getLore();
                //有等级
                if (level != 0) {
                    //exp+1
                    nbtSetInteger(itemMeta, "AttributeExp", exp );
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
                    maxLevel = 25;
                } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MYTHIC.name())) {
                    maxLevel = 35;
                } else if (nbtGetString(itemMeta, "Rarity").equals(Rarity.MAJESTIC.name())) {
                    maxLevel = 55;
                } else {
                    maxLevel = 100;
                }
                //是否到达下一级
                if (getNextLevelExp(level + 1) <= exp && level + 1 <= maxLevel) {
                    //之前设置回去,重新获取.
                    itemMeta = itemStack.getItemMeta();
                    lore = itemMeta.getLore();
                    //到达则增加
                    level = level +1;
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
                    //发送一个actionBar
                    if (attacker instanceof Player player) {
                        player.sendTitle("你的" + player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName() + "升级了!", "", 0, 30, 0);
                    }
                    //如果等级能除开5,则升级属性
                    if (level % 5 == 0) {
                        attacker.getEquipment().setItemInMainHand(updateAttributes(itemStack));
                    }
                }

            }


        }
    }

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

}
