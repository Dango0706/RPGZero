package me.tuanzi.rpgzero.utils;

import me.tuanzi.rpgzero.quality.Quality;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

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
     * @param puppet     是否是木偶?
     * @return 计算后伤害量
     */
    public static double damageCalculation(LivingEntity attacker, LivingEntity victim, double damage, DamageType damageType, boolean puppet) {
        //木偶输出
        Player player = null;
        if (puppet)
            player = (Player) attacker;
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
        //log
        logger.log(Level.FINE, "buff前攻击力:" + attackDamage);
        logger.log(Level.FINE, "buff前暴击率:" + (critRate * 100) + "%");
        logger.log(Level.FINE, "buff前暴击伤害:" + (critDamage * 100) + "%");
        logger.log(Level.FINE, "buff前增伤:" + (increase * 100) + "%");
        //send Puppet
        if (puppet) {
            player.sendMessage("§bbuff前攻击力:§c" + attackDamage);
            player.sendMessage("§bbuff前暴击率:§c" + (critRate * 100) + "%");
            player.sendMessage("§bbuff前暴击伤害:§c" + (critDamage * 100) + "%");
            player.sendMessage("§bbuff前增伤:§c" + (increase * 100) + "%");
        }
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
        logger.log(Level.FINE, "增加品质后攻击力:" + attackDamage);
        logger.log(Level.FINE, "增加品质后暴击率:" + (critRate * 100) + "%");
        logger.log(Level.FINE, "增加品质后暴击伤害:" + (critDamage * 100) + "%");
        logger.log(Level.FINE, "增加品质后增伤:" + (increase * 100) + "%");
        if (puppet) {
            player.sendMessage("§b增加品质后攻击力:§c" + attackDamage);
            player.sendMessage("§b增加品质后暴击率:§c" + (critRate * 100) + "%");
            player.sendMessage("§b增加品质后暴击伤害:§c" + (critDamage * 100) + "%");
            player.sendMessage("§b增加品质后增伤:§c" + (increase * 100) + "%");
        }
        amount += attackDamage;
        //除增伤外buff


        logger.log(Level.FINE, "buff后攻击力:" + attackDamage);
        logger.log(Level.FINE, "加攻击力后,伤害为:" + amount);
        logger.log(Level.FINE, "buff后暴击率:" + (critRate * 100) + "%");
        logger.log(Level.FINE, "buff后暴击伤害:" + (critDamage * 100) + "%");
        if (puppet) {
            player.sendMessage("§b增加buff后攻击力:§c" + attackDamage);
            player.sendMessage("§b增加buff后暴击率:§c" + (critRate * 100) + "%");
            player.sendMessage("§b增加buff后暴击伤害:§c" + (critDamage * 100) + "%");
            player.sendMessage("§b增加攻击力后,伤害为:§c" + amount);
        }
        //暴击?
        double rank = new Random().nextDouble();
        if (rank <= critRate) {
            amount *= (1.0 + critDamage);
            if (attacker instanceof Player player1) {
                player.playSound(player1, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            logger.log(Level.FINE, "暴击!暴击后伤害:" + amount);
            if (puppet)
                player.sendMessage("§b暴击!暴击后伤害:§c" + amount);

        } else {
            logger.log(Level.FINE, "此次攻击没有暴击!此次攻击需要暴击阈值为:" + (rank * 100) + "%");
            if (puppet)
                player.sendMessage("§b此次攻击没有暴击!此次攻击需要暴击阈值为:§c" + (rank * 100) + "%");
        }
        //buff加增伤/减伤
        //....

        logger.log(Level.FINE, "增伤数值:" + (increase * 100) + "%");
        if (puppet)
            player.sendMessage("§b增加buff后增伤数值:§c" + (increase * 100) + "%");
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
        if (puppet)
            player.sendMessage("§b增伤后伤害:§c" + amount);
        //减去攻击速度
        if (attacker instanceof Player player1) {
            amount *= player1.getAttackCooldown();
            logger.log(Level.FINE, "削减攻击进度(速度)后伤害:" + amount);
            if (puppet)
                player.sendMessage("§b削减攻击进度(速度)后伤害:§c" + amount);
        }
        logger.log(Level.FINE, "buff前敌人防御力:" + def);
        logger.log(Level.FINE, "buff前敌人物理抗性:" + (physicalResistance * 100) + "%");
        logger.log(Level.FINE, "buff前敌人魔法抗性:" + (magicResistance * 100) + "%");
        if (puppet) {
            player.sendMessage("§bbuff前敌人防御力:§c" + def);
            player.sendMessage("§bbuff前敌人物理抗性:§c" + (physicalResistance * 100) + "%");
            player.sendMessage("§bbuff前敌人魔法抗性:§c" + (magicResistance * 100) + "%");
        }

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
        logger.log(Level.FINE, "添加品质后敌人防御力:" + def);
        logger.log(Level.FINE, "添加品质后敌人物理抗性:" + (physicalResistance * 100) + "%");
        logger.log(Level.FINE, "添加品质后敌人魔法抗性:" + (magicResistance * 100) + "%");
        if (puppet) {
            player.sendMessage("§b添加品质后敌人防御力:§c" + def);
            player.sendMessage("§b添加品质后敌人物理抗性:§c" + (physicalResistance * 100) + "%");
            player.sendMessage("§b添加品质后敌人魔法抗性:§c" + (magicResistance * 100) + "%");
        }

        //buff


        logger.log(Level.FINE, "添加buff后防御力:" + def);
        if (puppet)
            player.sendMessage("§b添加buff后敌人防御力:§c" + def);
        //计算防御力
        logger.log(Level.FINE, "防御力削减伤害值为:" + (amount - calculationDef(amount, def)));
        if (puppet)
            player.sendMessage("§b防御力削减伤害值为:§c" + (amount - calculationDef(amount, def)));
        amount = calculationDef(amount, def);
        logger.log(Level.FINE, "计算防御后伤害:" + amount);
        if (puppet)
            player.sendMessage("§b计算防御后伤害:§c" + amount);

        if (damageType == DamageType.PHYSICAL) {
            logger.log(Level.FINE, "伤害类型为物理!");
            if (puppet)
                player.sendMessage("§b伤害类型为物理!");
            physicalResistance += 0.1;
            logger.log(Level.FINE, "增加buff后物理抗性:" + (physicalResistance * 100) + "%");
            if (puppet)
                player.sendMessage("§b增加buff后物理抗性:§c" + (physicalResistance * 100) + "%");
            amount *= (1.0 - physicalResistance);
        } else if (damageType == DamageType.MAGIC) {
            logger.log(Level.FINE, "伤害类型为魔法!");
            if (puppet)
                player.sendMessage("§b伤害类型为魔法!");
            logger.log(Level.FINE, "增加buff后魔法抗性:" + (magicResistance * 100) + "%");
            if (puppet)
                player.sendMessage("§b增加buff后魔法抗性:§c" + (magicResistance * 100) + "%");
            amount *= (1.0 - magicResistance);
        } else {
            logger.log(Level.FINE, "真实伤害!不计算抗性");
            if (puppet)
                player.sendMessage("§b真实伤害!不计算抗性");
        }
        logger.log(Level.FINE, "计算抗性后伤害:" + amount);
        if (puppet)
            player.sendMessage("§b计算抗性后伤害:§c" + amount);

        if (victim.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            int level = victim.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier() + 1;
            logger.log(Level.FINE, "有抗性药水!等级为:" + level);
            if (puppet)
                player.sendMessage("§b有抗性药水!等级为:§c" + level);
            amount *= (1 - 0.25 * level);
        }
        logger.log(Level.FINE, "计算药水效果后,最终伤害为:" + Math.max(amount, 0));
        logger.log(Level.FINE, "####################");
        if (puppet) {
            player.sendMessage("§b计算药水效果后,最终伤害为:§c" + Math.max(amount, 0));
            player.sendMessage("§d===============");
        }
        return Math.max(amount, 0);
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
