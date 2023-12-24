package me.tuanzi.rpgzero.test;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TestEvent implements Listener {

    //bbs
    public static Entity getTargetEntity1(Player player) {
        player.sendMessage("=========");
        //如果指令发出者不是玩家，不进行处理
        if (player == null) {
            return null;
        }
        //获取指令发送者的位置信息
        Location senderL = player.getLocation();
        //获取指令发送者的视线方向
        Location eyeLocation = player.getEyeLocation();
        //将视线方向转换为矢量
        Vector sight = eyeLocation.getDirection();
        //寻找以此实体为中心，半径20范围内的全部实体
        List<Entity> entities = player.getNearbyEntities(20, 20, 20);
        //实例化一个用于储存目标实体与发送者的视线夹角为90°以内的列表
        ArrayList<Entity> nearSightEntities = new ArrayList<Entity>();
        //遍历全部实体，寻找与指令发送者视线夹角在90°以内且可见的实体
        for (Entity e : entities) {
            //如果当前实体不可见，跳过
            if (!(player.hasLineOfSight(e))) {
                continue;
            }
            //将视线方向转换为模为1的向量
            Vector dir = sight.normalize();
            //获取当前遍历实体的位置
            Location eL = e.getLocation();
            //构建一个从指令发送者指向当前实体的矢量
            Vector v = eL.subtract(senderL).toVector();
            //获取夹角
            player.sendMessage(String.format("你们之间夹角的弧度值为%.2f", v.angle(sight)));
            //夹角小于90度
            if (Math.toDegrees(v.angle(sight)) <= 90) {
                player.sendMessage(String.format("寻获夹角为%.2f度的实体", Math.toDegrees(v.angle(sight))));
                //计算该实体坐标到视线的距离，距离小于0.4的实体认为是与视线产生碰撞的实体
                //构造一个以当前视线为法向量，且过该实体坐标的平面
                //构造的平面以数组形式存储，其中角标0,1,2分别为A,B,C，角标3为D。该平面为Ax+By+Cz+D = 0
                Double[] F = new Double[]{dir.getX(), dir.getY(), dir.getZ(), -(dir.getX() * e.getLocation().getX() + dir.getY() * e.getLocation().getY() + dir.getZ() * e.getLocation().getZ())};
                //构造一个以命令发送者的视线方向为方向向量，且过命令发送者所在坐标(x0,y0,z0)的直线(x-x0)/a = (y-y0)/b = (z-z0)/c
                //则该直线上的一点可以表示为(at+x0),(bt+y0),(ct+z0)
                //将此点带入平面，求出t值A(at+x0)+B(bt+y0)+C(ct+z0)+D = 0
                //推导
                //Aat+Ax0+Bbt+By0+Cct+Cz0+D = 0
                //Aat+Bbt+Cct+Ax0+By0+Cz0+D = 0
                //Aat+Bbt+Cct = -(Ax0+By0+Cz0+D)
                //(Aa+Bb+Cc)t = -(Ax0+By0+Cz0+D)
                //t = -(Ax0+By0+Cz0+D)/(Aa+Bb+Cc)
                //根据推导公式求t
                double t = -(F[0] * senderL.getX() + F[1] * senderL.getY() + F[2] * senderL.getZ() + F[3]) / (F[0] * dir.getX() + F[1] * dir.getY() + F[2] * dir.getZ());
                //将t代入(at+x0),(bt+y0),(ct+z0)，求得点P
                Location P = new Location(e.getWorld(), dir.getX() * t + senderL.getX(), dir.getY() * t + senderL.getY(), dir.getZ() * t + senderL.getZ());
                //求实体e坐标到点P之间的距离，距离小于0.4认为与视线发生碰撞
                player.sendMessage(String.format("该实体是:" +
                        e.getType().name() +
                        "与你的视线的直线距离为%.2f", e.getLocation().distance(P)));
                if (e.getLocation().distance(P) <= 0.4) {
                    nearSightEntities.add(e);
                }
            }
        }
        //如果没有视线夹角在90°内的实体，返回空
        if (nearSightEntities.size() < 1) {
            return null;
        }
        //用于储存最短距离的实体的对象
        Entity result = null;
        //遍历夹角在90度以内，发送者可见的实体，寻找距离最近的实体
        for (Entity e : nearSightEntities) {
            Location eL = e.getLocation();
            if (result == null) {
                result = e;
                continue;
            }
            //始终保持result中的实体为距离最近的实体
            if (eL.distance(senderL) < result.getLocation().distance(senderL) && eL.distance(senderL) != 0) {
                result = e;
            }

        }
        player.sendMessage("=========");
        //返回最终结果
        return result;

    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.THORNS) {
        }
    }

    @EventHandler
    public void rightClickSkills(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {

        }

    }

}
