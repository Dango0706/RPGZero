package me.tuanzi.rpgzero.test;

public class test {

    // 定义一个方法，计算自身受到的伤害



    // 定义一个测试方法，输出不同防御力下的伤害
    public static void tt() {
        // 遍历不同的防御力，从-100到100，每次增加10
        for (double f = -100; f <= 100; f += 10) {
            // 调用damage方法，计算自身受到的伤害
            double d = 10 / (1 + 1.5 * Math.log(1 + 0.01 * f));
            // 输出结果，保留两位小数
//            System.out.printf("old防御力: %.2f, 伤害: %.2f%n", f, d);
//            d = damage(10,f);
            System.out.printf("new防御力: %.2f, 伤害: %.2f%n", f, d);
        }
    }


    public static void main(String[] args) {
        tt();

    }


}
