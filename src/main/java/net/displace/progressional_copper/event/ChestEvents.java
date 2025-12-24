package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.resources.Identifier;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class ChestEvents {

    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");

    @SubscribeEvent
    public static void chestOpen(LootTableLoadEvent event) {
        Identifier tableId = event.getName();

        if (tableId.toString().contains("village/village_plains_house")) {
            LOGGER.info("Modifying Plains House Loot Table");

            LootTable originalTable = event.getTable();

            float chance = Config.EMERALD_INCREASE_CHANCE.get().floatValue();

            LootPool customPool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EMERALD)
                            .setWeight(1)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(20))))
                    .when(LootItemRandomChanceCondition.randomChance(chance))
                    .build();

            originalTable.addPool(customPool);
        }

    }
}
