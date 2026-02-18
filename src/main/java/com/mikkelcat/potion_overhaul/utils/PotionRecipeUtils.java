package com.mikkelcat.potion_overhaul.utils;

import com.mikkelcat.potion_overhaul.PotionOverhaul;
import com.mikkelcat.potion_overhaul.PotionOverhaulRegistries;
import com.mikkelcat.potion_overhaul.mixins.PotionBrewingBuilderMixin;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.ReloadableRegistryManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class PotionRecipeUtils {
    public static void insertDatapackRecipes(RegistryAccess registryAccess, PotionBrewingBuilderMixin builder) {
        Level level = Spaghetti.tryGetLevel();
        Collection<PotionRecipe> recipes;
        if (level != null) {
            ReloadableRegistryManager<PotionRecipe> manager = RegistryManagerHelper.getPotionRecipesManager(level);
            recipes = manager.getByName().values();
        } else {
            recipes = registryAccess.registryOrThrow(PotionOverhaulRegistries.POTION_RECIPE_KEY).stream().toList();
        }
        for (PotionRecipe value : recipes) {
            builder.getPotionMixes().add(new PotionBrewing.Mix<>(registryAccess.holderOrThrow(value.from()), value.ingredient(), registryAccess.holderOrThrow(value.to())));
        }
    }
}
