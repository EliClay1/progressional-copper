package net.displace.progressional_copper.event;

import net.displace.progressional_copper.ProgressionalCopper;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class Server {
//    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");
    public static final Set<ServerLevel> cachedServerLevels = new HashSet<>();

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        event.getServer().getAllLevels().forEach(cachedServerLevels::add);
    }

    @SubscribeEvent
    public static void onServerClose(ServerStoppedEvent event) {
        cachedServerLevels.clear();
    }
}
