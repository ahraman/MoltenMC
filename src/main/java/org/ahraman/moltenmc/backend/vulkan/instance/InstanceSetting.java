package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;

import static org.ahraman.moltenmc.backend.vulkan.instance.InstanceCondition.*;

public enum InstanceSetting {
    API_VERSION_1_1("api_version_1_1", version(ApiVersion.VULKAN_1_1, true)),
    API_VERSION_1_2("api_version_1_2", version(ApiVersion.VULKAN_1_2, true)),
    API_VERSION_1_3("api_version_1_3", version(ApiVersion.VULKAN_1_3, true)),

    PROTECTED_MEMORY_QUERIES("protected_memory_queries", version(ApiVersion.VULKAN_1_1)),

    DEVICE_EXTENDED_QUERIES("device_extended_info",
                            any(version(ApiVersion.VULKAN_1_1),
                                extension(InstanceExtension.VK_KHR_get_physical_device_properties2))),
    DEVICE_GROUP_QUERIES("device_group_queries",
                         any(version(ApiVersion.VULKAN_1_1),
                             extension(InstanceExtension.VK_KHR_device_group_creation))),
    NONCONFORMANT_DEVICES("nonconformant_devices",
                          all(extension(InstanceExtension.VK_KHR_portability_enumeration),
                              setting(DEVICE_EXTENDED_QUERIES))),

    SURFACE("surface", extension(InstanceExtension.VK_KHR_surface)),
    SURFACE_EXTENDED_QUERIES("surface_extended_queries",
                             all(extension(InstanceExtension.VK_KHR_get_surface_capabilities2),
                                 extension(InstanceExtension.VK_KHR_surface))),
    SURFACE_EXTENDED_INFO("surface_extended_info",
                          all(any(extension(InstanceExtension.VK_KHR_surface_maintenance1),
                                  extension(InstanceExtension.VK_EXT_surface_maintenance1)),
                              setting(SURFACE_EXTENDED_QUERIES))),
    SURFACE_PROTECTED_INFO("surface_protected_info",
                           all(extension(InstanceExtension.VK_KHR_surface_protected_capabilities),
                               version(ApiVersion.VULKAN_1_1),
                               setting(SURFACE_EXTENDED_QUERIES))),
    SURFACE_EXTENDED_COLOR_SPACE("surface_extended_color_space",
                                 all(extension(InstanceExtension.VK_EXT_swapchain_colorspace),
                                     extension(InstanceExtension.VK_KHR_surface))),
    SURFACE_CREATE_HEADLESS("surface_create_headless",
                            all(extension(InstanceExtension.VK_EXT_headless_surface),
                                extension(InstanceExtension.VK_KHR_surface))),
    SURFACE_CREATE_WIN32("surface_create_win32",
                         all(extension(InstanceExtension.VK_KHR_win32_surface),
                             extension(InstanceExtension.VK_KHR_surface))),

    DISPLAY("display", extension(InstanceExtension.VK_KHR_display)),
    DISPLAY_EXTENDED_QUERIES("display_extended_queries", extension(InstanceExtension.VK_KHR_get_display_properties2)),

    DEBUG_CALLBACKS("debug_callbacks",
                    any(extension(InstanceExtension.VK_EXT_debug_utils),
                        extension(InstanceExtension.VK_EXT_debug_report))),
    LAYER_SETTINGS("layer_settings", extension(InstanceExtension.VK_EXT_layer_settings, true)),
    LAYER_VALIDATION_SETTINGS("layer_validation_settings",
                              any(setting(LAYER_SETTINGS),
                                  extension(InstanceExtension.VK_EXT_validation_features, true),
                                  extension(InstanceExtension.VK_EXT_validation_flags, true))),

    LAYER_KHRONOS_VALIDATION("layer_khronos_validation", layer("VK_LAYER_KHRONOS_validation")),
    LAYER_CRASH_DIAGNOSTIC("layer_crash_diagnostic", layer("VK_LAYER_LUNARG_crash_diagnostic")),
    LAYER_API_DUMP("layer_api_dump", layer("VK_LAYER_LUNARG_api_dump")),
    ;

    private final @NotNull String id;
    private final @NotNull InstanceCondition condition;

    InstanceSetting(@NotNull String id, @NotNull InstanceCondition condition) {
        this.id = id;
        this.condition = condition;
    }

    public boolean test(@NotNull InstanceSettingProvider provider) {
        return this.condition.test(provider);
    }

    public boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
        return this.condition.enable(provider, acceptor);
    }

    public @NotNull String id() {
        return id;
    }

    @Override
    public @NotNull String toString() {
        return this.id();
    }
}
