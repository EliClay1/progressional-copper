package net.displace.progressional_copper.event;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
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
                    LOGGER.info("IRON PICK FOUND - Cost A: {}, Cost B: {}, Result {}", offer.getCostA(), offer.getCostB(), offer.getResult());
                } else {
                    LOGGER.info("Cost A: {}, Cost B: {}, Result {}", offer.getCostA(), offer.getCostB(), offer.getResult());
                }
            }
        }
    }
}
