package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.blocks.ModBlocks;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ProgressionalCopper.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.COPPER_TO_IRON_TEMPLATE.get(), ModelTemplates.FLAT_ITEM);

//        blockModels.createTrivialCube(ModBlocks.TEST_BLOCK.get());
    }
}
