package me.tuanzi.rpgzero.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import static me.tuanzi.rpgzero.RPGZero.javaPlugin;
import static me.tuanzi.rpgzero.items.JavaItems.*;

public class Recipe {

    public Recipe() {
        //灵魂之晶
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "SOUL_GEM"), SOUL_GEM);
        shapedRecipe = shapedRecipe.shape("aba","dcd","aba");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.EMERALD_BLOCK)
                .setIngredient('b',Material.DIAMOND_BLOCK)
                .setIngredient('c',new RecipeChoice.ExactChoice(SPECTRAL_FRAGMENT))
                .setIngredient('d',Material.RAW_GOLD);
        Bukkit.addRecipe(shapedRecipe);
        //幽光碎片
        shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "SPECTRAL_FRAGMENT"), SPECTRAL_FRAGMENT);
        shapedRecipe = shapedRecipe.shape("abc","def","ghi");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.RAW_IRON)
                .setIngredient('b',Material.HONEYCOMB)
                .setIngredient('c',Material.SHULKER_SHELL)
                .setIngredient('d',Material.RAW_COPPER)
                .setIngredient('e',Material.GLOW_INK_SAC)
                .setIngredient('f',Material.BLAZE_ROD)
                .setIngredient('g',Material.RAW_GOLD)
                .setIngredient('h',Material.PUFFERFISH)
                .setIngredient('i',Material.PHANTOM_MEMBRANE);
        Bukkit.addRecipe(shapedRecipe);
        //永恒之眼
        shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "ETERNIAS_GAZE"), ETERNIAS_GAZE);
        shapedRecipe = shapedRecipe.shape("aaa","bcb","ddd");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.DIAMOND)
                .setIngredient('b',Material.GOLD_INGOT)
                .setIngredient('c',new RecipeChoice.ExactChoice(SOUL_OF_SMELTING))
                .setIngredient('d',Material.IRON_INGOT);
        Bukkit.addRecipe(shapedRecipe);
        //焕耀之光
        shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "REFRESHING_GLORY"), REFRESHING_GLORY);
        shapedRecipe = shapedRecipe.shape("ccc","cac","ccc");
        shapedRecipe = shapedRecipe.setIngredient('a',new RecipeChoice.ExactChoice(ESSENCE_OF_RESTORATION))
                .setIngredient('c',new RecipeChoice.ExactChoice(SOUL_OF_SMELTING));
        Bukkit.addRecipe(shapedRecipe);
        //回春余烬
        shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "RESTORATIVE_EMBER"), RESTORATIVE_EMBER);
        shapedRecipe = shapedRecipe.shape("aaa","ccc","bbb");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.DIAMOND)
                .setIngredient('b',Material.NETHERITE_INGOT)
                .setIngredient('c',new RecipeChoice.ExactChoice(ESSENCE_OF_RESTORATION));
        Bukkit.addRecipe(shapedRecipe);
        //gui菜单
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new NamespacedKey(javaPlugin, "GUI_MENU"), GUI_MENU);
        shapelessRecipe = shapelessRecipe.addIngredient(1,Material.CLOCK);
        Bukkit.addRecipe(shapelessRecipe);
    }



}
