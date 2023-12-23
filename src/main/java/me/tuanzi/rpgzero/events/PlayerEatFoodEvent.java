package me.tuanzi.rpgzero.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.tuanzi.rpgzero.utils.ItemStackUtils.setItemStackCount;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;

public class PlayerEatFoodEvent implements Listener {

    @EventHandler
    public void playerEatFood(PlayerItemConsumeEvent event) {
        //可以在不饿的时候吃
        if (event.getItem().getType() == Material.GOLDEN_APPLE) {
            //获取item
            ItemStack itemStack = event.getItem();
            ItemMeta itemMeta;
            if (itemStack.hasItemMeta()) {
                itemMeta = itemStack.getItemMeta();
            } else {
                return;
            }
            //如果吃的是一个有自定义模型的金苹果,且是新物品.
            if (itemMeta.hasCustomModelData() && nbtGetBoolean(itemMeta, "isNew")) {
                //取消
                event.setCancelled(true);
                // 获取玩家
                Player player = event.getPlayer();
                //增加饱食度饱和度 最大不超过20
                int foodLevel = nbtGetInteger(itemMeta, "FoodLevel") + player.getFoodLevel();
                float saturation = nbtGetFloat(itemMeta, "Saturation") + player.getSaturation();
                player.setFoodLevel(Math.min(foodLevel, 20));
                //如果饱和度超过饱食度,则设置为一致.
                player.setSaturation(Math.min(saturation,player.getFoodLevel()));
                //减少一个物品
                player.getEquipment().setItemInMainHand(setItemStackCount(itemStack, itemStack.getAmount() - 1));
                //播放打嗝声
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
                //效果...
                //绿宝石苹果
                if (itemMeta.getCustomModelData() == 15210008) {
                    //3m夜视 + 3m急迫II
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60 * 5, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 5, 1));
                }
            }
        }
        //不可以在不饿的时候吃
        if (event.getItem().getType() == Material.GOLDEN_CARROT) {
            //获取item
            ItemStack itemStack = event.getItem();
            ItemMeta itemMeta;
            if (itemStack.hasItemMeta()) {
                itemMeta = itemStack.getItemMeta();
            } else {
                return;
            }
            //如果吃的是一个有自定义模型的金苹果,且是新物品.
            if (itemMeta.hasCustomModelData() && nbtGetBoolean(itemMeta, "isNew")) {
                event.setCancelled(true);
            }
        }
    }

}
