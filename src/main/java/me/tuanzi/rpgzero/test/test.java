package me.tuanzi.rpgzero.test;

import java.util.Random;

public class test {

    private static int aVoid2Counter = 0;

    public static void main(String[] args) {
        double damage = 10.0;
        double def = 15.0;
        System.out.println("伤害:" + damage);
        System.out.println("防御力:" + def);
        System.out.println("计算后伤害:" + damage / (1 + 1.5 * Math.log(1 + 0.01 * def)));

        int goldenCount = 82;
        double goldenRank = 0.6;
        if (goldenCount >= 60) {
            goldenRank += (goldenCount - 60) * 0.5;
            if (goldenCount >= 70) {
                goldenRank += (goldenCount - 70) * 4;
                if (goldenCount >= 77 && goldenCount <= 82) {
                    goldenRank += (goldenCount - 77) * 7;
                }
            }
        }
        System.out.println(goldenRank);


        System.out.println(aVoid(0, 10));


    }

    public static int aVoid(int count, int max) {
        int a = 0;
        if (count >= max || aVoid2Counter >= max) {
            return a;
        }

        boolean aa = aVoid2();
        if (aa) {
            a++;
            count++;
            aVoid2Counter++;
        }

        // 模拟无法更改的 for 循环
        for (int i = 0; i < 49; i++) {
            a += aVoid(count, max);
        }

        return a;
    }

    public static boolean aVoid2() {
        return new Random().nextBoolean();
    }


}
