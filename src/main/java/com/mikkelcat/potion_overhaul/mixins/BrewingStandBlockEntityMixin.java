package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Definitions;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mikkelcat.potion_overhaul.PotionOverhaul;
import com.mikkelcat.potion_overhaul.PotionOverhaulConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @Shadow
    int fuel;

    // TODO: When hovering the fuel things, show the maximum amount of uses
    // TODO: Make the maximum amount of uses configurable

    @ModifyExpressionValue(
            method = "canPlaceItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean potionOverhaul$canPlaceItem(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
        return original || (itemStack.getItemHolder().getData(PotionOverhaul.BREWING_FUEL_MAP) != null);
    }

    @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean potionOverhaul$serverTickItemCheck(boolean original, @Local(ordinal = 0) ItemStack itemStack) {
        return original || (itemStack.getItemHolder().getData(PotionOverhaul.BREWING_FUEL_MAP) != null);
    }

    @ModifyConstant(method = "serverTick", constant = @Constant(intValue = 20))
    private static int potionOverhaul$serverTickFuelAmount(int constant, @Local(ordinal = 0) ItemStack itemStack, @Local(ordinal = 0) BrewingStandBlockEntity blockEntity) {
        Integer data = itemStack.getItemHolder().getData(PotionOverhaul.BREWING_FUEL_MAP);
        int fuel = ((BrewingStandBlockEntityMixin) (Object) blockEntity).fuel;
        return (data != null) ? fuel + data : fuel + constant;
    }

    @Definitions({
        @Definition(id = "blockEntity", local = @Local(type = BrewingStandBlockEntity.class)),
            @Definition(id = "fuel", field = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;fuel:I")
    })
    @Expression("blockEntity.fuel <= 0")
    @ModifyExpressionValue(method = "serverTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean potionOverhaul$serverTickFuelAmountComparison(boolean original, @Local(ordinal = 0) ItemStack itemStack, @Local(ordinal = 0) BrewingStandBlockEntity blockEntity) {
        Integer data = itemStack.getItemHolder().getData(PotionOverhaul.BREWING_FUEL_MAP);
        int fuel = ((BrewingStandBlockEntityMixin) (Object) blockEntity).fuel;
        return (itemStack.is(Items.BLAZE_POWDER) && (fuel + 20 <= PotionOverhaulConfig.brewingStandFuelUses) || (data != null ? fuel + data <= PotionOverhaulConfig.brewingStandFuelUses : original));
    }

}
