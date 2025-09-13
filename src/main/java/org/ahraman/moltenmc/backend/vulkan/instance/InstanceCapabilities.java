package org.ahraman.moltenmc.backend.vulkan.instance;

import com.google.common.collect.ImmutableSet;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InstanceCapabilities extends AbstractInstanceCapabilities implements InstanceSettingProvider {
    private final @NotNull ImmutableSet<String> layers;

    public InstanceCapabilities(
        @NotNull ApiVersion version,
        @NotNull ImmutableSet<String> layers,
        @NotNull ImmutableSet<InstanceExtension> extensions) {
        super(version, extensions);
        this.layers = layers;
    }

    @Override
    public @NotNull ImmutableSet<String> layers() {
        return this.layers;
    }

    @Override
    public @NotNull ImmutableSet<InstanceExtension> extensions(@Nullable String layer) {
        return this.extensions;
    }
}
