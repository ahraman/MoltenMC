package org.ahraman.moltenmc.client.render;

import com.mojang.logging.LogUtils;
import org.ahraman.moltenmc.backend.vulkan.instance.Instance;
import org.ahraman.moltenmc.backend.vulkan.instance.InstanceSetting;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class MoltenRenderer {
    private static final @NotNull Logger LOGGER = LogUtils.getLogger();

    private final @NotNull Instance instance;

    public MoltenRenderer() {
        LOGGER.info("Initializing MoltenMC renderer.");
        this.instance = createInstance();
    }

    private static @NotNull Instance createInstance() {
        var factory = Instance.factory()
                              .applicationInfo("minecraft", 1)
                              .engineInfo("moltenmc-vulkan", 1)
                              .required(InstanceSetting.API_VERSION_1_1,
                                        InstanceSetting.SURFACE,
                                        InstanceSetting.SURFACE_CREATE_WIN32)
                              .optional(InstanceSetting.API_VERSION_1_3, InstanceSetting.API_VERSION_1_2)
                              .optional(InstanceSetting.SURFACE_EXTENDED_QUERIES,
                                        InstanceSetting.SURFACE_EXTENDED_INFO,
                                        InstanceSetting.SURFACE_EXTENDED_COLOR_SPACE)
                              .optional(InstanceSetting.LAYER_KHRONOS_VALIDATION,
                                        InstanceSetting.LAYER_CRASH_DIAGNOSTIC,
                                        InstanceSetting.DEBUG_CALLBACKS,
                                        InstanceSetting.LAYER_VALIDATION_SETTINGS);

        var instance = factory.create();
        LOGGER.info("Created Vulkan instance.");

        var capabilities = instance.capabilities();
        LOGGER.info("    version: {}", capabilities.version());

        var layers = capabilities.layers();
        if (!layers.isEmpty()) {
            LOGGER.info("    {} enabled layers: {}", layers.size(), layers);
        }

        var extensions = capabilities.extensions();
        if (!extensions.isEmpty()) {
            LOGGER.info("    {} enabled extensions: {}", extensions.size(), extensions);
        }

        return instance;
    }

    public void cleanup() {
        LOGGER.info("Cleaning up MoltenMC resources.");
        this.instance.destroy();
    }
}
