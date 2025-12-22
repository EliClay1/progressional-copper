package net.displace.progressional_copper;

import net.displace.progressional_copper.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<@NotNull CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ProgressionalCopper.MOD_ID);

    public static final Supplier<CreativeModeTab> PROGRESSIONAL_COPPER_ITEMS_TAB =
            CREATIVE_MOD_TABS.register("progressional_copper_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.progressional_copper.progressional_copper_items_tab"))
                    .icon(() -> new ItemStack(ModItems.COPPER_TO_IRON_TEMPLATE.get()))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COPPER_TO_IRON_TEMPLATE);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
