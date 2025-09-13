package org.ahraman.moltenmc.backend.vulkan.instance;

import org.jetbrains.annotations.NotNull;

public record InstanceInfo(@NotNull String applicationName, int applicationVersion, @NotNull String engineName,
                           int engineVersion) {}
