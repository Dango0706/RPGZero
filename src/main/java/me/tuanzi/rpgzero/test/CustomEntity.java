package me.tuanzi.rpgzero.test;

import net.minecraft.server.level.BossBattleServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.BossBattle;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.level.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftChatMessage;

public class CustomEntity extends EntityZombie {

    private final BossBattleServer bossBar;
    public CustomEntity(EntityTypes<? extends EntityZombie> var0, World var1) {
        super(var0, var1);
        bossBar = new BossBattleServer(CraftChatMessage.fromString("haha", true)[0], BossBattle.BarColor.b, BossBattle.BarStyle.d);
        bossBar.a(true);
    }

    public CustomEntity(Location location) {
        this(EntityTypes.bp, ((CraftWorld) location.getWorld()).getHandle());
        //设置位置
        e(location.getX(),location.getY(),location.getZ());
    }
    //在身边
    @Override
    public void c(EntityPlayer entityplayer) {
        super.c(entityplayer);
        //添加player
        this.bossBar.a(entityplayer);
    }
    //不在身边
    @Override
    public void d(EntityPlayer entityplayer) {
        super.d(entityplayer);
        //移除player
        this.bossBar.b(entityplayer);
    }

    //tick
    @Override
    public void l() {
        super.l();
        //设置血量百分比
        this.bossBar.a(eu() / eL());
    }
}
