package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.datagen.loot.RemoveItemModifier;
import net.displace.progressional_copper.items.ModItems;
import net.displace.progressional_copper.datagen.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ProgressionalCopper.MOD_ID);
    }

    private final String[] villageChestNames = {"village_toolsmith", "village_weaponsmith"};

    @Override
    protected void start() {
        // generates smithing templates and removes iron picks within the villager chests
        generateVillageChests();
    }

    private void generateVillageChests() {
        for (String location : villageChestNames) {
            this.add(String.format("smithing_template_from_%s", location),
                    new AddItemModifier(new LootItemCondition[] {
                            new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(String.format("chests/village/%s", location))).build(),
                            LootItemRandomChanceCondition.randomChance(0.50f).build()
                    }, ModItems.COPPER_TO_IRON_TEMPLATE.get()));

            this.add(String.format("remove_iron_pickaxe_from_%s", location),
                    new RemoveItemModifier(new LootItemCondition[] {
                            new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(String.format("chests/village/%s", location))).build()
                    }, Items.IRON_PICKAXE));
        }
    }
}
