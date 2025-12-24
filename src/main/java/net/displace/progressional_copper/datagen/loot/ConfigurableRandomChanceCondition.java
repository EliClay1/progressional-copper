package net.displace.progressional_copper.datagen.loot;

import net.displace.progressional_copper.Config;
import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class ConfigurableRandomChanceCondition implements LootItemCondition {
    private final String configKey;
    private final float fallback;

    public ConfigurableRandomChanceCondition(String configKey, float fallback) {
        this.configKey = configKey;
        this.fallback = fallback;
    }


    @Override
    public boolean test(LootContext lootContext) {
        float chance = switch (configKey) {
            case "smithing_template" -> Config.SMITHING_TEMPLATE_CHANCE.get().floatValue();
            case "emerald_increase" -> Config.EMERALD_INCREASE_CHANCE.get().floatValue();
            default -> fallback;
        };
        return lootContext.getRandom().nextFloat() < chance;
    }

    @Override
    public LootItemConditionType getType() {
        return null;
    }
}
