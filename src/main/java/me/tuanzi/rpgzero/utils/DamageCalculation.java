package me.tuanzi.rpgzero.utils;

import me.tuanzi.rpgzero.quality.Quality;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.utils.LivingEntityAttribute.*;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.nbtGetString;

/**
 * The type Damage calculation.
 */
public class DamageCalculation {

    /**
     * 伤害计算
     *
     * @param attacker   攻击者
     * @param victim     受伤者
     * @param damage     伤害量
     * @param damageType 伤害类型
     * @return 计算后伤害量
     */
    public static double damageCalculation(LivingEntity attacker, LivingEntity victim, double damage, DamageType damageType) {
        //最终伤害
        double amount = damage;
        javaPlugin.getLogger().info("真实伤害:" + amount);
        //敌人防御力
        double def = getLivingEntityTotalDefense(victim);
        //敌人抗性
        double physicalResistance = getLivingEntityTotalPhysicalResistance(victim);
        double magicResistance = getLivingEntityTotalMagicResistance(victim);
        //暴击率
        double critRate = getLivingEntityTotalCritRate(attacker);
        //暴击伤害
        double critDamage = getLivingEntityTotalCritDamage(attacker);
        //增伤减伤
        double increase = 0.0;
        //攻击力
        double attackDamage = getLivingEntityTotalAttackDamage(attacker);
        javaPlugin.getLogger().info("buff前攻击力:" + attackDamage);
        javaPlugin.getLogger().info("buff前暴击率:" + (critRate * 100) + "%");
        javaPlugin.getLogger().info("buff前暴击伤害:" + (critDamage * 100) + "%");
        javaPlugin.getLogger().info("buff前增伤:" + (increase * 100) + "%");
        ItemStack itemStack = attacker.getEquipment().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        //品质加伤害等
        for (Quality quality : Quality.values()) {
            if (nbtGetString(itemMeta, "Quality").equals(quality.name())) {
                attackDamage += quality.getAttackDamage();
                critRate += quality.getCritRate();
                critDamage += quality.getCritDamage();
                increase += quality.getIncrease();
            }
        }
        amount += attackDamage;
        //除增伤外buff


        javaPlugin.getLogger().info("buff后攻击力:" + attackDamage);
        javaPlugin.getLogger().info("加攻击力后,伤害为:" + amount);
        javaPlugin.getLogger().info("buff后暴击率:" + (critRate * 100) + "%");
        javaPlugin.getLogger().info("buff后暴击伤害:" + (critDamage * 100) + "%");
        //暴击?
        double rank = new Random().nextDouble();
        if (rank <= critRate) {
            amount *= (1.0 + critDamage);
            if (attacker instanceof Player player) {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            javaPlugin.getLogger().info("暴击!暴击后伤害:" + amount);
        }
        //buff加增伤/减伤
        //....

        javaPlugin.getLogger().info("增伤数值:" + (increase * 100) + "%");
        //计算增伤减伤
        //超过200%的部分衰减一半,超过-50%的部分衰减一半,无法-150%.
        if (increase >= 1.0) {
            amount *= (2.0 + ((1 + (increase - 1.0)) * 0.5));
        } else if (increase <= -1.5) {
            amount *= 0.01;
        } else if (increase <= -0.5) {
            amount *= (-0.5 - ((increase + 0.5) * 0.5/* (1.0 - Math.abs(increase + 0.5))*/));
        } else {
            amount *= (1 + increase);
        }
        javaPlugin.getLogger().info("增伤后伤害:" + amount);
        //减去攻击速度
        if(attacker instanceof Player player){
            amount *= player.getAttackCooldown();
        }
        javaPlugin.getLogger().info("减去攻速后伤害:" + amount);
        javaPlugin.getLogger().info("buff前防御力:" + def);
        javaPlugin.getLogger().info("buff前物理抗性:" + physicalResistance);
        javaPlugin.getLogger().info("buff前魔法抗性:" + magicResistance);
        //buff加防御力..

        ItemStack helmet = victim.getEquipment().getHelmet();
        ItemStack chest = victim.getEquipment().getChestplate();
        ItemStack leg = victim.getEquipment().getLeggings();
        ItemStack boot = victim.getEquipment().getBoots();
        for (Quality quality : Quality.values()) {
            try {
                if (nbtGetString(helmet.getItemMeta(), "Quality").equals(quality.name())) {
                    def += quality.getDefense();
                    physicalResistance += quality.getPhysicalResistance();
                    magicResistance += quality.getMagicResistance();
                }
                if (nbtGetString(chest != null ? chest.getItemMeta() : null, "Quality").equals(quality.name())) {
                    def += quality.getDefense();
                    physicalResistance += quality.getPhysicalResistance();
                    magicResistance += quality.getMagicResistance();
                }
                if (nbtGetString(leg.getItemMeta(), "Quality").equals(quality.name())) {
                    def += quality.getDefense();
                    physicalResistance += quality.getPhysicalResistance();
                    magicResistance += quality.getMagicResistance();
                }
                if (nbtGetString(boot.getItemMeta(), "Quality").equals(quality.name())) {
                    def += quality.getDefense();
                    physicalResistance += quality.getPhysicalResistance();
                    magicResistance += quality.getMagicResistance();
                }
            } catch (NullPointerException ignored) {

            }

        }
        javaPlugin.getLogger().info("buff后防御力:" + def);
        //计算防御力
        amount -= amount * (def / (def + 75));
        javaPlugin.getLogger().info("计算防御后伤害:" + amount);
        if (damageType == DamageType.PHYSICAL) {
            javaPlugin.getLogger().info("伤害类型为物理!");
            physicalResistance += 0.1;
            javaPlugin.getLogger().info("buff后抗性:" + physicalResistance);
            amount *= (1.0 - physicalResistance);
        } else if (damageType == DamageType.MAGIC) {
            
            javaPlugin.getLogger().info("伤害类型为魔法!");
            javaPlugin.getLogger().info("buff后抗性:" + magicResistance);
            amount *= (1.0 - magicResistance);
        } else {
            javaPlugin.getLogger().info("真实伤害!不计算抗性");
        }

        javaPlugin.getLogger().info("计算抗性后最终伤害:" + amount);
        javaPlugin.getLogger().info("##########");
        return amount;
    }


}
