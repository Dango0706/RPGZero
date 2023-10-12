package me.tuanzi.rpgzero.draw;

public enum ItemType {
    //todo:国际化
    SWORD("剑"),
    GREAT_SWORD("大剑"),
    HELMET("头盔"),
    CHESTPLATE("胸甲"),
    LEGGINGS("护腿"),
    BOOTS("靴子");

    private String  displayName;
    ItemType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
