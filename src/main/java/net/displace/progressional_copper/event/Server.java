package net.displace.progressional_copper.event;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.fixes.ItemStackCustomNameToOverrideComponentFix;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class Server {
    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");
    public static final Set<ServerLevel> cachedServerLevels = new HashSet<>();

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        event.getServer().getAllLevels().forEach(cachedServerLevels::add);
    }

    @SubscribeEvent
    public static void onServerClose(ServerStoppedEvent event) {
        cachedServerLevels.clear();
    }

    @SubscribeEvent
    public static void onVillagerGuiOpen(PlayerInteractEvent.EntityInteract event) {

        // prevents clientSide crash
        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getTarget() instanceof Villager villager) {
            var allOffers = villager.getOffers();
            for (MerchantOffer offer : allOffers) {
                if (ItemStack.isSameItem(offer.getResult(), Items.IRON_PICKAXE.getDefaultInstance())) {
                    LOGGER.info("IRON PICK FOUND - Cost A: {}, Cost B: {}, Result {} \n Changing Offer...",
                            offer.getCostA(), offer.getCostB(), offer.getResult());

                    List<EnchantmentInstance> enchantmentInstanceList = new ArrayList<>();

                    for (var enchant : offer.getResult().getTagEnchantments().entrySet()) {
                        Holder<@NotNull Enchantment> enchantment = enchant.getKey();
                        EnchantmentInstance enchantmentInstance = new EnchantmentInstance(Holder.direct(enchantment.value()),
                                enchant.getIntValue());
                        enchantmentInstanceList.add(enchantmentInstance);
                    }
                    ItemStack replacementTool = Items.COPPER_PICKAXE.applyEnchantments(Items.COPPER_PICKAXE.getDefaultInstance(),
                            enchantmentInstanceList);

                    MerchantOffer newOffer = new MerchantOffer(
                            offer.getItemCostA(),
                            offer.getItemCostB(),
                            replacementTool,
                            offer.getMaxUses(),
                            offer.getXp(),
                            offer.getPriceMultiplier()
                    );
                    allOffers.set(allOffers.indexOf(offer), newOffer);
                }
            }
        }
    }
}
