package com.mikkelcat.potion_overhaul.utils;

import com.mikkelcat.potion_overhaul.resources.PotionRecipe;
import com.mikkelcat.potion_overhaul.resources.RegistryManagersGetter;
import com.mikkelcat.potion_overhaul.resources.ReloadableRegistryManager;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.level.Level;

public final class RegistryManagerHelper {
    public static ReloadableRegistryManager<PotionRecipe> getPotionRecipesManager(Level level) {
        if (!level.isClientSide()) {
            ReloadableServerResources resources = level.getServer().getServerResources().managers();
            return ((RegistryManagersGetter) resources).getPotionRecipesManager();
        } else {
            return ((RegistryManagersGetter) level).getPotionRecipesManager();
        }
    }
}
