package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.Set;

public class InstanceDescriptor implements InstanceSettingAcceptor {
    private @NotNull ApiVersion version = ApiVersion.VULKAN_1_0;
    private final @NotNull SequencedSet<String> layers = new LinkedHashSet<>();
    private final @NotNull Set<InstanceExtension> extensions = EnumSet.noneOf(InstanceExtension.class);

    public InstanceDescriptor() {}

    @Override
    public void version(@NotNull ApiVersion version) {
        if (!this.version.supports(version)) {
            this.version = version;
        }
    }

    @Override
    public @NotNull SequencedSet<String> layers() {
        return this.layers;
    }

    @Override
    public @NotNull Set<InstanceExtension> extensions() {
        return this.extensions;
    }
}
