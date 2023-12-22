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
        //有序合成
        //灵魂之晶
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "SOUL_GEM_1"), SOUL_GEM);
        shapedRecipe = shapedRecipe.shape("aba","dcd","aba");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.EMERALD_BLOCK)
                .setIngredient('b',Material.DIAMOND_BLOCK)
                .setIngredient('c',new RecipeChoice.ExactChoice(SPECTRAL_FRAGMENT))
                .setIngredient('d',Material.RAW_GOLD);
        Bukkit.addRecipe(shapedRecipe);
        shapedRecipe = new ShapedRecipe(new NamespacedKey(javaPlugin, "SOUL_GEM_2"), SOUL_GEM);
        shapedRecipe = shapedRecipe.shape("aba","dcd","aba");
        shapedRecipe = shapedRecipe.setIngredient('a',Material.EMERALD_BLOCK)
                .setIngredient('b',new RecipeChoice.ExactChoice(SPIRITUAL_STONE))
                .setIngredient('c',new RecipeChoice.ExactChoice(SPECTRAL_FRAGMENT))
                .setIngredient('d',Material.RAW_GOLD);
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
        //无序合成
        //gui菜单
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new NamespacedKey(javaPlugin, "GUI_MENU"), GUI_MENU);
        shapelessRecipe = shapelessRecipe.addIngredient(1,Material.CLOCK);
        Bukkit.addRecipe(shapelessRecipe);
        //幽光碎片1
        shapelessRecipe = new ShapelessRecipe(new NamespacedKey(javaPlugin,"SPECTRAL_FRAGMENT_1"),SPECTRAL_FRAGMENT);
        shapelessRecipe = shapelessRecipe.addIngredient(Material.RAW_IRON)
                .addIngredient(Material.HONEYCOMB)
                .addIngredient(Material.SHULKER_SHELL)
                .addIngredient(Material.RAW_COPPER)
                .addIngredient(Material.GLOW_INK_SAC)
                .addIngredient(Material.BLAZE_ROD)
                .addIngredient(Material.RAW_GOLD)
                .addIngredient(Material.PUFFERFISH)
                .addIngredient(Material.PHANTOM_MEMBRANE);
        Bukkit.addRecipe(shapelessRecipe);
        //幽光碎片2
        shapelessRecipe = new ShapelessRecipe(new NamespacedKey(javaPlugin,"SPECTRAL_FRAGMENT_2"),SPECTRAL_FRAGMENT);
        shapelessRecipe = shapelessRecipe.addIngredient(Material.RAW_IRON)
                .addIngredient(Material.RABBIT_HIDE)
                .addIngredient(Material.SHULKER_SHELL)
                .addIngredient(Material.RAW_COPPER)
                .addIngredient(Material.GLOW_INK_SAC)
                .addIngredient(Material.BLAZE_ROD)
                .addIngredient(Material.RAW_GOLD)
                .addIngredient(Material.PUFFERFISH)
                .addIngredient(Material.PHANTOM_MEMBRANE);
        Bukkit.addRecipe(shapelessRecipe);
        //灵石
        shapelessRecipe = new ShapelessRecipe(new NamespacedKey(javaPlugin,"SPIRITUAL_STONE"),SPIRITUAL_STONE);
        shapelessRecipe = shapelessRecipe.addIngredient(Material.DIAMOND)
                .addIngredient(Material.DIAMOND)
                .addIngredient(Material.DIAMOND)
                .addIngredient(Material.COPPER_BLOCK)
                .addIngredient(Material.COPPER_BLOCK)
                .addIngredient(Material.QUARTZ_BLOCK)
                .addIngredient(Material.QUARTZ_BLOCK);
        Bukkit.addRecipe(shapelessRecipe);




    }



}
