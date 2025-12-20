package net.displace.progressional_copper.event;

import net.displace.progressional_copper.ProgressionalCopper;
import net.neoforged.fml.common.EventBusSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventBusSubscriber(modid = ProgressionalCopper.MOD_ID)
public class ServerEvents {
    public static final Logger LOGGER = LoggerFactory.getLogger("progressional_copper");
}
