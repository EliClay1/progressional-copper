package net.displace.progressional_copper.items;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProgressionalCopper.MOD_ID);

    public static final DeferredItem<@NotNull Item> RUBY = ITEMS.registerSimpleItem("ruby");

    /*
    * This should create a smithing template, though I don't think it will work with the tools I need it to.
    * I think I'll need to use the above stuff to map the smithing template to only work on copper tools with iron ingots.
    * */

    public static final DeferredItem<@NotNull SmithingTemplateItem> IRON_TO_COPPER_TEMPLATE =
            ITEMS.register("iron_to_copper_template",
                    () -> new SmithingTemplateItem(
                            Component.translatable("item.progressional_copper.smithing_template.iron_to_copper.applies_to"),
                            Component.translatable("item.progressional_copper.smithing_template.iron_to_copper.ingredients"),
                            Component.translatable("item.progressional_copper.smithing_template.iron_to_copper.base_slot_description"),
                            Component.translatable("item.progressional_copper.smithing_template.iron_to_copper.additions_slot_description"),
                            List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
                            // Leaving this blank, may break. Just duplicate above code.
                            List.of(),
                            new Item.Properties().rarity(Rarity.UNCOMMON)
                    )
            );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
