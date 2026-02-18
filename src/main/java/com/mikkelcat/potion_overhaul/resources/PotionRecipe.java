package com.mikkelcat.potion_overhaul.resources;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;

public record PotionRecipe(ResourceKey<Potion> from, Ingredient ingredient, ResourceKey<Potion> to) {
    public static final MapCodec<PotionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceKey.codec(Registries.POTION).fieldOf("from").forGetter(PotionRecipe::from),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(PotionRecipe::ingredient),
            ResourceKey.codec(Registries.POTION).fieldOf("to").forGetter(PotionRecipe::to)
    ).apply(inst, PotionRecipe::new));
}
