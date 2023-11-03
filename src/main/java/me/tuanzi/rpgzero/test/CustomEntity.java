package me.tuanzi.rpgzero.test;

import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityGiantZombie;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;

public class CustomEntity extends EntityGiantZombie {

    public CustomEntity(EntityTypes<? extends EntityGiantZombie> var0, World var1) {
        super(var0, var1);
    }

    public CustomEntity(Location location) {
        this(EntityTypes.R, ((CraftWorld) location.getWorld()).getHandle());

    }
}
