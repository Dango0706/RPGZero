package me.tuanzi.rpgzero.events;

import me.tuanzi.rpgzero.utils.DamageType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.draw.DrawItems.drawItem;
import static me.tuanzi.rpgzero.utils.DamageCalculation.damageCalculation;
import static me.tuanzi.rpgzero.utils.DamageType.MAGIC;
import static me.tuanzi.rpgzero.utils.DamageType.PHYSICAL;

public class DamageEvent implements Listener {

    @EventHandler
    public static void Damage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity victim) {
            System.out.println("##########");
            System.out.println("原最终伤害:" + event.getFinalDamage());
            //盔甲伤害减免设置为0
            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
            //默认为物理伤害
            DamageType damageType = PHYSICAL;
            if (attacker.getEquipment().getItemInMainHand().getItemMeta() != null) {
                if (attacker.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "MAGIC"), PersistentDataType.BOOLEAN, false))
                    damageType = MAGIC;
                if (attacker.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PENETRATION"), PersistentDataType.BOOLEAN, false))
                    damageType = DamageType.PENETRATION;

            }
            event.setDamage(EntityDamageEvent.DamageModifier.BASE, damageCalculation(attacker, victim, event.getDamage(), damageType));
            //test

            attacker.getWorld().dropItem(attacker.getLocation(), drawItem((Player) attacker));

        }

    }

}
