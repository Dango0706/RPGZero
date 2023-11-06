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

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static me.tuanzi.rpgzero.utils.Config.getPlayerConfig;
import static me.tuanzi.rpgzero.utils.ItemStackUtils.getEquipments;
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
        //手持
        ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
        //移动速度
        ArrayList<ItemStack> itemStacks = getEquipments(player);
        itemStacks.add(itemStack);
        double attackSpeed = 0.0;
        double speed = 0.0;
        AttributeInstance attackSpeedAttribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        AttributeInstance moveSpeed = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        try {
            //清除加减的攻速
            Collection<AttributeModifier> remove = attackSpeedAttribute.getModifiers();
            remove.addAll(moveSpeed.getModifiers());
            for (AttributeModifier attributeModifier1 : remove) {
                if (attributeModifier1.getUniqueId().equals(UUID.fromString("f2eae622-eb37-4dd5-b4d7-5a07aa0a9c3f"))) {
                    attackSpeedAttribute.removeModifier(attributeModifier1);
                }
                if (attributeModifier1.getUniqueId().equals(UUID.fromString("c6b7f1ce-90a9-4d47-97bc-2e4778dc6c56"))) {
                    moveSpeed.removeModifier(attributeModifier1);
                }
            }
        } catch (NullPointerException ignored) {

        }
        for (ItemStack itemStack1 : itemStacks) {
            if (itemStack1 != null && itemStack1.hasItemMeta()) {
                ItemMeta itemMeta = itemStack1.getItemMeta();
                for (Quality quality : Quality.values()) {
                    if (nbtGetString(itemMeta, "Quality").equals(quality.name())) {
                        speed += quality.getSpeed();
                        attackSpeed += quality.getAttackSpeed();
                    }
                }
                if (nbtGetDouble(itemMeta, ItemAttributes.ATTACK_SPEED.name()) > 0) {
                    attackSpeed += (nbtGetDouble(itemMeta, ItemAttributes.ATTACK_SPEED.name()) / 100);
                }
            }
        }
        attackSpeedAttribute.addModifier(new AttributeModifier(UUID.fromString("f2eae622-eb37-4dd5-b4d7-5a07aa0a9c3f"), "QualityAddAttackSpeed", attackSpeed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
        moveSpeed.addModifier(new AttributeModifier(UUID.fromString("c6b7f1ce-90a9-4d47-97bc-2e4778dc6c56"), "QualityAddMovementSpeed", speed, AttributeModifier.Operation.MULTIPLY_SCALAR_1));

    }
}
