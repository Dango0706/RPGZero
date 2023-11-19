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

import java.util.logging.Level;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.RPGZero.logger;
import static me.tuanzi.rpgzero.utils.Config.playerConfig;
import static me.tuanzi.rpgzero.utils.DamageCalculation.damageCalculation;
import static me.tuanzi.rpgzero.utils.DamageType.*;

public class DamageEvent implements Listener {

    @EventHandler
    public void Damage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity attacker && event.getEntity() instanceof LivingEntity victim) {
            if (attacker instanceof Player p && !playerConfig.getBoolean(p.getDisplayName().toLowerCase() + ".isNewDamageCalculate", true))
                return;
            if (victim instanceof Player p && !playerConfig.getBoolean(p.getDisplayName().toLowerCase() + ".isNewDamageCalculate", true))
                return;
            logger.log(Level.FINE,"####################");
            logger.log(Level.FINE,"原最终伤害:" + event.getFinalDamage());
            //盔甲伤害减免设置为0
            event.setDamage(EntityDamageEvent.DamageModifier.ARMOR, 0);
            //默认为物理伤害
            DamageType damageType = PHYSICAL;
            if (attacker.getEquipment().getItemInMainHand().getItemMeta() != null) {
                if (attacker.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "MAGIC"), PersistentDataType.BOOLEAN, false))
                    damageType = MAGIC;
                if (attacker.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PENETRATION"), PersistentDataType.BOOLEAN, false))
                    damageType = PENETRATION;
            }
            //魔法伤害
            if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC
                    || event.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH
                    || event.getCause() == EntityDamageEvent.DamageCause.POISON
                    || event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING
                    || event.getCause() == EntityDamageEvent.DamageCause.WITHER
                    || event.getCause() == EntityDamageEvent.DamageCause.THORNS
                    || event.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM
            ) {
                damageType = MAGIC;
            }
            //真实伤害
            if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING
                    || event.getCause() == EntityDamageEvent.DamageCause.KILL
                    || event.getCause() == EntityDamageEvent.DamageCause.WORLD_BORDER
                    || event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION
                    || event.getCause() == EntityDamageEvent.DamageCause.FALL
                    || event.getCause() == EntityDamageEvent.DamageCause.VOID
                    || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE
                    || event.getCause() == EntityDamageEvent.DamageCause.STARVATION
                    || event.getCause() == EntityDamageEvent.DamageCause.FREEZE
            ) {
                damageType = PENETRATION;
            }

            event.setDamage(EntityDamageEvent.DamageModifier.BASE, damageCalculation(attacker, victim, event.getDamage(), damageType));
        }

    }

}
