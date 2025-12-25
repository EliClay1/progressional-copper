package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.Map;
import java.util.Random;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class LootTableEvents {

    private static final String[] smithVillageChests = {"village_toolsmith", "village_weaponsmith"};
    private static final String[] emeraldVillageChests = {"village_plains_house", "village_desert_house",
            "village_savanna_house", "village_snowy_house", "village_taiga_house"};

    @SubscribeEvent
    public static void lootTableLoading(LootTableLoadEvent event) {

        // emerald increase
        for (String location : emeraldVillageChests) {
            int maxCount = 5;
            int minCount = 1;

            Identifier tableId = event.getName();
            if (tableId.toString().contains(String.format("village/%s", location))) {
                LootTable originalTable = event.getTable();
                float chance = Config.EMERALD_INCREASE_CHANCE.get().floatValue();
                Random random = new Random();
                int count = random.nextInt((maxCount - minCount) + 1) + minCount;
                LootPool customPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.EMERALD)
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))))
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .build();

                originalTable.addPool(customPool);
            }
        }

        // smithing template
        for (String location : smithVillageChests) {
            int minCount = 1;

            Identifier tableId = event.getName();
            if (tableId.toString().contains(String.format("village/%s", location))) {
                LootTable originalTable = event.getTable();
                float chance = Config.SMITHING_TEMPLATE_CHANCE.get().floatValue();
                Random random = new Random();
                int count = random.nextInt(1) + minCount;
                LootPool customPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.COPPER_TO_IRON_TEMPLATE.get())
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))))
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .build();
                originalTable.addPool(customPool);

                LootPool replacementPool = LootPool.lootPool()
                        .build();
            }
        }
    }
}
