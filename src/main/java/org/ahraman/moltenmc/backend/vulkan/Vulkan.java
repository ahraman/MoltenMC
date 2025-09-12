package org.ahraman.moltenmc.backend.vulkan;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.ahraman.moltenmc.backend.vulkan.instance.InstanceExtension;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.ahraman.moltenmc.backend.vulkan.util.Results;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkLayerProperties;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.Objects;

import static org.lwjgl.vulkan.VK10.*;

public final class Vulkan {
    private static @Nullable VulkanCapabilities capabilities;

    private Vulkan() {
    }

    public static @NotNull VulkanCapabilities getSupportedCapabilities() {
        return getSupportedCapabilities(false);
    }

    public static @NotNull VulkanCapabilities getSupportedCapabilities(boolean refresh) {
        if (refresh || capabilities == null) {
            var version = getSupportedVersion();
            var extensions = getSupportedExtensions();
            var layerExtensions = getSupportedLayerExtensions();
            capabilities = new VulkanCapabilities(version, extensions, layerExtensions);
        }

        return capabilities;
    }

    public static @NotNull ApiVersion getSupportedVersion() {
        return ApiVersion.of(VK.getInstanceVersionSupported());
    }

    public static @NotNull ImmutableMap<String, ImmutableSet<InstanceExtension>> getSupportedLayerExtensions() {
        try (var stack = MemoryStack.stackPush()) {
            var countBuffer = stack.mallocInt(1);
            Results.checked(vkEnumerateInstanceLayerProperties(countBuffer, null), "get instance layer count");

            int count = countBuffer.get(0);
            if (count == 0) {
                return ImmutableMap.of();
            }

            var buffer = VkLayerProperties.malloc(count, stack);
            Results.checked(vkEnumerateInstanceLayerProperties(countBuffer, buffer), "enumerate instance layers");

            return buffer.stream()
                         .collect(ImmutableMap.toImmutableMap(l -> l != null ? l.layerNameString() : null,
                                                              l -> l != null ? getSupportedExtensions(l.layerName())
                                                                       : null));
        }
    }

    public static @NotNull ImmutableSet<InstanceExtension> getSupportedExtensions() {
        return getSupportedExtensions(null);
    }

    public static @NotNull ImmutableSet<InstanceExtension> getSupportedExtensions(@Nullable ByteBuffer layer) {
        var layerName = layer != null ? MemoryUtil.memASCII(layer) : "none";
        try (var stack = MemoryStack.stackPush()) {
            var countBuffer = stack.mallocInt(1);
            Results.checked(vkEnumerateInstanceExtensionProperties(layer, countBuffer, null),
                            MessageFormat.format("get instance extension count [layer = {0}]", layerName));

            int count = countBuffer.get(0);
            if (count == 0) {
                return ImmutableSet.of();
            }

            var buffer = VkExtensionProperties.malloc(count, stack);
            Results.checked(vkEnumerateInstanceExtensionProperties(layer, countBuffer, buffer),
                            MessageFormat.format("enumerate instance extensions [layer = {0}]", layerName));

            return buffer.stream()
                         .map(e -> InstanceExtension.fromName(e.extensionNameString()))
                         .filter(Objects::nonNull)
                         .collect(Sets.toImmutableEnumSet());
        }
    }
}
