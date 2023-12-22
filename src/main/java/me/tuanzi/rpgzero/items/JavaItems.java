package me.tuanzi.rpgzero.items;

import me.tuanzi.rpgzero.draw.Rarity;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.persistence.PersistentDataType;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.draw.CreateItemStack.createDisplayItemStack;
import static me.tuanzi.rpgzero.draw.CreateItemStack.createMiscItemStack;

public class JavaItems {
    public static final ItemStack GUI_MENU;
    public static final ItemStack HELP_BOOK;
    public static final ItemStack RECIPE_BOOK;



    //has custom model
    public static final ItemStack SOUL_GEM;
    public static final ItemStack SPECTRAL_FRAGMENT;
    public static final ItemStack ETERNIAS_GAZE;
    public static final ItemStack REFRESHING_GLORY;
    public static final ItemStack RESTORATIVE_EMBER;
    public static final ItemStack SOUL_OF_SMELTING;
    public static final ItemStack ESSENCE_OF_RESTORATION;


    //display
    public static final ItemStack DISPLAY_CONFIG;
    public static final ItemStack DISPLAY_ENABLE;

    public static final ItemStack DISPLAY_DISABLE;
    public static final ItemStack DISPLAY_BACK;
    public static final ItemStack DISPLAY_HOME;
    public static final ItemStack DISPLAY_CLOSE;
    public static final ItemStack DISPLAY_FORGE;
    public static final ItemStack DISPLAY_TIP;
    public static final ItemStack DISPLAY_RIGHT_ARROW;
    public static final ItemStack DISPLAY_ADD;
    public static final ItemStack DISPLAY_DISINTEGRATION ;
    public static final ItemStack DISPLAY_REFRESH ;
    public static final ItemStack DISPLAY_WISH ;

    public static final ItemStack SPIRITUAL_STONE ;

