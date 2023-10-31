package me.tuanzi.rpgzero.attributes;

public enum ItemAttributes {
    CRIT_RATE(0.028, 0.056, 5, "ALL", "暴击率"), //0.7%
    CRIT_DAMAGE(0.056, 0.112, 5, "ALL", "暴击伤害"), // 1.4%
    ATTACK_DAMAGE(1.5, 3.5, 5, "ALL", "攻击力"), // 0.5
    ATTACK_SPEED(1.7, 3.3, 5, "TOOL", "攻击速度"), //0.4
    DEFENSE(4.2, 7.0, 5, "ARMOR", "防御力"), //0.7
    PHYSICAL_RESISTANCE(0.021, 0.041, 5, "ARMOR", "物理抗性"), //0.5%
    MAGIC_RESISTANCE(0.016, 0.024, 5, "ARMOR", "魔法抗性"),//0.2%
    ;

    private final double min;
    private final double max;
    private final int weight;
    private final String type;
    private final String displayName;

    ItemAttributes(double min, double max, int weight, String type, String displayName) {
        this.min = min;
        this.max = max;
        this.weight = weight;
        this.type = type;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }
}
