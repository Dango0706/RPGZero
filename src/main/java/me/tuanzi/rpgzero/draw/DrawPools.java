package me.tuanzi.rpgzero.draw;

import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.tuanzi.rpgzero.draw.CreateItemStack.createSwordItemStack;

/**
 * 抽奖卡池
 */
public class DrawPools {

    static final ItemStack ENDLESS_FLAME = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "无尽之焰", List.of("燃烧心灵，化敌为灰。"));
    static final ItemStack e = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "无尽之焰", List.of("燃烧心灵，化敌为灰。"));
    static final ItemStack c = createSwordItemStack(Rarity.MYTHIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "无尽之焰", List.of("燃烧心灵，化敌为灰。"));

    static final ItemStack ROAR_OF_FROST = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "霜之咆哮", List.of("寒冬之物，欲毁一切。"));
    static final ItemStack b = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, true, true, 1000, 10, 1.6, "test2", List.of("寒冬之物，欲毁一切。"));
    static final ItemStack bb = createSwordItemStack(Rarity.MAJESTIC, ItemType.SWORD, false, false, 1000, 10, 1.6, "test1", List.of("寒冬之物，欲毁一切。"));

    static final ItemStack THUNDER_WRATH = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, true, true, 1000, 10, 1.6, "雷霆之怒", List.of("闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test1", List.of("闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cccc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test2", List.of("闪电般的破坏力，摧毁一切敌意。"));
    static final ItemStack cccccc = createSwordItemStack(Rarity.SUPREME, ItemType.SWORD, false, false, 1000, 10, 1.6, "test3", List.of("闪电般的破坏力，摧毁一切敌意。"));


}
