package me.tuanzi.rpgzero.draw;

public enum Rarity {

    SINGULAR("§w", "独特"),
    EXQUISITE("§7", "精致"),
    MYTHIC("§b", "神秘"),
    MAJESTIC("§d", "威严"),
    SUPREME("§6", "至高");


    private final String color;
    private final String displayName;

    Rarity(String color, String displayName) {
        this.color = color;
        this.displayName = displayName;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }
}