    static {
        //不需要自定义模型
        //菜单
        GUI_MENU = createMiscItemStack(Material.CLOCK,Rarity.SINGULAR,0,1,"§aGUI菜单","§b按右键打开菜单","§b手机端点一下也可以打开");
        //帮助之书
        HELP_BOOK = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) HELP_BOOK.getItemMeta();
        bookMeta.setAuthor("团子");
        bookMeta.setTitle("帮助书");
        StringBuilder text = new StringBuilder();
        text.append("§a钟§r放在合成台上即可出现一个打开菜单的专属物品.\n");
        text.append("请最好装配§b资源包§r进行游玩!\n");
        text.append("俗称抽卡:在主世界§cx:909 y:63 z:-622§r有如何搭建.\n");
        text.append("抽卡概率与原神/崩铁相同.\n");
        text.append("合成表请输入/r recipe 获取.\n");
        bookMeta.addPage(text.toString());
        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
        HELP_BOOK.setItemMeta(bookMeta);
        //合成表
        RECIPE_BOOK = new ItemStack(Material.KNOWLEDGE_BOOK);
        KnowledgeBookMeta knowledgeBookMeta = (KnowledgeBookMeta) RECIPE_BOOK.getItemMeta();
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "SOUL_GEM_1"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "SOUL_GEM_2"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "ETERNIAS_GAZE"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "REFRESHING_GLORY"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "RESTORATIVE_EMBER"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "GUI_MENU"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "SPECTRAL_FRAGMENT_1"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "SPECTRAL_FRAGMENT_2"));
        knowledgeBookMeta.addRecipe(new NamespacedKey(javaPlugin, "SPIRITUAL_STONE"));
        RECIPE_BOOK.setItemMeta(knowledgeBookMeta);

        //灵魂之晶 15210000
        SOUL_GEM = createMiscItemStack(Material.EMERALD, Rarity.SUPREME, 15210000, 1, "§6灵魂之晶", "§7蕴含无尽智慧，触摸心灵深处。",
                "§7对着摆放在特定位置的信标前使用,",
                "§7灵魂之晶会散发出神秘光辉，连接灵魂与命运",
                "§7引领你探索未知的宇宙奥秘。",
                "§7还不快去试试看?!");
        ItemMeta itemMeta = SOUL_GEM.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "weaponDraw"), PersistentDataType.BOOLEAN, true);
        SOUL_GEM.setItemMeta(itemMeta);
        //幽光碎片 15210001
        SPECTRAL_FRAGMENT = createMiscItemStack(Material.ECHO_SHARD, Rarity.MAJESTIC, 15210001, 1, "§d幽光碎片",
                "§7蕴含着古老灵魂的能量"
                , "§7如同星辰之光在黑暗中闪耀"
                , "§7这些碎片或许是大自然赋予我们的礼物"
                , "§7握在手中，我们或许能够洞悉生命的奥秘"
                , "§7感悟灵魂的真谛。");
        //永恒之眼 15210002
        ETERNIAS_GAZE = createMiscItemStack(Material.EMERALD,Rarity.MYTHIC,15210002,1,"§b永恒之眼",
                "§7永恒之眼是一只神秘的宝石眼镜"
                ,"§7据说能够看穿时间与空间的迷雾"
                ,"§7揭示隐藏的力量"
                );
        //焕耀之光 0003
        REFRESHING_GLORY = createMiscItemStack(Material.EMERALD,Rarity.MAJESTIC,15210003,1,"§d焕耀之光",
                "§7来自遥远仙境的神奇珠宝"
                , "§7传说握紧它将焕发荣耀"
                , "§7唤醒心灵，获得新生"
        );
        //回春余烬 0004
        RESTORATIVE_EMBER = createMiscItemStack(Material.EMERALD, Rarity.SUPREME, 15210004, 1, "§6回春余烬",
                "§7来自魔法世界的神秘宝石"
                , "§7能够释放出强大的回春力量"
                , "§7使武器焕发出新的活力"
        );
        //b熔炼之魂 0005
        SOUL_OF_SMELTING = createMiscItemStack(Material.EMERALD, Rarity.MYTHIC, 15210005, 1, "§b熔炼之魂",
                "§7这个精华能够将废弃的金属重新熔化"
                , "§7赋予它们焕然一新的力量"
                , "§7因此成为了铁匠工坊的宝物"
                , "§7带来繁荣与创造力。"
                , "§8获取方式:分解台"
        );
        //d复原精华 0006
        ESSENCE_OF_RESTORATION = createMiscItemStack(Material.EMERALD, Rarity.MAJESTIC, 15210006, 1, "§d复原精华",
                "§7在遥远的魔法王国中"
                , "§7一位传奇魔法师发现了这个神秘物品"
                , "§7它可以通过注入物品中"
                , "§7使其焕发出崭新的光芒"
                , "§8获取方式:分解台"
        );
        //灵石 0007
        SPIRITUAL_STONE = createMiscItemStack(Material.EMERALD, Rarity.EXQUISITE, 15210007, 1, "§a灵石",
                "§7「灵石」是一种珍贵的合成物品"
                , "§7由金属和水晶构成，"
                , "§7能发挥出各自的优势"
                , "§7是一种多用途的物品"
        );
        //装饰用 15219000开头
        DISPLAY_CONFIG = createDisplayItemStack(Material.EMERALD, 15219000, "§b设置", (String) null);
        DISPLAY_ENABLE = createDisplayItemStack(Material.EMERALD, 15219001, null, "§b当前状态:§a启用");
        DISPLAY_DISABLE = createDisplayItemStack(Material.EMERALD, 15219002, null, "§b当前状态:§c关闭");
        DISPLAY_BACK = createDisplayItemStack(Material.EMERALD, 15219003, "§b返回上一层", (String) null);
        DISPLAY_HOME = createDisplayItemStack(Material.EMERALD, 15219004, "§b返回主页", (String) null);
        DISPLAY_CLOSE = createDisplayItemStack(Material.EMERALD, 15219005, "§c关闭GUI", (String) null);
        DISPLAY_FORGE = createDisplayItemStack(Material.EMERALD, 15219006, "§d锻造", "§7对武器进一步优化!");
        DISPLAY_TIP = createDisplayItemStack(Material.EMERALD, 15219007, "§b小提示", (String) null);
        DISPLAY_RIGHT_ARROW = createDisplayItemStack(Material.EMERALD, 15219008, "§b ", (String) null);
        DISPLAY_ADD = createDisplayItemStack(Material.EMERALD, 15219009, "§b ", (String) null);
        DISPLAY_DISINTEGRATION = createDisplayItemStack(Material.EMERALD, 15219010, "§b分解台", (String) null);
        DISPLAY_REFRESH = createDisplayItemStack(Material.EMERALD, 15219011, "§b更新背包内旧物品为新物品", (String) null);
        DISPLAY_WISH = createDisplayItemStack(Material.EMERALD, 15219012, "§b祈愿相关", (String) null);
    }

}
