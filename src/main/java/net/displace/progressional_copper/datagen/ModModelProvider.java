package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.blocks.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    protected void generateBlockStateModels(BlockModelGenerators generator) {
        // New way to generate a simple bloc
    }
}
