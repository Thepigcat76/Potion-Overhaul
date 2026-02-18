package com.mikkelcat.potion_overhaul.networking;

import com.mikkelcat.potion_overhaul.PotionOverhaul;
import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.utils.RegistryManagerHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;

public record UpdatePotionRecipesPayload(HashMap<ResourceLocation, PotionRecipe> potionRecipes) implements CustomPacketPayload {
    public static final StreamCodec<? super RegistryFriendlyByteBuf, UpdatePotionRecipesPayload> STREAM_CODEC = ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.fromCodec(PotionRecipe.CODEC.codec()))
            .map(UpdatePotionRecipesPayload::new, UpdatePotionRecipesPayload::potionRecipes);
    public static final Type<UpdatePotionRecipesPayload> TYPE = new Type<>(PotionOverhaul.rl("update_potion_recipes"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            RegistryManagerHelper.getPotionRecipesManager(context.player().level()).replaceContents(this.potionRecipes);
        }).exceptionally(err -> {
            PotionOverhaul.LOGGER.error("Failed to handle UpdatePotionRecipesPayload", err);
            return null;
        });
    }
}