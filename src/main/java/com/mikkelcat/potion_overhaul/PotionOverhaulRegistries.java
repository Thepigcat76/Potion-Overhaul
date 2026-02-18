package com.mikkelcat.potion_overhaul;

import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class PotionOverhaulRegistries {
    public static final ResourceKey<Registry<PotionRecipe>> POTION_RECIPE_KEY = ResourceKey.createRegistryKey(PotionOverhaul.rl("potion_recipes"));
}
