package net.displace.progressional_copper.datagen;

import net.displace.progressional_copper.ProgressionalCopper;
import net.displace.progressional_copper.datagen.custom_builders.CustomSmithingRecipeBuilder;
import net.displace.progressional_copper.event.PlayerInteractions;
import net.displace.progressional_copper.items.ModItems;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.*;
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

    private final List<Item> copperEquipment = PlayerInteractions.copperEquipmentList;
    private final List<Item> ironEquipment = PlayerInteractions.ironEquipmentList;

    @Override
    protected void buildRecipes() {
        // template crafting recipe
        shaped(RecipeCategory.TOOLS, ModItems.COPPER_TO_IRON_TEMPLATE.get())
                .pattern("CSC")
                .pattern("IEI")
                .pattern("CSC")
                .define('C', Items.COPPER_BLOCK)
                .define('S', Items.STONE)
                .define('I', Items.IRON_INGOT)
                .define('E', Items.EMERALD)
                .unlockedBy("has_copper", has(Items.COPPER_BLOCK))
                .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(
                        String.format("%s:crafting/%s", ProgressionalCopper.MOD_ID, "copper_to_iron_template"))));

        // template duplication recipe
        shaped(RecipeCategory.TOOLS, ModItems.COPPER_TO_IRON_TEMPLATE.get(), 2)
                .pattern("CTC")
                .pattern("CSC")
                .pattern("III")
                .define('C', Items.COPPER_INGOT)
                .define('S', Items.STONE)
                .define('I', Items.IRON_INGOT)
                .define('T', ModItems.COPPER_TO_IRON_TEMPLATE)
                .unlockedBy("has_copper_to_iron_template", has(ModItems.COPPER_TO_IRON_TEMPLATE))
                .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(
                        String.format("%s:crafting/%s", ProgressionalCopper.MOD_ID, "copper_to_iron_template_duplicate"))));

        // copper to iron smithing recipes
        for (int i = 0; i < copperEquipment.size(); i++) {
            createCopperToIronTemplateRecipes(i);
            // removes recipes for iron
            removeRecipe("minecraft", ironEquipment.get(i).asItem().toString().toLowerCase().split(":")[1]);
        }

    }

    private void createCopperToIronTemplateRecipes(int index) {
        String itemAsString = ironEquipment.get(index).asItem().toString().toLowerCase().split(":")[1] + "_smithing";

        CustomSmithingRecipeBuilder
                .smithing(Ingredient.of(ModItems.COPPER_TO_IRON_TEMPLATE),
                Ingredient.of(copperEquipment.get(index)), RecipeCategory.TOOLS,
                PlayerInteractions.ironEquipmentList.get(index))
                .unlocks("has_copper_to_iron_template", has(ModItems.COPPER_TO_IRON_TEMPLATE))
                .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(
                        String.format("%s:smithing/%s", ProgressionalCopper.MOD_ID, itemAsString))));
    }

    public void removeRecipe(String namespaceId, String recipeId) {
        ResourceKey<@NotNull Recipe<?>> resourceKey = ResourceKey.create(Registries.RECIPE,
                Identifier.parse(String.format("%s:%s", namespaceId, recipeId)));

        shapeless(RecipeCategory.MISC, Items.BARRIER)
                .requires(Items.BARRIER)
                .unlockedBy("never", RecipeUnlockedTrigger.unlocked(resourceKey))
                .save(output, resourceKey);

    }
}
