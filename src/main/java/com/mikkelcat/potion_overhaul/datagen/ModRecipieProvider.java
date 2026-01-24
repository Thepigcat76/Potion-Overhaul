package com.mikkelcat.potion_overhaul.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipieProvider extends RecipeProvider implements IConditionBuilder
{
    public ModRecipieProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput)
    {/*
        //Everything smeltable should be in this list.
        List<ItemLike> DURANIUM_SMELTABLES = List.of(ModItems.DURANIUMCLUMP, ModItems.ROUGHDURANIUMINGOT,
                ModItems.TEMPEREDDURANIUMINGOT,ModBlocks.DURANIUM_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DURANIUM_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.DURANIUMINGOT.get())
                .unlockedBy("has_durium", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DURANIUMINGOT.get(), 9)
                .requires(ModBlocks.DURANIUM_BLOCK)
                .unlockedBy("has_duranium_block", has(ModBlocks.DURANIUM_BLOCK))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(LembasBreadMod.MODID, "duraniumblock_to_ingots"));
        //If you have the same output/result you would need to give/allocate a modid. Example:
        // add .save(recipeOutput, "mrt:duranium_from_forge_block"); to the recipie method.


        //SMELTING DURANIUM CLUMP -> ROUGHDURANIUMINGOT
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.DURANIUMCLUMP.get()),
                RecipeCategory.MISC,
                ModItems.ROUGHDURANIUMINGOT.get(),
                5f,
                1000
        ).unlockedBy("has_duraniumclump", has(ModItems.DURANIUMCLUMP.get()))
                        .save(recipeOutput);

        //SMELTING ROUGHDURANIUMINGOT -> "TEMPEREDDURANIUM". (Need to change name).
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.ROUGHDURANIUMINGOT.get()),
                        RecipeCategory.MISC,
                        ModItems.TEMPEREDDURANIUMINGOT.get(),
                        5f,
                        1000
                ).unlockedBy("roughduraniumingot", has(ModItems.ROUGHDURANIUMINGOT.get()))
                .save(recipeOutput);

        //SMELTING TEMPEREDDURANIUM -> DURANIUM INGOT
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.TEMPEREDDURANIUMINGOT.get()),
                        RecipeCategory.MISC,
                        ModItems.DURANIUMINGOT.get(),
                        5f,
                        1000
                ).unlockedBy("duranium_ingot", has(ModItems.DURANIUMINGOT.get()))
                .save(recipeOutput);



        //SMELTING DURANIUM CLUMP -> ROUGHDURANIUMINGOT
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.DURANIUMCLUMP.get()),
                        RecipeCategory.MISC,
                        ModItems.ROUGHDURANIUMINGOT.get(),
                        5f,
                        500
                ).unlockedBy("has_duraniumclump", has(ModItems.DURANIUMCLUMP.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(LembasBreadMod.MODID, "clump_to_rough_duranium"));

        //SMELTING ROUGHDURANIUMINGOT -> "TEMPEREDDURANIUM". (Need to change name).
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.ROUGHDURANIUMINGOT.get()),
                        RecipeCategory.MISC,
                        ModItems.TEMPEREDDURANIUMINGOT.get(),
                        5f,
                        500
                ).unlockedBy("roughduraniumingot", has(ModItems.ROUGHDURANIUMINGOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(LembasBreadMod.MODID, "rough_to_tempered_duranium"));

        //SMELTING TEMPEREDDURANIUM -> DURANIUM INGOT
        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.TEMPEREDDURANIUMINGOT.get()),
                        RecipeCategory.MISC,
                        ModItems.DURANIUMINGOT.get(),
                        5f,
                        500
                ).unlockedBy("duranium_ingot", has(ModItems.DURANIUMINGOT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(LembasBreadMod.MODID, "tempered_to_ingot"));


        //Lembasbread Crafting Recipie
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.LEMBASBREAD.get())
                .pattern("BBB")
                .pattern("XSX")
                .pattern("BBB")
                .define('B', Items.WHEAT)
                .define('X', Items.HONEY_BOTTLE)
                .define('S', Items.BREAD)
                .unlockedBy("has_wheat", has(Items.WHEAT)).save(recipeOutput);

        //Duranium Tools Crafting Recipie
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DURANIUMPICKAXE.get())
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', ModItems.DURANIUMINGOT)
                .define('S', Items.STICK)
                .unlockedBy("duranium_pickaxe", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DURANIUMSWORD.get())
                .pattern(" D ")
                .pattern(" D ")
                .pattern(" S ")
                .define('D', ModItems.DURANIUMINGOT)
                .define('S', Items.STICK)
                .unlockedBy("duranium_sword", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DURANIUMAXE.get())
                .pattern("DD ")
                .pattern("DS ")
                .pattern(" S ")
                .define('D', ModItems.DURANIUMINGOT)
                .define('S', Items.STICK)
                .unlockedBy("duranium_axe", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DURANIUMSHOVEL.get())
                .pattern(" D ")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', ModItems.DURANIUMINGOT)
                .define('S', Items.STICK)
                .unlockedBy("duranium_shovel", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DURANIUMHOE.get())
                .pattern("DD ")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', ModItems.DURANIUMINGOT)
                .define('S', Items.STICK)
                .unlockedBy("duranium_hoe", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        //Armour crafting
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DURANIUM_HELMET.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("   ")
                .define('D', ModItems.DURANIUMINGOT)
                .unlockedBy("duranium_helmet", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DURANIUM_CHESTPLATE.get())
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.DURANIUMINGOT)
                .unlockedBy("duranium_chestplate", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DURANIUM_LEGGINGS.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.DURANIUMINGOT)
                .unlockedBy("duranium_leggings", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DURANIUM_BOOTS.get())
                .pattern("   ")
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.DURANIUMINGOT)
                .unlockedBy("duranium_boots", has(ModItems.DURANIUMINGOT)).save(recipeOutput);

        //Potion Related Crafting Recipies
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ModItems.POTIONPRIMER.get())
                .pattern("FGF")
                .pattern("GRG")
                .pattern("FGF")
                .define('F', ItemTags.FLOWERS) //ItemTags.SMALL_FLOWERS
                .define('G', Items.SHORT_GRASS)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BREWING_STAND)
                .pattern("IGI")
                .pattern("CGC")
                .pattern("SSS")
                .define('I', Items.IRON_BARS)
                .define('G', Items.GOLD_INGOT)
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.SMOOTH_STONE_SLAB)
                .unlockedBy("has_gold", has(Items.GOLD_INGOT)).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ModItems.ALCHEMISTCOAL.get())
                .requires(Items.COAL)
                .requires(Items.REDSTONE)
                .unlockedBy("has_coal", has (Items.COAL))
                .save(recipeOutput, "alchemistcoal_from_coal");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ModItems.ALCHEMISTCOAL.get())
                .requires(Items.CHARCOAL)
                .requires(Items.REDSTONE)
                .unlockedBy("has_coal", has (Items.COAL))
                .save(recipeOutput, "alchemistcoal_from_charcoal");


    */
    }

}
