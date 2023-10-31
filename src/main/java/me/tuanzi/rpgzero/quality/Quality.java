package me.tuanzi.rpgzero.quality;

public enum Quality {
    //盔甲
    RESILIENT("§9", "坚韧",   "ARMOR", "§9+5防御力", 5, 0, 0, 0, 0, 0, 5, 0, 0, 0,0,0,0),
    STURDINESS("§9", "坚固",   "ARMOR", "§9+15防御力 +10%物理抗性 +10%魔法抗性 +5%移动速度", 2, 0, 0, 0, 0, 0, 15, 0, 0.1, 0.1,0,0.05,0),
    ATTENTION_TO_DETAIL("§9", "注重细节",   "ARMOR", "§9+10防御力 +5%暴击率", 5, 0, 0, 0, 0.05, 0, 10, 0, 0, 0,0,0,0),
    EFFICIENT_DEFENSE("§9", "高效防御",   "ARMOR", "§9+10%物理抗性", 5, 0, 0, 0, 0, 0, 0, 0, 0.1, 0,0,0,0),
    DILAPIDATED("§c", "破旧",   "ARMOR", "§c-5防御力", 8, 0, 0, 0, 0, 0, -5, 0, 0, 0,0,0,0),
    BRITTLENESS("§c", "脆弱",   "ARMOR", "§c-10%物理抗性", 8, 0, 0, 0, 0, 0, 0, 0, -0.1, 0,0,0,0),
    INEFFECTIVE("§4", "无法防护",   "ARMOR", "§4-10%物理抗性 -10%魔法抗性 -15防御力 -5%移动速度", 2, 0, 0, 0, 0, 0, -15, 0, -0.1, -0.1,0,-0.05,0),
    VERSATILITY("§e", "灵活性",   "ARMOR", "§c-5防御力 §9+10%移动速度", 3, 0, 0, 0, 0, 0, -5, 0, 0, 0,0,0.1,0),
    WEIGHT_BALANCE("§e", "重量平衡",   "ARMOR", "§9+15防御力 §c-10%移动速度", 3, 0, 0, 0, 0, 0, 15, 0, 0, 0,0,-0.1,0),
    //武器
    POWERFUL("§9", "强大", "TOOL", "§9+10%攻击伤害 +10%攻击速度", 5, 0, 0.1, 0.1, 0, 0, 0, 0, 0, 0,0,0,0),
    RELIABLE("§9", "可靠", "TOOL", "§9+15%攻击伤害", 5, 0, 0, 0.15, 0, 0, 0, 0, 0, 0,0,0,0),
    PRECISE("§9", "准确", "TOOL", "§9+5%暴击率", 5, 0, 0, 0, 0.05, 0, 0, 0, 0, 0,0,0,0),
    AGILE("§9", "敏捷", "TOOL", "§9+10%攻击速度", 5, 0, 0.1, 0, 0, 0, 0, 0, 0, 0,0,0,0),
    RADIANT("§9", "闪耀", "TOOL", "§9+10%暴击伤害 +5%暴击率", 5, 0, 0, 0, 0.05, 0.1, 0, 0, 0, 0,0,0,0),
    EXQUISITE("§9", "绝妙", "TOOL", "§9+10%暴击伤害 +10%暴击率 +10%攻击速度 +10%攻击伤害", 2, 0, 0.1, 0.1, 0.1, 0.1, 0, 0, 0, 0,0,0,0),
    ADAPTABLE("§e", "适应性", "TOOL", "§9+10%攻击速度 §c-5%攻击伤害", 4, 0, 0.1, -0.05, 0, 0, 0, 0, 0, 0,0,0,0),
    FRAGILE("§c", "脆弱", "TOOL", "-15%攻击伤害", 8, 0, 0, -0.15, 0, 0, 0, 0, 0, 0,0,0,0),
    CORRODED("§c", "腐蚀", "TOOL", "-5%暴击率 -5%暴击伤害", 8, 0, 0, 0, -0.05, -0.05, 0, 0, 0, 0,0,0,0),
    HEAVY("§c", "沉重", "TOOL", "-10%攻击速度", 8, 0, -0.1, 0, 0, 0, 0, 0, 0, 0,0,0,0),
    RUSTY("§c", "生锈", "TOOL", "-15%攻击伤害 -5%暴击伤害", 8, 0, 0, -0.15, 0, -0.05, 0, 0, 0, 0,0,0,0),
    WORTHLESS("§4", "无价值", "TOOL", "-10%攻击速度 -10%攻击伤害 -10%暴击率 -10%暴击伤害", 3, 0, -0.1, -0.1, -0.1, -0.1, 0, 0, 0, 0,0,0,0),

    ;


    private final String color;
    private final String displayName;
    private final String type;
    private final String synopsis;
    private final int weight;

    private final double attackDamage;
    private final double attackSpeed;
    private final double increase;
    private final double critRate;
    private final double critDamage;
    private final double defense;
    private final double armorToughness;
    private final double physicalResistance;
    private final double magicResistance;
    private final double health;
    private final double speed;
    private final double luck;


    Quality(String color, String displayName, String type, String synopsis, int weight, double attackDamage, double attackSpeed, double increase, double critRate, double critDamage, double defense, double armorToughness, double physicalResistance, double magicResistance, double health, double speed, double luck) {
        this.color = color;
        this.displayName = displayName;
        this.type = type;
        this.synopsis = synopsis;
        this.weight = weight;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.increase = increase;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.defense = defense;
        this.armorToughness = armorToughness;
        this.physicalResistance = physicalResistance;
        this.magicResistance = magicResistance;
        this.health = health;
        this.speed = speed;
        this.luck = luck;
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

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getIncrease() {
        return increase;
    }

    public double getCritRate() {
        return critRate;
    }

    public double getCritDamage() {
        return critDamage;
    }

    public double getDefense() {
        return defense;
    }

    public double getArmorToughness() {
        return armorToughness;
    }

    public double getPhysicalResistance() {
        return physicalResistance;
    }

    public double getMagicResistance() {
        return magicResistance;
    }

    public double getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }

    public double getLuck() {
        return luck;
    }
}
