package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.datagen.loot.ReplaceItemModifier;
import net.displace.progressional_copper.items.ModItems;
import net.displace.progressional_copper.datagen.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ProgressionalCopper.MOD_ID);
    }

    private final String[] smithVillageChests = {"village_toolsmith", "village_weaponsmith"};
    private final String[] emeraldVillageChests = {"village_plains_house", "village_desert_house",
            "village_savanna_house", "village_snowy_house", "village_taiga_house"};

    private static final Map<Item, Item> copperToIron = Map.ofEntries(
            Map.entry(Items.COPPER_PICKAXE, Items.IRON_PICKAXE),
            Map.entry(Items.COPPER_SHOVEL, Items.IRON_SHOVEL),
            Map.entry(Items.COPPER_SWORD, Items.IRON_SWORD),
            Map.entry(Items.COPPER_HELMET, Items.IRON_HELMET),
            Map.entry(Items.COPPER_CHESTPLATE, Items.IRON_CHESTPLATE),
            Map.entry(Items.COPPER_LEGGINGS, Items.IRON_LEGGINGS),
            Map.entry(Items.COPPER_BOOTS, Items.IRON_BOOTS),
            Map.entry(Items.COPPER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR),
            Map.entry(Items.COPPER_SPEAR, Items.IRON_SPEAR)
    );

    @Override
    protected void start() {
        // generates smithing templates and removes iron picks within the villager chests
        generateVillageChests();
    }

    private void generateVillageChests() {
        for (String location : smithVillageChests) {
            this.add(String.format("smithing_template_from_%s", location),
                    new AddItemModifier(new LootItemCondition[] {
                            new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(String.format("chests/village/%s", location))).build(),
                            LootItemRandomChanceCondition.randomChance(0.50f).build()
                    }, ModItems.COPPER_TO_IRON_TEMPLATE.get(), 1, 1));

            // replaces iron equipment with copper.
            copperToIron.forEach((copperItem, ironItem) -> {
                this.add(String.format("replace_%s_in_%s", ironItem.asItem().toString().toLowerCase()
                                .split(":")[1], location),
                        new ReplaceItemModifier(new LootItemCondition[] {
                                new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(String.format("chests/village/%s", location))).build()
                        }, ironItem, copperItem));
            });
        }

        for (String location : emeraldVillageChests) {
            this.add(String.format("emerald_increase_in_%s", location),
                    new AddItemModifier(new LootItemCondition[] {
                            new LootTableIdCondition.Builder(Identifier.withDefaultNamespace(String.format("chests/village/%s", location))).build(),
                            LootItemRandomChanceCondition.randomChance(0.50f).build(),
                    }, Items.EMERALD, 14, 15));
        }
    }
}
