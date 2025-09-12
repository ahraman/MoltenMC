package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;

import java.util.SequencedSet;
import java.util.Set;

public interface InstanceSettingAcceptor {
    void version(@NotNull ApiVersion version);

    @NotNull SequencedSet<String> layers();

    @NotNull Set<InstanceExtension> extensions();
}
