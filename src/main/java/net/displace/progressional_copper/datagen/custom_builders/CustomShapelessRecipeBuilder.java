package net.displace.progressional_copper.datagen.custom_builders;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class CustomShapelessRecipeBuilder {
    private final HolderGetter<@NotNull Item> items;
    private final RecipeCategory category;
    private final ItemStack result;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private @Nullable String group;

    private CustomShapelessRecipeBuilder(HolderGetter<@NotNull Item> items, RecipeCategory category, ItemStack result) {
        this.items = items;
        this.category = category;
        this.result = result;
    }

    public static CustomShapelessRecipeBuilder shapeless(HolderGetter<@NotNull Item> items, RecipeCategory category, ItemStack result) {
        return new CustomShapelessRecipeBuilder(items, category, result);
    }

    public static CustomShapelessRecipeBuilder shapeless(HolderGetter<@NotNull Item> items, RecipeCategory category, ItemLike result) {
        return shapeless(items, category, result, 1);
    }

    public static CustomShapelessRecipeBuilder shapeless(HolderGetter<@NotNull Item> items, RecipeCategory category, ItemLike result, int count) {
        return new CustomShapelessRecipeBuilder(items, category, result.asItem().getDefaultInstance().copyWithCount(count));
    }

    public CustomShapelessRecipeBuilder requires(TagKey<@NotNull Item> tag) {
        return this.requires(Ingredient.of(this.items.getOrThrow(tag)));
    }

    public CustomShapelessRecipeBuilder requires(ItemLike item) {
        return this.requires((ItemLike)item, 1);
    }

    public CustomShapelessRecipeBuilder requires(ItemLike item, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public CustomShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires((Ingredient)ingredient, 1);
    }

    public CustomShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public CustomShapelessRecipeBuilder unlockedBy(String p_176781_, Criterion<?> p_300897_) {
        this.criteria.put(p_176781_, p_300897_);
        return this;
    }

    public CustomShapelessRecipeBuilder group(@Nullable String p_126195_) {
        this.group = p_126195_;
        return this;
    }

    public Item getResult() {
        return this.result.getItem();
    }

    public void save(RecipeOutput p_301215_, ResourceKey<@NotNull Recipe<?>> p_379987_) {
        this.ensureValid(p_379987_);
        Advancement.Builder advancement$builder = p_301215_.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_379987_)).rewards(AdvancementRewards.Builder.recipe(p_379987_)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancement$builder);
        this.criteria.forEach(advancement$builder::addCriterion);
        ShapelessRecipe shapelessrecipe = new ShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), this.result, this.ingredients);
        p_301215_.accept(p_379987_, shapelessrecipe, advancement$builder.build(p_379987_.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceKey<@NotNull Recipe<?>> recipe) {
    }
}
