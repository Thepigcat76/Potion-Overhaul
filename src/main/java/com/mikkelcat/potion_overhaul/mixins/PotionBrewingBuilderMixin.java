package com.mikkelcat.potion_overhaul.mixins;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayList;
import java.util.List;

@Mixin(PotionBrewing.Builder.class)
public interface PotionBrewingBuilderMixin {
    @Accessor("containers")
    List<Ingredient> getContainers();
    @Accessor("potionMixes")
    List<PotionBrewing.Mix<Potion>> getPotionMixes();
    @Accessor("containerMixes")
    List<PotionBrewing.Mix<Item>> getContainerMixes();
    @Accessor("recipes")
    List<net.neoforged.neoforge.common.brewing.IBrewingRecipe> getRecipes();
}
