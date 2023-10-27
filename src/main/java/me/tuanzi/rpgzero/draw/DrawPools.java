package me.tuanzi.rpgzero.draw;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.tuanzi.rpgzero.draw.CreateItemStack.createMiscItemStack;
import static me.tuanzi.rpgzero.draw.CreateItemStack.createSwordItemStack;

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
    static final ItemStack SOME_DIAMOND = createMiscItemStack(Material.DIAMOND, Rarity.MYTHIC, 0, 15, null, null);
    static final ItemStack SOME_EMERALD = createMiscItemStack(Material.EMERALD, Rarity.MYTHIC, 0, 15, null, null);
    static final ItemStack SOME_GOLD_INGOT = createMiscItemStack(Material.GOLD_INGOT, Rarity.MYTHIC, 0, 32, null, null);
    static final ItemStack SOME_GOLDEN_CARROT = createMiscItemStack(Material.GOLDEN_CARROT, Rarity.MYTHIC, 0, 32, null, null);
    static final ItemStack SOME_GOLDEN_APPLE = createMiscItemStack(Material.GOLDEN_APPLE, Rarity.MYTHIC, 0, 8, null, null);

    //紫色
    //武器
    static final ItemStack ROAR_OF_FROST = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 15212000, 9.25, 1.6, "霜之咆哮", List.of("§7寒冬之物，欲毁一切。"));
    static final ItemStack b = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "test2", List.of("寒冬之物，欲毁一切。"));
    static final ItemStack bb = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 1000, 10, 1.6, "test1", List.of("寒冬之物，欲毁一切。"));
    //其他奖励

    //金色
    static final ItemStack THUNDER_WRATH = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, true, true, 15213000, 12, 1.6, "雷霆之怒", List.of("§7闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test1", List.of("闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cccc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test2", List.of("闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cccccc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test3", List.of("闪电般的破坏力，摧毁一切敌意。"));


}
