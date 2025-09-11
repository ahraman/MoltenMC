package org.ahraman.moltenmc.client.render;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class MoltenRenderer {
    private static final @NotNull Logger LOGGER = LogUtils.getLogger();

    public MoltenRenderer() {
        LOGGER.info("Initializing MoltenMC renderer.");
    }

    public void cleanup() {
        LOGGER.info("Cleaning up MoltenMC resources.");
    }
}
