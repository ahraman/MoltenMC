package org.ahraman.moltenmc.backend.vulkan.instance;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum InstanceExtension {
    VK_KHR_get_physical_device_properties2,
    VK_KHR_device_group_creation,
    VK_KHR_portability_enumeration,

    VK_KHR_surface,
    VK_KHR_get_surface_capabilities2,
    VK_KHR_surface_maintenance1,
    VK_EXT_surface_maintenance1,
    VK_KHR_surface_protected_capabilities,
    VK_EXT_swapchain_colorspace,
    VK_EXT_headless_surface,
    VK_KHR_win32_surface,

    VK_KHR_display,
    VK_KHR_get_display_properties2,

    VK_EXT_debug_utils,
    VK_EXT_debug_report,

    VK_EXT_layer_settings,
    VK_EXT_validation_features,
    VK_EXT_validation_flags,
    ;

    public static @Nullable InstanceExtension fromName(@NotNull String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    @Override
    public final @NotNull String toString() {
        return this.name();
    }
}
