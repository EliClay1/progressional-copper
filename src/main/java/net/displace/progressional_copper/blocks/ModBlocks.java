package net.displace.progressional_copper.blocks;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ProgressionalCopper.MOD_ID);

//    public static final DeferredBlock<@NotNull Block> TEST_BLOCK = BLOCKS.registerBlock("test_block",
//            Block::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
