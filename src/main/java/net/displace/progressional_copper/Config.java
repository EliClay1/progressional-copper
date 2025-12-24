package net.displace.progressional_copper;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue REPLACE_DIAMOND_WITH_COPPER_TRADES = BUILDER
            .comment("Removes all diamond trades to match the progression balance")
            .define("replace_diamond_with_copper_trade", true);

    public static final ModConfigSpec.DoubleValue SMITHING_TEMPLATE_CHANCE = BUILDER
            .comment("Chance to find smithing template in village toolsmith/weaponsmith")
            .defineInRange("smithing_template_chance", 0.7D, 0.0D, 1.0D);

    public static final ModConfigSpec.DoubleValue EMERALD_INCREASE_CHANCE = BUILDER
            .comment("Chance to find extra emerald in village houses")
            .defineInRange("emerald_increase_chance", 0.7D, 0.0D, 1.0D);

    // TODO - add configuration changes for recipe removal, and maybe even add functionality for lists of items to remove.

    // TODO - configure drop chance from villagers (datagen)

    static final ModConfigSpec SPEC = BUILDER.build();
}
