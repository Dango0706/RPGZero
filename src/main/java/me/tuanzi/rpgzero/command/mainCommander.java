package me.tuanzi.rpgzero.command;

import me.tuanzi.rpgzero.draw.DrawPools;
import me.tuanzi.rpgzero.items.JavaItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.attributes.CreateItemAttributes.refreshAttributes;
import static me.tuanzi.rpgzero.draw.CreateItemStack.refreshOldItem;
import static me.tuanzi.rpgzero.gui.ChestGUI.getMainGui;
import static me.tuanzi.rpgzero.items.JavaItems.RECIPE_BOOK;
import static me.tuanzi.rpgzero.quality.CreateQuality.refreshQuality;
import static me.tuanzi.rpgzero.utils.Config.getPlayerConfig;
import static me.tuanzi.rpgzero.utils.Config.setPlayerConfig;
import static me.tuanzi.rpgzero.utils.GeyserUtils.isBedrockPlayer;

public class mainCommander implements TabExecutor {
    private static final ArrayList<String> allItemsNames = new ArrayList<>();

    static {
        JavaItems javaItems = new JavaItems();
        Field[] fields = javaItems.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == ItemStack.class) {
                allItemsNames.add(field.getName());
            }
        }
        fields = DrawPools.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == ItemStack.class) {
                allItemsNames.add(field.getName());
            }
        }
    }

    //判断是否是数字
    static boolean isNumber(String input) {
        // 使用正则表达式判断是否为数字
        if (!input.matches("\\d+")) {
            return false;
        }
        // 将字符串转换为整数
        int number = Integer.parseInt(input);
        // 判断数字是否小于等于64
        return number <= 64;
    }

    public static void sendPlayerDrawCount(Player player){
        player.sendMessage("§e[系统]§a你总共抽了:§b" +
                (player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "TotalDrawCount"), PersistentDataType.INTEGER, 1) + 1) +
                "§a次,你距离下个四星保底约为§d" +
                (10 - 1 - player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "PurpleDrawCount"), PersistentDataType.INTEGER, 0) + 1) +
                "§a次,你距离下个五星保底为:§6" +
                (90 - 1 - player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "GoldenDrawCount"), PersistentDataType.INTEGER, 0) + 1) +
                "§a次,你下次紫色是否是up:§d" +
                player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListPurple"), PersistentDataType.BOOLEAN, false) +
                "§a你下个五星是否是up:§6" +
                player.getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "isListGolden"), PersistentDataType.BOOLEAN, false));
    }


    //名字获取类中的ItemStack
    static ItemStack getItemStackByName(String name) {
        try {
            JavaItems javaItems = new JavaItems();
            // 获取类的字段
            Field field = javaItems.getClass().getDeclaredField(name);
            return (ItemStack) field.get(javaItems);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            try {
                DrawPools drawPools = new DrawPools();
                Field field1 = drawPools.getClass().getDeclaredField(name);
                field1.setAccessible(true);
                ItemStack itemStack = (ItemStack) field1.get(drawPools);
                itemStack = refreshQuality(itemStack);
                itemStack = refreshAttributes(itemStack);
                return itemStack;
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                return new ItemStack(Material.AIR);
            }

        }
    }


    static void sendHelp(CommandSender sender) {
        sender.sendMessage("RPGZero使用指南<可以简略为/r>"
                , "/rpg help <页数> 指令帮助"
                , "/rpg config <设置项目> <设置值> 个人设置"
                , "/rpg server <设置项目> <设置值> 服务器设置"
                , "/rpg draw 抽卡查询"
                , "/rpg gui 打开GUI界面"
                , "/rpg refresh 给背包里所有旧的装备一个Quality与Attribute."
                , "/rpg recipe 获取一本知识之书,用于获取合成配方."
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        if (args[0].equals("config")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    sender.sendMessage("可选的配置有:\nisDrawCountToast:是否开启抽卡次数通知 您目前为:"
                                    + getPlayerConfig(player, "isDrawCountToast")
                            , "isNewDamageCalculate:是否启用新的伤害计算模式?关闭则使您攻击与被攻击均不使用新的伤害计算模式. 您目前为:"
                                    + getPlayerConfig(player, "isNewDamageCalculate"));
                    return true;
                } else if (args.length == 2) {
                    sender.sendMessage("请选择 true 还是 false");
                    return true;
                } else if (args.length == 3) {
                    String key;
                    if (args[1].equals("isDrawCountToast")) {
                        key = "isDrawCountToast";
                    } else if (args[1].equals("isNewDamageCalculate")) {
                        key = "isNewDamageCalculate";
                    } else {
                        sender.sendMessage("您的设置选项好像有些问题?");
                        return true;
                    }
                    if (args[2].equals("true")) {
                        if ((Boolean) getPlayerConfig(player, key)) {
                            sender.sendMessage("此设置已经是true了!");
                            return true;
                        }
                        setPlayerConfig(player, key, true);
                        sender.sendMessage("个人设置:" + key + "设置为:true");
                        return true;
                    } else if (args[2].equals("false")) {
                        if (!(Boolean) getPlayerConfig(player, key)) {
                            sender.sendMessage("此设置已经是false了!");
                            return true;
                        }
                        setPlayerConfig(player, key, false);
                        if (key.equals("isNewDamageCalculate")) {
                            //清除攻速加成
                            for (AttributeModifier attributeModifier : player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getModifiers()) {
                                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(attributeModifier);
                            }
                        }
                        sender.sendMessage("个人设置:" + key + "设置为:false");
                        return true;
                    } else {
                        sender.sendMessage("请选择true还是false");
                        return true;
                    }
                }
            } else {
                sender.sendMessage("这条指令只有玩家才能使用哦!");
                return true;
            }
        } else if (args[0].equals("gui")) {
            if (sender instanceof Player player) {
                if (isBedrockPlayer(player)) {
                    //是基岩版的玩家

                    return true;
                } else {
                    //是Java版的玩家
                    player.openInventory(getMainGui(player));
                    return true;
                }
            } else {
                sender.sendMessage("这条指令只有玩家才能使用哦!");
                return true;
            }
        } else if (args[0].equals("server")) {
            if (sender.hasPermission("rpgzero.command.server")) {
                if (args.length == 1) {
                    //xxxx

                    return true;
                }
                if (args[1].equals("give")) {
                    if (sender instanceof Player player) {
                        if (args.length == 2) {
                            player.sendMessage("请输入需要给予的物品!");
                            return true;
                        }
                        if (args.length == 3) {
                            if (allItemsNames.contains(args[2])) {
                                player.getInventory().addItem(getItemStackByName(args[2]));
                                player.sendMessage("成功将1个[" + getItemStackByName(args[2]).getItemMeta().getDisplayName() + "§f]给予给:" + player.getName());
                            } else {
                                player.sendMessage("啊哦!你输入的不是正确的名字哦!");
                            }
                            return true;
                        }
                        if (args.length == 4) {
                            if (allItemsNames.contains(args[2])) {
                                ItemStack itemStack = getItemStackByName(args[2]);
                                int count = 1;
                                if (isNumber(args[3]))
                                    count = Integer.parseInt(args[3]);
                                else
                                    player.sendMessage("你输入的数量错误!不能大于64,或不是数字,已自动重置为1");
                                itemStack.setAmount(count);
                                player.getInventory().addItem(itemStack);
                                player.sendMessage("成功将" + count + "个[" + itemStack.getItemMeta().getDisplayName() + "§f]给予给:" + player.getName());
                            } else {
                                player.sendMessage("啊哦!你输入的不是正确的名字哦!");
                            }
                            return true;
                        }
                        if (args.length == 5) {
                            if (allItemsNames.contains(args[2])) {
                                ItemStack itemStack = getItemStackByName(args[2]);
                                int count = 1;
                                if (isNumber(args[3]))
                                    count = Integer.parseInt(args[3]);
                                else
                                    player.sendMessage("你输入的数量错误!不能大于64,或不是数字,已自动重置为1");
                                itemStack.setAmount(count);
                                ArrayList<String> playerName = new ArrayList<>();
                                for (Player player1 : Bukkit.getOnlinePlayers()) {
                                    playerName.add(player1.getName());
                                }
                                if (playerName.contains(args[4])) {
                                    Bukkit.getPlayer(args[4]).getInventory().addItem(itemStack);
                                    player.sendMessage("成功将" + count + "个[" + itemStack.getItemMeta().getDisplayName() + "§f]给予给:" + args[4]);
                                } else {
                                    player.sendMessage("你输入的玩家名称不在线!");
                                    return true;
                                }
                            } else {
                                player.sendMessage("啊哦!你输入的不是正确的名字哦!");
                            }
                            return true;
                        }

                    } else {
                        sender.sendMessage("只有玩家可以使用这个命令!");
                        return true;
                    }
                } else if (args[1].equals("quality")) {
                    if (sender instanceof Player player) {
                        player.getEquipment().setItemInMainHand(refreshQuality(player.getEquipment().getItemInMainHand()));
                    } else {
                        sender.sendMessage("只有玩家可以使用这个命令!");
                    }
                    return true;

                } else if (args[1].equals("attribute")) {
                    if (sender instanceof Player player) {
                        player.getEquipment().setItemInMainHand(refreshAttributes(player.getEquipment().getItemInMainHand()));
                    } else {
                        sender.sendMessage("只有玩家可以使用这个命令!");
                    }
                    return true;

                }
            } else {
                sender.sendMessage("你没有使用这个指令的权限!");
                return true;
            }
        } else if (args[0].equals("refresh")) {
            if (sender instanceof Player player) {
                player.sendMessage("刷新品质中...");
                for (int i = 0; i < 200; i++) {
                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() != Material.AIR) {
                        player.getInventory().setItem(i, refreshOldItem(player.getInventory().getItem(i)));
                    }
                }
                player.sendMessage("刷新完成!");
            } else {
                sender.sendMessage("只有玩家可以使用这个命令!");
                return true;
            }
        } else if (args[0].equals("draw")) {
            if (sender instanceof Player player) {
                sendPlayerDrawCount(player);
            } else {
                sender.sendMessage("只有玩家可以使用这个命令!");
                return true;
            }

        } else if (args[0].equals("help")) {
            sendHelp(sender);
            return true;
        }else if (args[0].equals("recipe")){
            if(sender instanceof Player player){
                player.getInventory().addItem(RECIPE_BOOK);
            } else {
                sender.sendMessage("只有玩家可以使用这个命令!");
            }
            return true;
        }

        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            // 控制台注册个鬼
            return null;
        }
        if (args.length == 0 || args.length == 1) {
            //返回config,help,draw,server四个list
            return Arrays.asList("config", "help", "draw", "server", "gui", "refresh","recipe");
        }
        if (args[0].equals("config")) {
            if (args.length == 3) {
                return Arrays.asList("true", "false");
            } else if (args.length == 4) {
                return Collections.singletonList("后面没有参数辣!!");
            }else if(args.length == 5){
                return Collections.singletonList("你还想怎样!!!");
            }
            return Arrays.asList("isDrawCountToast", "isNewDamageCalculate");
        }
        if (args[0].equals("server")) {
            if (args.length == 2)
                return Arrays.asList("give", "quality","attribute");
            if (args[1].equals("give")) {
                if (args.length == 3)
                    return allItemsNames;
                if (args.length == 4)
                    return List.of("[<Count>]");
                if (args.length == 5){
                    ArrayList<String> name = new ArrayList<>();
                    for(Player player : Bukkit.getOnlinePlayers()){
                        name.add(player.getName());
                    }
                    return name;
                }else{
                    return null;
                }
            }
        }

        return null;
    }
}
