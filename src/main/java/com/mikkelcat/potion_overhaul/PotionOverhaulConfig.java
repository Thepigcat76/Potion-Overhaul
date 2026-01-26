package com.mikkelcat.potion_overhaul;

import com.portingdeadmods.portingdeadlibs.api.config.ConfigValue;

public class PotionOverhaulConfig {
    @ConfigValue(comment = "The amount of potions that can be brewed when the brewing stand has full fuel")
    public static int brewingStandFuelUses = 40;
}
