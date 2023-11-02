package me.tuanzi.rpgzero.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class GUIHolder implements InventoryHolder {
    private final Player owner;

    public GUIHolder(Player owner) {
        this.owner = owner;
    }

    public GUIHolder(UUID uuid){
        owner = Bukkit.getPlayer(uuid);
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public Inventory getInventory() {
        return null;
    }




}
