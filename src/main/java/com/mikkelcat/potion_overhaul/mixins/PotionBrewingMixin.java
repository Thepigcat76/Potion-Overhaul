package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.ReloadableRegistryManager;
import com.mikkelcat.potion_overhaul.utils.PotionRecipeUtils;
import com.mikkelcat.potion_overhaul.utils.RegistryManagerHelper;
import com.mikkelcat.potion_overhaul.utils.Spaghetti;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @Inject(method = "bootstrap(Lnet/minecraft/world/flag/FeatureFlagSet;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/alchemy/PotionBrewing;", at = @At(value = "INVOKE", target = "Lnet/neoforged/bus/api/IEventBus;post(Lnet/neoforged/bus/api/Event;)Lnet/neoforged/bus/api/Event;"))
    private static void potionOverhaul$bootstrap(FeatureFlagSet enabledFeatures, RegistryAccess registryAccess, CallbackInfoReturnable<PotionBrewing> cir, @Local PotionBrewing.Builder builder) {
        PotionRecipeUtils.insertDatapackRecipes(registryAccess, (PotionBrewingBuilderMixin) builder);
        List<PotionBrewing.Mix<Potion>> potionMixes = new ArrayList<>();
        for (PotionBrewing.Mix<Potion> potionMix : ((PotionBrewingBuilderMixin) builder).getPotionMixes()) {
            if (potionMix.from() == Potions.AWKWARD) {
                potionMixes.add(new PotionBrewing.Mix<>(Potions.MUNDANE, potionMix.ingredient(), potionMix.to()));
            }
        }
        ((PotionBrewingBuilderMixin) builder).getPotionMixes().addAll(potionMixes);
    }

}
