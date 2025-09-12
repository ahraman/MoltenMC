package org.ahraman.moltenmc.backend.vulkan.instance;

import com.google.common.collect.ImmutableSet;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractInstanceCapabilities implements InstanceSettingProvider {
    protected final @NotNull ApiVersion version;
    protected final @NotNull ImmutableSet<InstanceExtension> extensions;

    protected AbstractInstanceCapabilities(
        @NotNull ApiVersion version,
        @NotNull ImmutableSet<InstanceExtension> extensions) {
        this.version = version;
        this.extensions = extensions;
    }

    @Override
    public final @NotNull ApiVersion version() {
        return this.version;
    }

    @Override
    public @NotNull ImmutableSet<InstanceExtension> extensions() {
        return this.extensions;
    }
}
