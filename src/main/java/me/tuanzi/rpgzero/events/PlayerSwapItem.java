package me.tuanzi.rpgzero.events;

import me.tuanzi.rpgzero.attributes.ItemAttributes;
import me.tuanzi.rpgzero.quality.Quality;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.UUID;

import static me.tuanzi.rpgzero.utils.Config.getPlayerConfig;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetDouble;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetString;

public class PlayerSwapItem implements Listener {
    //PlayerSwapHandItemsEvent 按f触发
    @EventHandler
    public void aVoid(PlayerItemHeldEvent event) {
        //Quality加减攻速
        Player player = event.getPlayer();
        if (!(Boolean) getPlayerConfig(player, "isNewDamageCalculate"))
            return;
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
        double attackSpeed = 0.0;
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        try {
            //清除加减的攻速
            Collection<AttributeModifier> remove = attribute.getModifiers();
            for (AttributeModifier attributeModifier1 : remove) {
                if (attributeModifier1.getUniqueId().equals(UUID.fromString("f2eae622-eb37-4dd5-b4d7-5a07aa0a9c3f"))) {
                    attribute.removeModifier(attributeModifier1);
                }
            }
        } catch (NullPointerException ignored) {

        }
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            //加品质中的攻速
            for (Quality quality : Quality.values()) {
                if (nbtGetString(itemMeta, "Quality").equals(quality.name())) {
                    attackSpeed += quality.getAttackSpeed();
                }
            }//加属性中的攻速
            if (nbtGetDouble(itemMeta, ItemAttributes.ATTACK_SPEED.name()) > 0) {
                attackSpeed += (nbtGetDouble(itemMeta, ItemAttributes.ATTACK_SPEED.name())/100);
            }
        }
        attribute.addModifier(new AttributeModifier(UUID.fromString("f2eae622-eb37-4dd5-b4d7-5a07aa0a9c3f"), "QualityAddAttackSpeed", attackSpeed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
    }
}
