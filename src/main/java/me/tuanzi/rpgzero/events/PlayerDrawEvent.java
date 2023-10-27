package me.tuanzi.rpgzero.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.geysermc.cumulus.form.ModalForm;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.draw.DrawItems.drawItem;
import static me.tuanzi.rpgzero.utils.GeyserUtils.isBedrockPlayer;
import static me.tuanzi.rpgzero.utils.GeyserUtils.sendForm;

public class PlayerDrawEvent implements Listener {
    static Material getBlockType(World world, int x, int y, int z) {
        return world.getBlockAt(x, y, z).getType();
    }



    @EventHandler
    public void draw(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isBedrockPlayer(event.getPlayer()) && event.getPlayer().getEquipment().getItemInMainHand().getType() == Material.CLOCK) {
                ModalForm form = ModalForm.builder()
                        .title("标题")
                        .content("哈哈!")
                        .button1("按钮")
                        .button2("按钮").build();
                sendForm(event.getPlayer(), form);
            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Location location = event.getClickedBlock().getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            World world = event.getPlayer().getWorld();
            //检测block
            if (event.getClickedBlock().getType() == Material.BEACON
                    && getBlockType(world, x, y - 1, z) == Material.DIAMOND_BLOCK
                    && getBlockType(world, x - 1, y - 1, z) == Material.EMERALD_BLOCK
                    && getBlockType(world, x + 1, y - 1, z) == Material.EMERALD_BLOCK
                    && getBlockType(world, x, y - 1, z - 1) == Material.EMERALD_BLOCK
                    && getBlockType(world, x, y - 1, z + 1) == Material.EMERALD_BLOCK
                    && getBlockType(world, x + 1, y - 1, z + 1) == Material.NETHERITE_BLOCK
                    && getBlockType(world, x - 1, y - 1, z + 1) == Material.NETHERITE_BLOCK
                    && getBlockType(world, x - 1, y - 1, z - 1) == Material.NETHERITE_BLOCK
                    && getBlockType(world, x + 1, y - 1, z - 1) == Material.NETHERITE_BLOCK
                    && getBlockType(world, x, y - 1, z - 2) == Material.PEARLESCENT_FROGLIGHT
                    && getBlockType(world, x, y - 1, z + 2) == Material.VERDANT_FROGLIGHT
                    && getBlockType(world, x + 2, y - 1, z) == Material.OCHRE_FROGLIGHT
                    && getBlockType(world, x - 2, y - 1, z) == Material.OCHRE_FROGLIGHT
                    && getBlockType(world, x, y, z - 2) == Material.END_ROD
                    && getBlockType(world, x, y, z + 2) == Material.END_ROD
                    && getBlockType(world, x + 2, y, z) == Material.END_ROD
                    && getBlockType(world, x - 2, y, z) == Material.END_ROD
                    && getBlockType(world, x, y + 1, z - 2) == Material.CHAIN
                    && getBlockType(world, x, y + 1, z + 2) == Material.CHAIN
                    && getBlockType(world, x + 2, y + 1, z) == Material.CHAIN
                    && getBlockType(world, x - 2, y + 1, z) == Material.CHAIN
                    && getBlockType(world, x, y + 2, z - 2) == Material.END_ROD
                    && getBlockType(world, x, y + 2, z + 2) == Material.END_ROD
                    && getBlockType(world, x + 2, y + 2, z) == Material.END_ROD
                    && getBlockType(world, x - 2, y + 2, z) == Material.END_ROD
                    && getBlockType(world, x, y + 3, z) == Material.LIGHT_BLUE_STAINED_GLASS
                    && getBlockType(world, x - 1, y + 3, z) == Material.REDSTONE_BLOCK
                    && getBlockType(world, x + 1, y + 3, z) == Material.REDSTONE_BLOCK
                    && getBlockType(world, x, y + 3, z - 1) == Material.REDSTONE_BLOCK
                    && getBlockType(world, x, y + 3, z + 1) == Material.REDSTONE_BLOCK
                    && getBlockType(world, x + 1, y + 3, z + 1) == Material.GOLD_BLOCK
                    && getBlockType(world, x - 1, y + 3, z + 1) == Material.GOLD_BLOCK
                    && getBlockType(world, x - 1, y + 3, z - 1) == Material.GOLD_BLOCK
                    && getBlockType(world, x + 1, y + 3, z - 1) == Material.GOLD_BLOCK
                    && getBlockType(world, x, y + 3, z - 2) == Material.PEARLESCENT_FROGLIGHT
                    && getBlockType(world, x, y + 3, z + 2) == Material.VERDANT_FROGLIGHT
                    && getBlockType(world, x + 2, y + 3, z) == Material.OCHRE_FROGLIGHT
                    && getBlockType(world, x - 2, y + 3, z) == Material.OCHRE_FROGLIGHT
            ) {
                //方块正确
                event.setCancelled(true);
                if (player.getEquipment().getItemInMainHand().hasItemMeta()
                        && player.getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(javaPlugin, "weaponDraw"), PersistentDataType.BOOLEAN, false)
                ) {
                    //不是创造则消耗物品
                    if (!(player.getGameMode() == GameMode.CREATIVE))
                        player.getEquipment().getItemInMainHand().setAmount(player.getEquipment().getItemInMainHand().getAmount() - 1);
                    //抽卡
                    world.dropItem(new Location(world, x + 0.5, y + 1, z + 0.5), drawItem(player, new Location(world, x, y + 1, z)));

                } else {
                    player.sendMessage("古老的信标并没有回应你...");
                }
            }
        } else {
            return;
        }
    }
}
