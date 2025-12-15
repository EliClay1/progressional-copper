package net.displace.progressional_copper.items;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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

    public static final DeferredItem<@NotNull SmithingTemplateItem> COPPER_TO_IRON_TEMPLATE =
            ITEMS.registerItem("copper_to_iron_template", registryName -> new SmithingTemplateItem(
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.applies_to"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.ingredients"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.base_slot_description"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.additions_slot_description"),
                    List.of(Identifier.withDefaultNamespace("container/slot/ingot")),
                    // Leaving this blank, may break. Just duplicate above code.
                    List.of(),
                    // Todo - Find a better way to implement the ID. I'm not really sure what is going on here.
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                            Identifier.fromNamespaceAndPath(ProgressionalCopper.MOD_ID, "copper_to_iron_template")))
            ));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
