package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.blocks.ModBlocks;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        /// Available options to drop blocks:
//        dropSelf(ModBlocks.TEST_BLOCK.get());
//        this.add(ModBlocks.TEST_BLOCK.get(), block -> createDrop...()); Additionally, overriding createDrop functionality.

        this.add(ModBlocks.TEST_BLOCK.get(),
                block -> createOreDrop(ModBlocks.TEST_BLOCK.get(), ModItems.COPPER_TO_IRON_TEMPLATE.get()));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // returns all the known blocks within the ModBlocks class
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
