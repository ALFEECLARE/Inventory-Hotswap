package com.loucaskreger.inventoryhotswap.config;

import org.apache.commons.lang3.tuple.Pair;

import com.loucaskreger.inventoryhotswap.client.GuiRenderType;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ClientConfig CLIENT;
    public static final ModConfigSpec CLIENT_SPEC;

    public static GuiRenderType guiRenderType;
    public static boolean inverted;

    static {
        final Pair<ClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static void bakeConfig() {
        guiRenderType = ClientConfig.guiRenderType.get();
        inverted = ClientConfig.inverted.get();
    }
}
