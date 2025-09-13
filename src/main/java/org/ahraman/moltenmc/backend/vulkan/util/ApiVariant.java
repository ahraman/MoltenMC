package org.ahraman.moltenmc.backend.vulkan.util;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public enum ApiVariant {
    VULKAN("Vulkan"),
    VULKAN_SC("VulkanSC"),
    ;

    private final @NotNull String id;

    ApiVariant(@NotNull String id) {
        this.id = id;
    }

    public static @NotNull ApiVariant of(int variant) {
        if (variant < values().length) {
            return values()[variant];
        } else {
            throw new IllegalArgumentException(MessageFormat.format("API variant `{0}` not supported", variant));
        }
    }

    public @NotNull String id() {
        return this.id;
    }

    @Override
    public @NotNull String toString() {
        return this.id();
    }
}
