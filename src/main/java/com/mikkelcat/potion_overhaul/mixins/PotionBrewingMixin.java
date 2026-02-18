package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @Inject(method = "bootstrap(Lnet/minecraft/world/flag/FeatureFlagSet;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/alchemy/PotionBrewing;", at = @At("TAIL"))
    private static void potionOverhaul$bootstrap(FeatureFlagSet enabledFeatures, RegistryAccess registryAccess, CallbackInfoReturnable<PotionBrewing> cir, @Local PotionBrewing.Builder builder) {
        potion_Overhaul$createAlternativeBrewingRecipe((PotionBrewingBuilderMixin) builder);
    }

    @Unique
    private static void potion_Overhaul$createAlternativeBrewingRecipe(PotionBrewingBuilderMixin builder) {
        PotionBrewingBuilderMixin builderExt = builder;
        for (PotionBrewing.Mix<Potion> potionMix : builderExt.getPotionMixes()) {
            if (potionMix.from() == Potions.AWKWARD) {
                builderExt.getPotionMixes().add(new PotionBrewing.Mix<>(Potions.MUNDANE, potionMix.ingredient(), potionMix.to()));
            }
        }
    }
}
