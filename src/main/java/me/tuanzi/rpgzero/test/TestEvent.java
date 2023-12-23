package me.tuanzi.rpgzero.test;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;

import static me.tuanzi.rpgzero.utils.GeyserUtils.isBedrockPlayer;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetBoolean;

public class TestEvent implements Listener {

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        System.out.println(event.getCause());
        if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            System.out.println(event.getFinalDamage());
        }
    }

    @EventHandler
    public void rightClickSkills(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta;
        if (itemStack.hasItemMeta()) {
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
                        if (itemMeta.getCustomModelData() == 15213000) {
                            thunderWrathSkill(event);
                        }
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.of(new Color(0x6FE250)) + "你的武器冷却中,冷却还有:" + (cooldown / 20) + "秒"));
                    }
                }

            }
        }

    }

    private static void thunderWrathSkill(PlayerInteractEvent event){
        Player player = event.getPlayer();

    }


}
