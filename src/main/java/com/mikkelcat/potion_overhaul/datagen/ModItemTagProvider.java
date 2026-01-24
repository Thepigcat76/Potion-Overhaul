package com.mikkelcat.potion_overhaul.datagen;


import com.mikkelcat.potion_overhaul.PotionOverhaul;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, PotionOverhaul.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        //tag(ModTags.Items.TRANSFORMABLE_ITEMS)


        tag(ItemTags.PICKAXES);

        tag(ItemTags.SWORDS);

        tag(ItemTags.AXES);

        tag(ItemTags.SHOVELS);

        tag(ItemTags.HOES);

        //To make the armourset trimmable.
        this.tag(ItemTags.TRIMMABLE_ARMOR);


    }
}
