package me.tuanzi.rpgzero;

import java.util.*;

public class test {

    enum Prize {
        PRIZE1(1),
        PRIZE2(2),
        PRIZE3(2),
        PRIZE4(3);

        private final int weight;

        Prize(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    public static void main(String[] args) {

        System.out.println(5%5==0);


        List<Prize> prizes = Arrays.asList(Prize.values());

        Random random = new Random();
        Set<Prize> result = new HashSet<>();

        while (result.size() < 3) {
            int totalWeight = prizes.stream().mapToInt(Prize::getWeight).sum();
            int randomWeight = random.nextInt(totalWeight) + 1;
            int currentWeight = 0;
            Prize selectedPrize = null;

            for (Prize prize : prizes) {
                currentWeight += prize.getWeight();
                if (currentWeight >= randomWeight) {
                    selectedPrize = prize;
                    break;
                }
            }

            if (selectedPrize != null) {
                result.add(selectedPrize);
            }
        }

        for (Prize prize : result) {
            System.out.println("抽中奖品：" + prize.name());
        }
    }

}
