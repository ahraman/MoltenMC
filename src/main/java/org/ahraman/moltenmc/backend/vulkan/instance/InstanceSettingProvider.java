package org.ahraman.moltenmc.backend.vulkan.instance;

import com.google.common.collect.ImmutableSet;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InstanceSettingProvider {
    @NotNull ApiVersion version();

    @NotNull ImmutableSet<String> layers();

    default @NotNull ImmutableSet<InstanceExtension> extensions() {
        return this.extensions(null);
    }

    @Contract(value = "null -> !null")
    @Nullable ImmutableSet<InstanceExtension> extensions(@Nullable String layer);
}
