package com.mikkelcat.potion_overhaul.datagen;

import com.mikkelcat.potion_overhaul.PotionOverhaul;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider
{


    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider)
    {
        this.builder(PotionOverhaul.BREWING_FUEL_MAP)
                .add(Items.COAL.builtInRegistryHolder(), 2, false);
        this.builder(PotionOverhaul.BREWING_FUEL_MAP)
                .add(Items.DIAMOND.builtInRegistryHolder(), 10, false);
        this.builder(NeoForgeDataMaps.FURNACE_FUELS);
                //.add(modItems.YourItem_Here.getId(), new FurnaceFuel(1200), false);
    }
}
