package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public @NotNull String getName() {
            return "My Recipes";
        }
    }

    @Override
    protected void buildRecipes() {

        shaped(RecipeCategory.TOOLS, ModItems.COPPER_TO_IRON_TEMPLATE.get())
                .pattern("CSC")
                .pattern("IEI")
                .pattern("CSC")
                .define('C', Items.COPPER_BLOCK)
                .define('S', Items.STONE)
                .define('I', Items.IRON_INGOT)
                .define('E', Items.EMERALD)
                .unlockedBy("has_copper", has(Items.COPPER_BLOCK))
                .save(output, "copper_to_iron_template");

        shaped(RecipeCategory.TOOLS, ModItems.COPPER_TO_IRON_TEMPLATE.get())
                .pattern("CTC")
                .pattern("CSC")
                .pattern("III")
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.STONE)
                .define('I', Items.IRON_INGOT)
                .define('T', ModItems.COPPER_TO_IRON_TEMPLATE)
                .unlockedBy("has_copper_to_iron_template", has(ModItems.COPPER_TO_IRON_TEMPLATE))
                .save(output, "copper_to_iron_template_duplicate");
    }
}
