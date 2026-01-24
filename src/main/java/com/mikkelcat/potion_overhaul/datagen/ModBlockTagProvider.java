package com.mikkelcat.potion_overhaul.datagen;

import com.mikkelcat.potion_overhaul.PotionOverhaul;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider
{
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PotionOverhaul.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        tag(BlockTags.MINEABLE_WITH_PICKAXE);
                //.add etc etc remember not to break the chain with a ";".

        /*
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DURANIUM_ORE.get()); */


        tag(BlockTags.NEEDS_DIAMOND_TOOL);

                        //.add(ModBlocks.DURANIUM_BLOCK.get());
    /*
        //Every block X can mine (In this case Iron). Duranium can also mine.
        tag(ModTags.Blocks.NEEDS_DURANIUM_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
                //.addTag(BlockTags.NEEDS_DIAMOND_TOOL);


        tag(ModTags.Blocks.INCORRECT_FOR_DURANIUM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
    */
    }

}
