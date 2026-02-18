package com.mikkelcat.potion_overhaul.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class ClientSpaghetti {
    public static Level getClientLevel() {
        return Minecraft.getInstance().level;
    }
}
