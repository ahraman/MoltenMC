package org.ahraman.moltenmc.backend.vulkan.features;

import org.jetbrains.annotations.NotNull;

public interface Extension {
    @NotNull String extensionName();

    @NotNull ExtensionType type();
}
