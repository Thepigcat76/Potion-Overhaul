package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Definitions;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {
    @ModifyExpressionValue(
            method = "canPlaceItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean potionOverhaul$canPlaceItem(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
        return original || itemStack.is(Items.COAL);
    }

    @ModifyExpressionValue(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean potionOverhaul$serverTickItemCheck(boolean original, @Local(ordinal = 0) ItemStack itemStack) {
        return original || itemStack.is(Items.COAL);
    }

    @ModifyConstant(method = "serverTick", constant = @Constant(intValue = 20))
    private static int potionOverhaul$serverTickFuelAmount(int constant) {
        return 10;
    }

    @Definitions({
        @Definition(id = "blockEntity", local = @Local(type = BrewingStandBlockEntity.class)),
            @Definition(id = "fuel", field = "Lnet/minecraft/world/level/block/entity/BrewingStandBlockEntity;fuel:I")
    })
    @Expression("blockEntity.fuel <= 0")
    @ModifyExpressionValue(method = "serverTick", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean potionOverhaul$serverTickFuelAmountComparison(boolean original) {
        return original;
    }

}
