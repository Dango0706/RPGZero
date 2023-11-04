package me.tuanzi.rpgzero.gui;

import me.tuanzi.rpgzero.draw.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static me.tuanzi.rpgzero.items.JavaItems.*;
import static me.tuanzi.rpgzero.quality.CreateQuality.refreshQuality;
import static me.tuanzi.rpgzero.utils.Config.getPlayerConfig;
import static me.tuanzi.rpgzero.utils.Config.setPlayerConfig;
import static me.tuanzi.rpgzero.utils.ItemStackUtils.*;
import static me.tuanzi.rpgzero.utils.PersistentDataContainerUtils.*;

public class ChestGUI implements Listener {

    private static Inventory mainGui;
    private static final ItemStack MAIN_GUI_DISPLAY;
    private static final ItemStack PLAYER_HEAD;
    private static Inventory settingGui;
    private static Inventory forgeGui;
    private static Inventory disintegrationGui;

    static {
        //设置ItemStack
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        setItemStackDisplayName(itemStack, "§a看啥,我只是装饰用的");
        MAIN_GUI_DISPLAY = itemStack;
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        PLAYER_HEAD = itemStack;

    }

    private static Inventory createEasyInventory(InventoryHolder holder, int size, String title, ItemStack display) {
        Inventory inventory = Bukkit.createInventory(holder, size * 9, title);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, display);
            inventory.setItem(size * 9 - 1 - i, display);
        }
        for (int i = 0; i < size; i++) {
            inventory.setItem(i * 9, display);
            if (i != 0) {
                inventory.setItem(i * 9 - 1, display);
            }
        }
        return inventory;
    }


    public static Inventory getMainGui(Player player) {
        //设置MainGui
        mainGui = createEasyInventory(new GUIHolder(player), 6, "§a主菜单", MAIN_GUI_DISPLAY);
        //添加
        mainGui.setItem(8, DISPLAY_CLOSE);
        mainGui.setItem(21, DISPLAY_DISINTEGRATION);
        mainGui.setItem(23, DISPLAY_REFRESH);
        mainGui.setItem(29, DISPLAY_FORGE);
        mainGui.setItem(31, HELP_BOOK);
        mainGui.setItem(37, DISPLAY_CONFIG);
        //设置需要获取player的物品
        SkullMeta skullMeta = (SkullMeta) PLAYER_HEAD.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getPlayer(player.getName()));
        skullMeta.setOwnerProfile(Bukkit.createPlayerProfile(player.getUniqueId()));
        skullMeta.setDisplayName("§b欢迎你:§a" + player.getName());
        PLAYER_HEAD.setItemMeta(skullMeta);
        mainGui.setItem(13, PLAYER_HEAD);
        return mainGui;
    }

    public static Inventory getDisintegrationGui (Player player) {
        disintegrationGui = createEasyInventory(new GUIHolder(player), 6, "§c分解台", MAIN_GUI_DISPLAY);
        ItemStack itemStack;
        itemStack = DISPLAY_ENABLE.clone();
        setItemStackDisplayName(itemStack, "§c分解以上物品");
        removeItemStackLore(itemStack);
        addItemStackLore(itemStack, "§c注意,此操作无法逆转!!!!!", "§4请再三确认没有贵重物品!!", "§b无效物品会自动返还");
        disintegrationGui.setItem(53, itemStack);
        disintegrationGui.setItem(8, DISPLAY_HOME);
        disintegrationGui.setItem(45, DISPLAY_BACK);
        return disintegrationGui;
    }

    public static Inventory getForgeGui(Player player) {
        ItemStack itemStack;
        //forgeGUI
        forgeGui = createEasyInventory(new GUIHolder(player), 3, "§d锻造", MAIN_GUI_DISPLAY);
        itemStack = DISPLAY_TIP.clone();
        addItemStackLore(itemStack, "§a将装备放在左边", "§a右边放入对应强化道具", "§a即可为装备锻造强化!");
        forgeGui.setItem(8, DISPLAY_HOME);
        forgeGui.setItem(10, itemStack);
        forgeGui.setItem(12, DISPLAY_ADD);
        forgeGui.setItem(14, DISPLAY_RIGHT_ARROW);
        forgeGui.setItem(15, new ItemStack(Material.BARRIER));
        forgeGui.setItem(16, DISPLAY_TIP);
        forgeGui.setItem(18, DISPLAY_BACK);
        itemStack = DISPLAY_ENABLE.clone();
        setItemStackDisplayName(itemStack, "§e确认强化!");
        removeItemStackLore(itemStack);
        forgeGui.setItem(26, itemStack);
        return forgeGui;
    }

    public static Inventory getSettingGui(Player player) {
        //设置SettingGUI
        settingGui = createEasyInventory(new GUIHolder(player), 6, "§a个人设置", MAIN_GUI_DISPLAY);
        settingGui.setItem(8, DISPLAY_HOME);
        settingGui.setItem(45, DISPLAY_BACK);

        boolean settings;
        ItemStack enable = DISPLAY_ENABLE.clone();
        ItemStack disable = DISPLAY_DISABLE.clone();
        settings = (boolean) getPlayerConfig(player, "isNewDamageCalculate");
        if (settings) {
            setItemStackDisplayName(enable, "是否启用新的计算模式?");
            addItemStackLore(enable, "§7是否启用新的计算伤害模式", "§7禁用则受到伤害与攻击敌人均不使用新机制计算");
            settingGui.setItem(20, enable);
        } else {
            setItemStackDisplayName(disable, "是否启用新的计算模式?");
            addItemStackLore(disable, "§7是否启用新的计算伤害模式", "§7禁用则受到伤害与攻击敌人均不使用新机制计算");
            settingGui.setItem(20, disable);
        }
        settings = (boolean) getPlayerConfig(player, "isDrawCountToast");
        if (settings) {
            removeItemStackLore(enable);
            setItemStackDisplayName(enable, "是否启用抽卡通知?");
            addItemStackLore(enable, "§7抽卡是否告知您抽数与保底数");
            settingGui.setItem(22, enable);
        } else {
            removeItemStackLore(disable);
            setItemStackDisplayName(disable, "是否启用抽卡通知?");
            addItemStackLore(disable, "§7抽卡是否告知您抽数与保底数");
            settingGui.setItem(22, disable);
        }
        return settingGui;
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        InventoryView inventoryView = player.getOpenInventory();
        if (inventoryView.getTitle().equals("§d锻造")) {
            ItemStack weapon = inventory.getItem(11);
            ItemStack refresh = inventory.getItem(13);
            if (weapon != null) {
                player.getInventory().addItem(weapon);
                inventory.setItem(11, null);
            }
            if (refresh != null) {
                player.getInventory().addItem(refresh);
                inventory.setItem(13, null);
            }
            inventory.setItem(15, null);
        }
        if (inventoryView.getTitle().equals("§c分解台")) {
            ItemStack itemStack;
            itemStack = DISPLAY_ENABLE.clone();
            setItemStackDisplayName(itemStack, "§c分解以上物品");
            removeItemStackLore(itemStack);
            addItemStackLore(itemStack, "§c注意,此操作无法逆转!!!!!", "§4请再三确认没有贵重物品!!", "§b无效物品会自动返还");
            e.getInventory().setItem(53, itemStack);
            //返还物品
            for (int i = 1; i < 5; i++) {
                int a = 9 * i + 1;
                for (int j = 0; j < 7; j++) {
                    ItemStack itemStack1 = inventory.getItem(a + j);
                    if (itemStack1 != null) {
                        player.getInventory().addItem(itemStack1);
                        inventory.setItem(a + j, null);
                    }
                }
            }


        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        InventoryView inventoryView = player.getOpenInventory();
        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof GUIHolder) {
            if (player.getName().equals(((GUIHolder) inventoryHolder).getOwner().getName())) {
                System.out.println(e.getRawSlot());
                if (inventoryView.getTitle().equals("§a主菜单")) {
                    //close
                    if (e.getRawSlot() == 8) {
                        player.closeInventory();
                    }
                    //disintegration
                    if(e.getRawSlot() == 21){
                        player.openInventory(getDisintegrationGui(player));
                    }
                    //refresh
                    if(e.getRawSlot() == 23){
                        Bukkit.dispatchCommand(player, "r refresh");
                        player.closeInventory();
                    }
                    //forge
                    if (e.getRawSlot() == 29) {
                        player.openInventory(getForgeGui(player));
                    }
                    //help_book
                    if(e.getRawSlot() == 31)
                        player.getInventory().addItem(HELP_BOOK);
                    //setting
                    if (e.getRawSlot() == 37) {
                        player.openInventory(getSettingGui(player));
                    }
                    e.setCancelled(true);
                }
                if (inventoryView.getTitle().equals("§a个人设置")) {
                    String name = player.getName();
                    ItemStack enable = DISPLAY_ENABLE.clone();
                    ItemStack disable = DISPLAY_DISABLE.clone();
                    boolean settings;
                    //back to home
                    if (e.getRawSlot() == 45 || e.getRawSlot() == 8) {
                        player.openInventory(getMainGui(player));
                    }
                    //启用新的计算模式?
                    if (e.getRawSlot() == 20) {
                        settings = (boolean) getPlayerConfig(player, "isNewDamageCalculate");
                        if (settings) {
                            setItemStackDisplayName(disable, "是否启用新的计算模式?");
                            addItemStackLore(disable, "§7是否启用新的计算伤害模式", "§7禁用则受到伤害与攻击敌人均不使用新机制计算");
                            inventory.setItem(20, disable.clone());
                            setPlayerConfig(name, "isNewDamageCalculate", false);
                        } else {
                            setItemStackDisplayName(enable, "是否启用新的计算模式?");
                            addItemStackLore(enable, "§7是否启用新的计算伤害模式", "§7禁用则受到伤害与攻击敌人均不使用新机制计算");
                            inventory.setItem(20, enable.clone());
                            setPlayerConfig(name, "isNewDamageCalculate", true);
                        }
                    }
                    if (e.getRawSlot() == 22) {
                        //启用抽卡通知?
                        settings = (boolean) getPlayerConfig(player, "isDrawCountToast");
                        if (settings) {
                            setItemStackDisplayName(disable, "是否启用抽卡通知?");
                            addItemStackLore(disable, "§7抽卡是否告知您抽数与保底数");
                            inventory.setItem(22, disable.clone());
                            setPlayerConfig(name, "isDrawCountToast", false);
                        } else {
                            setItemStackDisplayName(enable, "是否启用抽卡通知?");
                            addItemStackLore(enable, "§7抽卡是否告知您抽数与保底数");
                            inventory.setItem(22, enable.clone());
                            setPlayerConfig(name, "isDrawCountToast", true);
                        }
                    }
                    e.setCancelled(true);
                }
                if (inventoryView.getTitle().equals("§c分解台")) {
                    //取消与否
                    for (int i = 1; i < 7; i++) {
                        if (e.getRawSlot() == i * 9 || e.getRawSlot() == i * 9 - 1)
                            e.setCancelled(true);
                    }
                    for (int i = 0; i < 9; i++) {
                        if (e.getRawSlot() == i || e.getRawSlot() == 45 + i)
                            e.setCancelled(true);
                    }
                    if (e.getRawSlot() > 53) {
                        e.setCancelled(false);
                    }
                    //返回
                    if (e.getRawSlot() == 45 || e.getRawSlot() == 8) {
                        player.openInventory(getMainGui(player));
                    }
                    //点击确认
                    if (e.getRawSlot() == 53) {
                        ItemStack itemStack;
                        itemStack = e.getInventory().getItem(53);
                        //二次确认
                        if (itemStack.getItemMeta().getDisplayName().equals("§c分解以上物品")) {
                            itemStack = DISPLAY_ENABLE.clone();
                            setItemStackDisplayName(itemStack, "§c确认分解以上物品");
                            removeItemStackLore(itemStack);
                            addItemStackLore(itemStack, "§c注意,此操作无法逆转!!!!!", "§4请再三确认没有贵重物品!!", "§b无效物品会自动返还");
                            e.getInventory().setItem(53, itemStack);
                            e.setCancelled(true);
                            return;
                        }
                        //确认
                        if (itemStack.getItemMeta().getDisplayName().equals("§c确认分解以上物品")) {
                            itemStack = DISPLAY_ENABLE.clone();
                            setItemStackDisplayName(itemStack, "§c分解以上物品");
                            removeItemStackLore(itemStack);
                            addItemStackLore(itemStack, "§c注意,此操作无法逆转!!!!!", "§4请再三确认没有贵重物品!!", "§b无效物品会自动返还");
                            e.getInventory().setItem(53, itemStack);
                            //分解
                            int mythicCount = 0;
                            int supremeCount = 0;
                            for (int i = 1; i < 5; i++) {
                                int a = 9 * i + 1;
                                for (int j = 0; j < 7; j++) {
                                    ItemStack itemStack1 = inventory.getItem(a + j);
                                    if (itemStack1 != null) {
                                        if (itemStack1.hasItemMeta() && itemStack1.getItemMeta().hasCustomModelData()) {
                                            if (nbtGetString(itemStack1.getItemMeta(), "Rarity").equals(Rarity.MYTHIC.name())) {
                                                //等级 * 2
                                                int level = nbtGetInteger(itemStack1.getItemMeta(), "AttributeLevel");
                                                if (level > 0) {
                                                    mythicCount += level * 2;
                                                } else {
                                                    player.getInventory().addItem(itemStack1);
                                                    inventory.setItem(a + j, null);
                                                    continue;
                                                }
                                                inventory.setItem(a + j, null);
                                            } else if (nbtGetString(itemStack1.getItemMeta(), "Rarity").equals(Rarity.MAJESTIC.name())) {
                                                //基础4 每多一级+2
                                                int level = nbtGetInteger(itemStack1.getItemMeta(), "AttributeLevel");
                                                if (level > 0) {
                                                    mythicCount += (level - 1) * 2 + 4;
                                                } else {
                                                    player.getInventory().addItem(itemStack1);
                                                    inventory.setItem(a + j, null);
                                                    continue;
                                                }
                                                //up额外+4
                                                if (nbtGetBoolean(itemStack1.getItemMeta(), "IsUP")) {
                                                    mythicCount += 4;
                                                }
                                                inventory.setItem(a + j, null);
                                            } else if (nbtGetString(itemStack1.getItemMeta(), "Rarity").equals(Rarity.SUPREME.name())) {
                                                //基础6 每多一级+2
                                                int level = nbtGetInteger(itemStack1.getItemMeta(), "AttributeLevel");
                                                if (level > 0) {
                                                    mythicCount += (level - 1) * 2 + 6;
                                                } else {
                                                    player.getInventory().addItem(itemStack1);
                                                    inventory.setItem(a + j, null);
                                                    continue;
                                                }
                                                //up额外+6
                                                if (nbtGetBoolean(itemStack1.getItemMeta(), "IsUP")) {
                                                    mythicCount += 6;
                                                }
                                                supremeCount += 2;
                                                inventory.setItem(a + j, null);
                                            } else {
                                                player.getInventory().addItem(itemStack1);
                                                inventory.setItem(a + j, null);
                                            }
                                        } else {
                                            player.getInventory().addItem(itemStack1);
                                            inventory.setItem(a + j, null);
                                        }
                                    }

                                }
                            }
                            //返回物品
                            if (supremeCount > 0 || mythicCount > 0) {
                                ItemStack itemStack1 = SOUL_OF_SMELTING.clone();
                                itemStack1.setAmount(mythicCount);
                                player.getInventory().addItem(itemStack1);
                                itemStack1 = ESSENCE_OF_RESTORATION.clone();
                                itemStack1.setAmount(supremeCount);
                                player.getInventory().addItem(itemStack1);
                                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            }
                        }

                    }


                }
                if (inventoryView.getTitle().equals("§d锻造")) {
                    //禁止触摸的地方
                    for (int i = 0; i < 9; i++) {
                        if (e.getRawSlot() == i || e.getRawSlot() == 26 - i)
                            e.setCancelled(true);
                    }
                    if (e.getRawSlot() == 17
                            || e.getRawSlot() == 9
                            || e.getRawSlot() == 10
                            || e.getRawSlot() == 12
                            || e.getRawSlot() == 14
                            || e.getRawSlot() == 16
                    )
                        e.setCancelled(true);
                    ItemStack weapon = inventory.getItem(11);
                    ItemStack refresh = inventory.getItem(13);
                    //back to home
                    if (e.getRawSlot() == 18 || e.getRawSlot() == 8) {
                        player.openInventory(getMainGui(player));
                        //还物品在close方法中实现.
                    }
                    //in
                    if (e.getRawSlot() == 11 || e.getRawSlot() == 13) {

                    }
                    //go
                    if (e.getRawSlot() == 26) {
                        int weaponLevel;
                        int refreshLevel;
                        ItemStack failed = DISPLAY_DISABLE.clone();
                        setItemStackDisplayName(failed, "§c失败!");
                        removeItemStackLore(failed);
                        //这俩其中之一是null
                        if (weapon == null || refresh == null) {
                            ItemStack itemStack = DISPLAY_TIP.clone();
                            addItemStackLore(itemStack, "§a您确定物品栏里都有物品?");
                            inventory.setItem(16, itemStack);
                            inventory.setItem(15, failed);
                            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                            return;
                        }
                        //武器是不是新的
                        if (nbtGetBoolean(weapon.getItemMeta(), "isNew")) {
                            //获取稀有度
                            if (nbtGetString(weapon.getItemMeta(), "Rarity").equals(Rarity.MYTHIC.name())) {
                                weaponLevel = 1;
                            } else if (nbtGetString(weapon.getItemMeta(), "Rarity").equals(Rarity.MAJESTIC.name())) {
                                weaponLevel = 2;
                            } else if (nbtGetString(weapon.getItemMeta(), "Rarity").equals(Rarity.SUPREME.name())) {
                                weaponLevel = 3;
                                //没有稀有度
                            } else {
                                ItemStack itemStack = DISPLAY_TIP.clone();
                                addItemStackLore(itemStack, "§a您确定您的装备稀有度达标?");
                                inventory.setItem(16, itemStack);
                                inventory.setItem(15, failed);
                                player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                                return;
                            }
                            //没有品质
                            if (nbtGetString(weapon.getItemMeta(), "Quality").equals("Null")) {
                                ItemStack itemStack = DISPLAY_TIP.clone();
                                addItemStackLore(itemStack, "§a您确定您的装备有品质?");
                                inventory.setItem(16, itemStack);
                                inventory.setItem(15, failed);
                                player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                                return;
                            }//不是新的
                        } else {
                            ItemStack itemStack = DISPLAY_TIP.clone();
                            addItemStackLore(itemStack, "§a您确定您的装备有品质?");
                            inventory.setItem(16, itemStack);
                            inventory.setItem(15, failed);
                            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                            return;
                        }//刷新材料没有customModel
                        if (!refresh.getItemMeta().hasCustomModelData()) {
                            ItemStack itemStack = DISPLAY_TIP.clone();
                            addItemStackLore(itemStack, "§a您确定您的刷新材料放对了?");
                            inventory.setItem(16, itemStack);
                            inventory.setItem(15, failed);
                            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                            return;
                        }//检测刷新材料品质
                        if (refresh.getItemMeta().getCustomModelData() == 15210002 && nbtGetString(refresh.getItemMeta(), "Rarity").equals(Rarity.MYTHIC.name())) {
                            refreshLevel = 1;
                        } else if (refresh.getItemMeta().getCustomModelData() == 15210003 && nbtGetString(refresh.getItemMeta(), "Rarity").equals(Rarity.MAJESTIC.name())) {
                            refreshLevel = 2;
                        } else if (refresh.getItemMeta().getCustomModelData() == 15210004 && nbtGetString(refresh.getItemMeta(), "Rarity").equals(Rarity.SUPREME.name())) {
                            refreshLevel = 3;
                            //刷新材料不对
                        } else {
                            ItemStack itemStack = DISPLAY_TIP.clone();
                            addItemStackLore(itemStack, "§a您确定您的刷新材料放对了?");
                            inventory.setItem(16, itemStack);
                            inventory.setItem(15, failed);
                            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                            return;
                            //刷新材料品质大于等于武器品质
                        }
                        if (refreshLevel >= weaponLevel) {
                            player.playSound(player, Sound.BLOCK_ANVIL_USE, 1, 0.7f);
                            ItemStack result = refreshQuality(weapon);
                            inventory.setItem(11, setItemStackCount(weapon, weapon.getAmount() - 1));
                            inventory.setItem(13, setItemStackCount(refresh, refresh.getAmount() - 1));
                            inventory.setItem(15, result);
                            inventory.setItem(16, DISPLAY_TIP);
                            //不大于品质
                        } else {
                            ItemStack itemStack = DISPLAY_TIP.clone();
                            addItemStackLore(itemStack, "§a您的刷新材料等级太低了!");
                            inventory.setItem(15, failed);
                            inventory.setItem(16, itemStack);
                            player.playSound(player, Sound.BLOCK_ANVIL_HIT, 1, 0.7f);
                            return;
                        }


                    }
                    //如果15是绿宝石 或者是空的则取消
                    if (e.getRawSlot() == 15) {
                        if(inventory.getItem(15) == null){
                            inventory.setItem(15, new ItemStack(Material.BARRIER));
                        }
                        if (inventory.getItem(15).getType() == Material.EMERALD || inventory.getItem(15).getType() == Material.BARRIER){
                            e.setCancelled(true);
                            return;
                        }
                    }
                }

            }
        }

    }

}
