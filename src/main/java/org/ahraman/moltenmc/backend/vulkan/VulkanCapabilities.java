package org.ahraman.moltenmc.backend.vulkan;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.ahraman.moltenmc.backend.vulkan.instance.AbstractInstanceCapabilities;
import org.ahraman.moltenmc.backend.vulkan.instance.InstanceExtension;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class VulkanCapabilities extends AbstractInstanceCapabilities {
    private final @NotNull ImmutableMap<String, ImmutableSet<InstanceExtension>> layerExtensions;

    public VulkanCapabilities(
        @NotNull ApiVersion version,
        @NotNull ImmutableSet<InstanceExtension> extensions,
        @NotNull ImmutableMap<String, ImmutableSet<InstanceExtension>> layerExtensions) {
        super(version, extensions);
        this.layerExtensions = layerExtensions;
    }

    @Override
    public @NotNull ImmutableSet<String> layers() {
        return this.layerExtensions.keySet();
    }

    @Override
    public @Nullable ImmutableSet<InstanceExtension> extensions(@Nullable String layer) {
        return layer == null ? this.extensions : this.layerExtensions.get(layer);
    }
}
