package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
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
                float chance = Config.SMITHING_TEMPLATE_CHEST_CHANCE.get().floatValue();
                Random random = new Random();
                int count = random.nextInt(1) + minCount;
                LootPool customPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.COPPER_TO_IRON_TEMPLATE.get())
                                .setWeight(1)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))))
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .build();
                originalTable.addPool(customPool);
            }
        }

        // villager loot tables
        Identifier tableId = event.getName();
        if (tableId.toString().contains("entity/villager")) {
            LootTable originalTable = event.getTable();
            float chance = Config.SMITHING_TEMPLATE_DROP_CHANCE.get().floatValue();

            HolderLookup.Provider registries = event.getRegistries();

            LootPool customPool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.COPPER_TO_IRON_TEMPLATE.get())
                            .setWeight(1)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceCondition.randomChance(chance))
                    .when(LootItemEntityPropertyCondition.hasProperties(
                            LootContext.EntityTarget.THIS,
                            getArmorerPredicate(registries.lookupOrThrow(Registries.ENTITY_TYPE))
                            )).build();
            originalTable.addPool(customPool);
        }
    }

    private static EntityPredicate getArmorerPredicate(HolderGetter<@NotNull EntityType<?>> entityTypes) {
        CompoundTag villagerData = new CompoundTag();
        villagerData.putString("profession", "minecraft:armorer");

        CompoundTag nbt = new CompoundTag();
        nbt.put("VillagerData", villagerData);

        return EntityPredicate.Builder.entity()
                .of(entityTypes, EntityType.VILLAGER)
                .nbt(new NbtPredicate(nbt))
                .build();
    }
}
