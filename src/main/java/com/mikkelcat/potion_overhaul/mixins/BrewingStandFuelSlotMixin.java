package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$FuelSlot")
public class BrewingStandFuelSlotMixin {
    @ModifyExpressionValue(
            method = "mayPlaceItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private static boolean potionOverhaul$mayPlaceItem(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
        return original || itemStack.is(Items.COAL);
    }

}
