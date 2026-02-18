package com.mikkelcat.potion_overhaul;

import com.portingdeadmods.portingdeadlibs.api.config.ConfigValue;

// TODO: Fix pdl config changing issue
public class PotionOverhaulConfig {
    @ConfigValue(comment = "The amount of potions that can be brewed when the brewing stand has full fuel", range = {0, Integer.MAX_VALUE})
    public static int brewingStandFuelUses = 40;
}
