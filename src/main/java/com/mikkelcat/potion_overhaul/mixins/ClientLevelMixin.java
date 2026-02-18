package com.mikkelcat.potion_overhaul.mixins;

import com.mikkelcat.potion_overhaul.PotionOverhaulRegistries;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.RegistryManagersGetter;
import com.mikkelcat.potion_overhaul.resources.ReloadableRegistryManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public class ClientLevelMixin implements RegistryManagersGetter {
    @Unique
    @Final
    @Mutable
    private ReloadableRegistryManager<PotionRecipe> potionOverhaul$potionRecipesManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void potionOverhaul$init(ClientPacketListener connection, ClientLevel.ClientLevelData clientLevelData, ResourceKey<Level> dimension, Holder<DimensionType> dimensionType, int viewDistance, int serverSimulationDistance, Supplier<ProfilerFiller> profiler, LevelRenderer levelRenderer, boolean isDebug, long biomeZoomSeed, CallbackInfo ci) {
        this.potionOverhaul$potionRecipesManager = new ReloadableRegistryManager<>(connection.registryAccess(), PotionOverhaulRegistries.POTION_RECIPE_KEY, PotionRecipe.CODEC.codec());
    }

    @Override
    public ReloadableRegistryManager<PotionRecipe> getPotionRecipesManager() {
        return potionOverhaul$potionRecipesManager;
    }
}