package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries,
                                  List<AdvancementSubProvider> subProviders) {
        super(output, registries, subProviders);
    }

    public static ModAdvancementProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new ModAdvancementProvider(output, registries,
                List.of(new AdvancementGenerator()));
    }

    public static class AdvancementGenerator implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer) {
            Advancement.Builder builder = Advancement.Builder.advancement();
            generateNewBasicAdvancement(provider, consumer, builder, ModItems.COPPER_TO_IRON_TEMPLATE.get(),
                    "story/copper_upgrade", "minecraft:story/smelt_iron",
                    null, AdvancementType.GOAL, true, true, false,
                    "has_iron_template", ModItems.COPPER_TO_IRON_TEMPLATE.get(), ProgressionalCopper.MOD_ID);
        }
    }

    private static void generateNewBasicAdvancement(
            HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, Advancement.Builder builder,
            Item displayItem, String advancementPath, String parentAdvancement, Identifier backgroundImagePath,
            AdvancementType advancementType, boolean showToast, boolean announceToChat, boolean hideAdvancement,
            String criterionKey, Item criterionItem, String modId) {
        builder.parent(AdvancementSubProvider.createPlaceholder(parentAdvancement));
        builder.display(
                new ItemStack(displayItem),
                Component.translatable(String.format("advancements.progressional_copper.%s.title", advancementPath)),
                Component.translatable(String.format("advancements.progressional_copper.%s.description", advancementPath)),
                backgroundImagePath, advancementType, showToast, announceToChat, hideAdvancement);
        builder.addCriterion(criterionKey, InventoryChangeTrigger.TriggerInstance.hasItems(criterionItem));
        builder.requirements(AdvancementRequirements.allOf(List.of(criterionKey)));
        builder.save(consumer, Identifier.fromNamespaceAndPath(modId, advancementPath));
    }
}
