package me.tuanzi.rpgzero.utils;

import me.tuanzi.rpgzero.quality.Quality;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

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
        System.out.println("真实伤害:" + amount);
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
        System.out.println("buff前攻击力:" + attackDamage);
        //buff+攻击力...
        System.out.println("buff后攻击力:" + attackDamage);
        amount += attackDamage;
        System.out.println("加攻击力后,伤害为:" + amount);

        System.out.println("buff前暴击率:" + (critRate * 100) + "%");
        System.out.println("buff前暴击伤害:" + (critDamage * 100) + "%");
        System.out.println("buff前增伤:" + (increase * 100) + "%");
        //buff加暴击/爆伤
        //...
        ItemStack itemStack = attacker.getEquipment().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (Quality quality : Quality.values()) {
            if (nbtGetString(itemMeta, "Quality").equals(quality.name())) {
                attackDamage += quality.getAttackDamage();
                critRate += quality.getCritRate();
                critDamage += quality.getCritDamage();
                increase += quality.getIncrease();
            }
        }
        System.out.println("buff后暴击率:" + (critRate * 100) + "%");
        System.out.println("buff后暴击伤害:" + (critDamage * 100) + "%");
        //暴击?
        double rank = new Random().nextDouble();
        if (rank <= critRate) {
            amount *= (1.0 + critDamage);
            if (attacker instanceof Player player) {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            System.out.println("暴击!暴击后伤害:" + amount);
        }
        //buff加增伤/减伤
        //....
        System.out.println("增伤数值:" + (increase * 100) + "%");
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
        System.out.println("增伤后伤害:" + amount);
        System.out.println("buff前防御力:" + def);
        System.out.println("buff前物理抗性:" + physicalResistance);
        System.out.println("buff前魔法抗性:" + magicResistance);
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

        System.out.println("buff后防御力:" + def);
        //计算防御力
        amount -= amount * (def / (def + 200));
        System.out.println("计算防御后伤害:" + amount);


        if (damageType == DamageType.PHYSICAL) {
            System.out.println("伤害类型为物理!");
            physicalResistance += 0.1;
            System.out.println("buff后抗性:" + physicalResistance);
            amount *= (1.0 - physicalResistance);
        } else if (damageType == DamageType.MAGIC) {
            System.out.println("伤害类型为魔法!");
            System.out.println("buff后抗性:" + magicResistance);
            amount *= (1.0 - magicResistance);
        } else {
            System.out.println("真实伤害!不计算抗性");
        }

        System.out.println("计算抗性后最终伤害:" + amount);
        System.out.println("##########");
        return amount;
    }


}
