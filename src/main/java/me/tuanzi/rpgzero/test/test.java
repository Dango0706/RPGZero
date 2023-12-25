package me.tuanzi.rpgzero.test;

public class test {

    private static int aVoid2Counter = 0;

    public static void main(String[] args) {
        double damage = 10.0;
        double def = 15.0;
        System.out.println("伤害:" + damage);
        System.out.println("防御力:" + def);
        System.out.println("计算后伤害:" + damage / (1 + 1.5 * Math.log(1 + 0.01 * def)));
        


    }



}
