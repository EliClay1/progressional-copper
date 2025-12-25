package net.displace.progressional_copper.event;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class PlayerInteractions {

//    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");
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

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();

        if (level.isClientSide()) {
            return;
        }

        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        // Iron tool chest replacement
        if (Config.REPLACE_IRON_EQUIPMENT_WITH_COPPER.isTrue()) {
            ironToolReplacement(block, level, blockPos);
        }
    }

    private static void ironToolReplacement(Block block, Level level, BlockPos blockPos) {
        if (block instanceof ChestBlock) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                ResourceKey<@NotNull LootTable> lootTableResourceKey = chestBlockEntity.getLootTable();

                if (lootTableResourceKey == null) {
                    return;
                }
                var path = lootTableResourceKey.identifier().getPath();
                if (path.contains("toolsmith") || path.contains("weaponsmith")) {
                    copperToIron.forEach((copperItem, ironItem) -> {
                        replaceItemFromChest(chestBlockEntity, ironItem, copperItem);
                    });
                }
            }
        }
    }

    private static void replaceItemFromChest(ChestBlockEntity chest, Item itemToRemove, Item itemToReplace) {
        for (int slot = 0; slot < chest.getContainerSize(); slot++) {
            ItemStack stack = chest.getItem(slot);

            if (stack.getItem() == itemToRemove) {
                chest.setItem(slot, new ItemStack(itemToReplace));
            }
        }
        chest.setChanged();
    }
}
