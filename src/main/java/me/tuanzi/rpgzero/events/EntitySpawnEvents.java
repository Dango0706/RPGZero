package me.tuanzi.rpgzero.events;


import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import static me.tuanzi.rpgzero.draw.CreateItemStack.refreshOldItem;

public class EntitySpawnEvents implements Listener {
    @EventHandler
    public void EntitySpawnCreateItem(EntitySpawnEvent event) {
        //怪物有装备 则更新他的装备
        if (event.getEntity() instanceof Monster monster) {
            ItemStack mainHand = monster.getEquipment().getItemInMainHand();
            ItemStack head = monster.getEquipment().getHelmet();
            ItemStack chest = monster.getEquipment().getChestplate();
            ItemStack leg = monster.getEquipment().getLeggings();
            ItemStack boot = monster.getEquipment().getBoots();
            if (mainHand.getType() != Material.AIR && mainHand.getType()!=Material.BOW ) {
                monster.getEquipment().setItemInMainHand(refreshOldItem(mainHand));
            }
            if (head != null) {
                monster.getEquipment().setHelmet(refreshOldItem(head));
            }
            if (chest != null) {
                monster.getEquipment().setChestplate(refreshOldItem(chest));
            }
            if (leg != null) {
                monster.getEquipment().setLeggings(refreshOldItem(leg));
            }
            if (boot != null) {
                monster.getEquipment().setBoots(refreshOldItem(boot));
            }
        }
    }
}
