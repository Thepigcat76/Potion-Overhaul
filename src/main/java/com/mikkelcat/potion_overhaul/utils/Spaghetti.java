package com.mikkelcat.potion_overhaul.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class Spaghetti {
    public static Level tryGetLevel() {
        if (FMLEnvironment.dist.isClient()) {
            return ClientSpaghetti.getClientLevel();
        } else {
            MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
            if (currentServer != null) {
                return currentServer.overworld();
            }
        }
        return null;
    }
}
