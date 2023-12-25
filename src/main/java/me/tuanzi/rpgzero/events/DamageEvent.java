package me.tuanzi.rpgzero.events;

import me.tuanzi.rpgzero.utils.DamageType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
        if (event.getEntity() instanceof LivingEntity victim) {
            //是生物近战攻击
            if (event.getDamager() instanceof LivingEntity attacker) {
                //木偶设置
                boolean puppet = victim instanceof ArmorStand
                        && attacker instanceof Player
                        && victim.getCustomName() != null
                        && victim.getCustomName().equals("puppet");

                if (attacker instanceof Player p
                        && !playerConfig.getBoolean(p.getDisplayName().toLowerCase() + ".isNewDamageCalculate", true))
                    return;
                if (victim instanceof Player p && !playerConfig.getBoolean(p.getDisplayName().toLowerCase() + ".isNewDamageCalculate", true))
                    return;
                logger.log(Level.FINE, "####################");
                logger.log(Level.FINE, "原最终伤害:" + event.getFinalDamage());
                if (puppet) {
                    Player player = (Player) attacker;
                    player.sendMessage("§a===============");
                    player.sendMessage("§b原最终伤害:" + event.getFinalDamage());
                }
                //盔甲伤害减免设置为0
                if (!(victim instanceof ArmorStand))
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
                //最终伤害
                double damage = damageCalculation(attacker, victim, event.getDamage(), damageType, puppet);

                //荆棘多于4的只触发20%.
                if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                    if (damage > 4) {
                        damage = (damage - 4) * 0.2 + 4;
                    }
                    event.setDamage(EntityDamageEvent.DamageModifier.BASE, damage);
                } else {
                    event.setDamage(EntityDamageEvent.DamageModifier.BASE, damage);
                }
            }

            //是生物远程攻击
            if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof LivingEntity attacker) {
                boolean puppet = victim instanceof ArmorStand
                        && attacker instanceof Player
                        && victim.getCustomName() != null
                        && victim.getCustomName().equals("puppet");
                if (puppet) {
                    event.setCancelled(true);
                    Player player = (Player) attacker;
                    player.sendMessage("§a===============");
                    player.sendMessage("§4[WARNING]§a检测到攻击源为远程!由于尚未制作远程计算结果,以下结果并非为最终结果!");
                    player.sendMessage("§b原最终伤害:" + event.getFinalDamage());
                    DamageType damageType = PHYSICAL;
                    damageCalculation(attacker, victim, event.getDamage(), damageType, true);
                    player.sendMessage("§4[WARNING]§a检测到攻击源为远程!由于尚未制作远程计算结果,以上结果并非为最终结果!");
                }
            }

        }
    }

}
