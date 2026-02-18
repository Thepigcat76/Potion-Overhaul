package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mikkelcat.potion_overhaul.PotionOverhaulRegistries;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.RegistryManagersGetter;
import com.mikkelcat.potion_overhaul.resources.ReloadableRegistryManager;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin implements RegistryManagersGetter {
    @Unique
    @Final
    @Mutable
    private ReloadableRegistryManager<PotionRecipe> potionOverhaul$potionRecipesManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void potionOverhaul$init(RegistryAccess.Frozen registryAccess, FeatureFlagSet enabledFeatures, Commands.CommandSelection commandSelection, int functionCompilationLevel, CallbackInfo ci) {
        this.potionOverhaul$potionRecipesManager = new ReloadableRegistryManager<>(registryAccess, PotionOverhaulRegistries.POTION_RECIPE_KEY, PotionRecipe.CODEC.codec());
    }

    @ModifyReturnValue(method = "listeners", at = @At("RETURN"))
    private List<PreparableReloadListener> potionOverhaul$listeners(List<PreparableReloadListener> original) {
        List<PreparableReloadListener> copy = new ArrayList<>(original);
        copy.add(this.potionOverhaul$potionRecipesManager);
        return List.copyOf(copy);
    }

    @Override
    public ReloadableRegistryManager<PotionRecipe> getPotionRecipesManager() {
        return this.potionOverhaul$potionRecipesManager;
    }
}
