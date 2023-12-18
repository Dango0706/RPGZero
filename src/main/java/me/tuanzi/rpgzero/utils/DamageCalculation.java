package me.tuanzi.rpgzero.utils;

import me.tuanzi.rpgzero.quality.Quality;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import static me.tuanzi.rpgzero.RPGZero.logger;
import static me.tuanzi.rpgzero.utils.ItemStackUtils.getEquipments;
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
        logger.log(Level.FINE, "真实伤害:" + amount);
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
        logger.log(Level.FINE, "buff前攻击力:" + attackDamage);
        logger.log(Level.FINE, "buff前暴击率:" + (critRate * 100) + "%");
        logger.log(Level.FINE, "buff前暴击伤害:" + (critDamage * 100) + "%");
        logger.log(Level.FINE, "buff前增伤:" + (increase * 100) + "%");
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


        logger.log(Level.FINE, "buff后攻击力:" + attackDamage);
        logger.log(Level.FINE, "加攻击力后,伤害为:" + amount);
        logger.log(Level.FINE, "buff后暴击率:" + (critRate * 100) + "%");
        logger.log(Level.FINE, "buff后暴击伤害:" + (critDamage * 100) + "%");
        //暴击?
        double rank = new Random().nextDouble();
        if (rank <= critRate) {
            amount *= (1.0 + critDamage);
            if (attacker instanceof Player player) {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            logger.log(Level.FINE, "暴击!暴击后伤害:" + amount);
        }
        //buff加增伤/减伤
        //....

        logger.log(Level.FINE, "增伤数值:" + (increase * 100) + "%");
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
        logger.log(Level.FINE, "增伤后伤害:" + amount);
        //减去攻击速度
        if (attacker instanceof Player player) {
            amount *= player.getAttackCooldown();
        }
        logger.log(Level.FINE, "减去攻速后伤害:" + amount);
        logger.log(Level.FINE, "buff前防御力:" + def);
        logger.log(Level.FINE, "buff前物理抗性:" + physicalResistance);
        logger.log(Level.FINE, "buff前魔法抗性:" + magicResistance);
        //buff加防御力..
        //装备quality
        ArrayList<ItemStack> itemStacks = getEquipments(victim);
        for (ItemStack itemStack1 : itemStacks) {
            for (Quality quality : Quality.values()) {
                if (itemStack1 != null && itemStack1.hasItemMeta()) {
                    if (nbtGetString(itemStack1.getItemMeta(), "Quality").equals(quality.name())) {
                        def += quality.getDefense();
                        physicalResistance += quality.getPhysicalResistance();
                        magicResistance += quality.getMagicResistance();
                    }
                }
            }
        }
        //buff


        logger.log(Level.FINE, "buff后防御力:" + def);
        //计算防御力
        logger.log(Level.FINE, "防御力削减伤害值为:" + (amount - calculationDef(amount,def)));
        amount = calculationDef(amount,def);
//        amount -= amount * (def / (def + 75));
        logger.log(Level.FINE, "计算防御后伤害:" + amount);
        if (damageType == DamageType.PHYSICAL) {
            logger.log(Level.FINE, "伤害类型为物理!");
            physicalResistance += 0.1;
            logger.log(Level.FINE, "buff后抗性:" + physicalResistance);
            amount *= (1.0 - physicalResistance);
        } else if (damageType == DamageType.MAGIC) {
            logger.log(Level.FINE, "伤害类型为魔法!");
            logger.log(Level.FINE, "buff后抗性:" + magicResistance);
            amount *= (1.0 - magicResistance);
        } else {
            logger.log(Level.FINE, "真实伤害!不计算抗性");
        }

        logger.log(Level.FINE, "计算抗性后最终伤害:" + amount);
        logger.log(Level.FINE, "####################");
        return amount;
    }

    //伤害计算防御力后.
    public static double calculationDef(double damage, double def) {
        //防御力效果
        double a = 1.5;
        //稀释效果
        double b = 0.01;
        // 使用简单的算术运算，计算自身受到的伤害
        //防御力>=0
        if(def>=0){
            return damage / (1 + a * Math.log(1 + b * def));
        }
        //否则则增加受到的伤害
        a = 0.05;
        b = 0.035;
        return damage * (1 + Math.max(0, -def) * Math.min(1, a / (1 + b * Math.abs(def))));
    }

}
