package com.loucaskreger.inventoryhotswap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.loucaskreger.inventoryhotswap.client.EventSubscriber;
import com.loucaskreger.inventoryhotswap.config.Config;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;

@Mod(InventoryHotswap.MOD_ID)
public class InventoryHotswap {
    public static final String MOD_ID = "inventoryhotswap";
    private static final Logger LOGGER = LogManager.getLogger();

    public InventoryHotswap() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registKeyBinding);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    	setupCompatibility();
    }

    private void setupCompatibility() {
    	LoadingModList modlist = FMLLoader.getLoadingModList();
    	EventSubscriber.ipn = modlist.getModFileById("inventoryprofilesnext");

    }

    private void registKeyBinding(final RegisterKeyMappingsEvent event) {
        event.register(EventSubscriber.vertScroll);
    }


}
