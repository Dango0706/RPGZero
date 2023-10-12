package me.tuanzi.rpgzero.quality;

public enum Quality {
    //盔甲
    RESILIENT("§9", "坚韧", "ARMOR", "+1盔甲韧性 +5防御力", 5),
    //武器
    POWERFUL("§9", "强大", "TOOL", "§9+10%攻击伤害 +10%攻击速度", 5),
    RELIABLE("§9", "可靠", "TOOL", "§9+15%攻击伤害", 5),
    PRECISE("§9", "准确", "TOOL", "§9+5%暴击率", 5),
    AGILE("§9", "敏捷", "TOOL", "§9+10%攻击速度", 5),
    RADIANT("§9", "闪耀", "TOOL", "§9+10%暴击伤害 +5%暴击率", 5),
    EXQUISITE("§9", "绝妙", "TOOL", "§9+10%暴击伤害 +10%暴击率 +10%攻击速度 +10%攻击伤害", 2),
    ADAPTABLE("§e", "适应性", "TOOL", "§9+10%攻击速度 §c-5%攻击伤害", 3),
    FRAGILE("§c", "脆弱", "TOOL", "-15%攻击伤害", 8),
    CORRODED("§c", "腐蚀", "TOOL", "-5%暴击率 -5%暴击伤害", 8),
    HEAVY("§c", "沉重", "TOOL", "-10%攻击速度", 8),
    RUSTY("§c", "生锈", "TOOL", "-15%攻击伤害 -5%暴击伤害", 8),
    WORTHLESS("§4", "无价值", "TOOL", "-10%攻击速度 -10%攻击伤害 -10%暴击率 -10%暴击伤害", 2),

    ;


    private final String color;
    private final String displayName;
    private final String type;
    private final String synopsis;
    private final int weight;

    Quality(String color, String displayName, String type, String synopsis, int weight) {
        this.color = color;
        this.displayName = displayName;
        this.type = type;
        this.synopsis = synopsis;
        this.weight = weight;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getType() {
        return type;
    }
}
