package com.mikkelcat.potion_overhaul.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mikkelcat.potion_overhaul.PotionOverhaulConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.BrewingStandMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandScreen.class)
public abstract class BrewingStandScreenMixin extends AbstractContainerScreen<BrewingStandMenu> {
    private BrewingStandScreenMixin() {
        super(null, null, null);
    }

    @ModifyExpressionValue(method = "renderBg", at = @At(value = "CONSTANT", args = "intValue=20"))
    private int potionOverhaul$renderBgReplaceFuelUses(int constant) {
        return PotionOverhaulConfig.brewingStandFuelUses;
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void potionOverhaul$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        int x = (this.width - this.imageWidth) / 2 + 60;
        int y = (this.height - this.imageHeight) / 2 + 44;
        if (mouseX > x && mouseX < x + 18 && mouseY > y && mouseY < y + 4) {
            renderTooltips(guiGraphics, mouseX, mouseY);
        }
    }

    private static void renderTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal("Fuel Uses: " + PotionOverhaulConfig.brewingStandFuelUses), mouseX, mouseY);
    }

}
