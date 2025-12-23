package net.displace.progressional_copper;

import net.displace.progressional_copper.blocks.ModBlocks;
import net.displace.progressional_copper.datagen.loot.ModLootModifiers;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(ProgressionalCopper.MOD_ID)
public class ProgressionalCopper {
    public static final String MOD_ID = "progressional_copper";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProgressionalCopper(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        // 12/15/2025 - add registrations here.
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        // Register the item to a creative tab
        ModCreativeModTabs.register(modEventBus);
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.COPPER_TO_IRON_TEMPLATE);
        }
    }
}
