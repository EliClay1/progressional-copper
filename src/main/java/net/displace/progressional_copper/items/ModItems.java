package net.displace.progressional_copper.items;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {

    private static final Identifier EMPTY_SLOT_HELMET = Identifier.withDefaultNamespace("container/slot/helmet");
    private static final Identifier EMPTY_SLOT_CHESTPLATE = Identifier.withDefaultNamespace("container/slot/chestplate");
    private static final Identifier EMPTY_SLOT_LEGGINGS = Identifier.withDefaultNamespace("container/slot/leggings");
    private static final Identifier EMPTY_SLOT_BOOTS = Identifier.withDefaultNamespace("container/slot/boots");
    private static final Identifier EMPTY_SLOT_HOE = Identifier.withDefaultNamespace("container/slot/hoe");
    private static final Identifier EMPTY_SLOT_AXE = Identifier.withDefaultNamespace("container/slot/axe");
    private static final Identifier EMPTY_SLOT_SWORD = Identifier.withDefaultNamespace("container/slot/sword");
    private static final Identifier EMPTY_SLOT_SHOVEL = Identifier.withDefaultNamespace("container/slot/shovel");
    private static final Identifier EMPTY_SLOT_SPEAR = Identifier.withDefaultNamespace("container/slot/spear");
    private static final Identifier EMPTY_SLOT_PICKAXE = Identifier.withDefaultNamespace("container/slot/pickaxe");
    private static final Identifier EMPTY_SLOT_NAUTILUS_ARMOR = Identifier.withDefaultNamespace("container/slot/nautilus_armor");


    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(ProgressionalCopper.MOD_ID);

    public static final DeferredItem<@NotNull SmithingTemplateItem> COPPER_TO_IRON_TEMPLATE =
            // in order to avoid the setID error, pass the lambda variable -> the AdvancedItem() (smithing template, pickaxe, etc.)
            ITEMS.registerItem("copper_to_iron_template", properties -> new SmithingTemplateItem(
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.applies_to"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.ingredients"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.base_slot_description"),
                    Component.translatable("item.progressional_copper.smithing_template.copper_to_iron.additions_slot_description"),
                    createCopperToIronUpgradeIconList(),
                    List.of(),
                    properties
            ));


    // while not entirely necessary, this is helpful for code organization.
    private static List<Identifier> createCopperToIronUpgradeIconList() {
        return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE,
                EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL,
                EMPTY_SLOT_NAUTILUS_ARMOR, EMPTY_SLOT_SPEAR);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
