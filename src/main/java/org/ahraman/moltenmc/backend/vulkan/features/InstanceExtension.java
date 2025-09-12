package org.ahraman.moltenmc.backend.vulkan.features;

import org.jetbrains.annotations.NotNull;

public enum InstanceExtension implements Extension {
    VK_KHR_get_physical_device_properties2,
    VK_KHR_device_group_creation,

    VK_KHR_surface,
    VK_KHR_win32_surface,

    VK_KHR_display,

    VK_EXT_debug_utils,
    VK_EXT_debug_report,

    VK_EXT_layer_settings,
    VK_EXT_validation_features,
    VK_EXT_validation_flags,
    ;

    public static @NotNull Extension fromName(@NotNull String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException ignored) {
            return new Custom(name);
        }
    }

    @Override
    public @NotNull String extensionName() {
        return this.name();
    }

    @Override
    public @NotNull ExtensionType type() {
        return ExtensionType.INSTANCE;
    }

    public static final class Custom implements Extension {
        private final @NotNull String name;

        private Custom(@NotNull String name) {
            this.name = name;
        }

        @Override
        public @NotNull String extensionName() {
            return this.name;
        }

        @Override
        public @NotNull ExtensionType type() {
            return ExtensionType.INSTANCE;
        }
    }
}
