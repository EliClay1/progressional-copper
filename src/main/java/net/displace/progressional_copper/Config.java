package net.displace.progressional_copper;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue REPLACE_DIAMOND_WITH_COPPER_TRADES = BUILDER
            .comment("Removes all diamond trades to match the progression balance")
            .define("replaceDiamondWithCopperTrade", true);

    // TODO - add configuration changes for recipe removal, and maybe even add functionality for lists of items to remove.

    // TODO - configure drop chance from villagers (datagen)

    static final ModConfigSpec SPEC = BUILDER.build();
}
