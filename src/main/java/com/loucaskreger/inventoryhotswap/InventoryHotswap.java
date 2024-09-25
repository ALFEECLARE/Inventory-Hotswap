package com.loucaskreger.inventoryhotswap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.loucaskreger.inventoryhotswap.client.EventSubscriber;
import com.loucaskreger.inventoryhotswap.config.Config;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(InventoryHotswap.MOD_ID)
public class InventoryHotswap {
    public static final String MOD_ID = "inventoryhotswap";
    private static final Logger LOGGER = LogManager.getLogger();
 
    public InventoryHotswap(IEventBus modEventBus, ModContainer modContainer) {
    	modEventBus.addListener(this::setup);
    	modEventBus.addListener(this::registKeyBinding);
    	modEventBus.addListener(EventSubscriber::onModConfigEvent);
    	modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        NeoForge.EVENT_BUS.register(EventSubscriber.class);
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
