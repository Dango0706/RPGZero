package me.tuanzi.rpgzero.draw;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.tuanzi.rpgzero.draw.CreateItemStack.*;
import static me.tuanzi.rpgzero.items.JavaItems.SPECTRAL_FRAGMENT;

/**
 * 抽奖卡池
 * 蓝:15211000~15211999
 * 紫:15212000~15212999
 * 金:15213000~15213999
 */
public class DrawPools {
    //蓝色
    //武器
    static final ItemStack ENDLESS_FLAME = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211000, 8.5, 1.6, "无尽之焰", List.of("§7燃烧心灵，化敌为灰。"));
    static final ItemStack FUCKING_BREAD = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211001, 8.5, 1.6, "法国长棍", List.of("§7这也居然能当武器?!"));
    static final ItemStack FROSTBITE_EDGE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211002, 8.5, 1.6, "寒霜之刃", List.of("§7北极寒冰打造的剑，能够将敌人的血液冻结成冰柱。"));
    static final ItemStack FLAME_SOUL_BLADE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211003, 8.5, 1.6, "烈焰剑魂", List.of("§7拥有火焰之魂的剑，能够喷射出灼热的烈焰。"));
    static final ItemStack SHADOWMOON_BLADE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211004, 8.5, 1.6, "影月之刃", List.of("§7源自幽暗之地的剑，能够让使用者潜行于黑暗之中。"));
    static final ItemStack DRAGON_TOOTH = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211005, 8.5, 1.6, "巨龙之牙", List.of("§7由巨龙之牙制成的剑，带有巨龙的力量与威严。"));
    static final ItemStack STORM_BLADE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211006, 8.5, 1.6, "风暴之刃", List.of("§7能够引发狂风暴雨的剑，将敌人吹散如风。"));
    static final ItemStack SEAHEART_BLADE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211007, 8.5, 1.6, "碧海剑心", List.of("§7蕴含大海力量的剑，能够召唤汹涌的海浪攻击敌人。"));
    static final ItemStack THORNBLADE = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 15211008, 8.5, 1.6, "荆棘之刃", List.of("§7带有尖锐荆棘的剑，每一次攻击都能够让敌人感受到剧痛。"));
    //其他奖励
    static final ItemStack BLUE_SOME_DIAMOND = createMiscItemStack(Material.DIAMOND, Rarity.MYTHIC, 0, 15, null, (String) null);
    static final ItemStack BLUE_SOME_EMERALD = createMiscItemStack(Material.EMERALD, Rarity.MYTHIC, 0, 15, null, (String) null);
    static final ItemStack BLUE_SOME_GOLD_INGOT = createMiscItemStack(Material.GOLD_INGOT, Rarity.MYTHIC, 0, 32, null, (String) null);
    static final ItemStack BLUE_SOME_GOLDEN_CARROT = createMiscItemStack(Material.GOLDEN_CARROT, Rarity.MYTHIC, 0, 32, null, (String) null);
    static final ItemStack BLUE_SOME_GOLDEN_APPLE = createMiscItemStack(Material.GOLDEN_APPLE, Rarity.MYTHIC, 0, 8, null, (String) null);

    //紫色
    //武器
    //up
    static final ItemStack ROAR_OF_FROST = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 15212000, 9.25, 1.6, "霜之咆哮", List.of("§7寒冬之物，欲毁一切。"));
    static final ItemStack WIND_DANCE_BLADE = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 15212004, 8.25, 1.8, "狂风剑舞", List.of("§7轻盈如风的剑，每一次挥舞都能够掀起狂风的破坏力。"));
    //noup
    static final ItemStack ETERNAL_BLADE = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 15212002, 9.25, 1.6, "永恒之刃", List.of("§7由神秘材料打造的剑，拥有永恒不灭的力量。"));
    static final ItemStack FROST_SHADOW_BLADE = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 15212003, 9.25, 1.6, "霜寒冥刀", List.of("§7蕴含冰霜与黑暗力量的刀，能够冻结敌人的血液。"));
    static final ItemStack HOLY_LIGHT_JUDGMENT_SWORD = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 15212001, 9.25, 1.6, "圣光审判剑", List.of("§7由圣光赋予的神圣剑，能够驱散黑暗与邪恶。"));
    static final ItemStack ENDLESS_SHADOW_BLADE = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 15212005, 9.25, 1.6, "无尽黯影", List.of("§7蕴含无尽黑暗力量的剑，能够吞噬一切光明。"));
    //其他奖励
//    static final ItemStack SOME_SOUL_GEM = setItemStackCount(SOUL_GEM, 2);
    static final ItemStack SOME_SPECTRAL_FRAGMENT = setItemStackCount(SPECTRAL_FRAGMENT, 5);
    static final ItemStack PURPLE_SOME_DIAMOND = createMiscItemStack(Material.DIAMOND, Rarity.MAJESTIC, 0, 32, null, (String) null);
    static final ItemStack PURPLE_SOME_EMERALD = createMiscItemStack(Material.EMERALD, Rarity.MAJESTIC, 0, 32, null, (String) null);
    static final ItemStack PURPLE_SOME_GOLD_INGOT = createMiscItemStack(Material.GOLD_INGOT, Rarity.MAJESTIC, 0, 64, null, (String) null);
    static final ItemStack PURPLE_SOME_GOLDEN_CARROT = createMiscItemStack(Material.GOLDEN_CARROT, Rarity.MAJESTIC, 0, 64, null, (String) null);
    static final ItemStack PURPLE_SOME_GOLDEN_APPLE = createMiscItemStack(Material.GOLDEN_APPLE, Rarity.MAJESTIC, 0, 16, null, (String) null);
    static final ItemStack PURPLE_SOME_ENCHANTED_GOLDEN_APPLE = createMiscItemStack(Material.ENCHANTED_GOLDEN_APPLE, Rarity.MAJESTIC, 0, 8, null, (String) null);

    
    //金色
    //up
    static final ItemStack THUNDER_WRATH = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, true, true, 15213000, 12, 1.6, "雷霆之怒", List.of("§7闪电般的破坏力，摧毁一切敌意。"));
    //noup
    static final ItemStack HOLY_DRAGON_SOUL_SWORD = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 15213001, 12, 1.6, "圣龙断魂", List.of("§7由圣龙之魂注入的剑，能够撕裂敌人的灵魂。"));
    static final ItemStack FLAME_JUDGEMENT = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 15213002, 12, 1.6, "火焰审判", List.of("§7传说中的绝世之剑，只选择命运的守护者为其主人。"));
    static final ItemStack SHADOW_RECKONER = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 15213003, 12, 1.6, "暗影裁决者","§7在被黑暗笼罩的夜晚，暗影裁决者从深渊中崛起","§7它的剑刃闪耀着无尽的黑暗力量，准确地裁决那些与黑暗同谋的罪人。");


}
