package org.ahraman.moltenmc.backend.vulkan.util;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK11.*;
import static org.lwjgl.vulkan.VK12.*;
import static org.lwjgl.vulkan.VK13.*;

public record ApiVersion(int value) implements Comparable<ApiVersion> {
    public static final @NotNull ApiVersion VULKAN_1_0 = new ApiVersion(VK_API_VERSION_1_0);
    public static final @NotNull ApiVersion VULKAN_1_1 = new ApiVersion(VK_API_VERSION_1_1);
    public static final @NotNull ApiVersion VULKAN_1_2 = new ApiVersion(VK_API_VERSION_1_2);
    public static final @NotNull ApiVersion VULKAN_1_3 = new ApiVersion(VK_API_VERSION_1_3);

    public static @NotNull ApiVersion of(int value) {
        return new ApiVersion(value);
    }

    public static @NotNull ApiVersion of(int variant, int major, int minor, int patch) {
        return ApiVersion.of(make(variant, major, minor, patch));
    }

    public static int make(int variant, int major, int minor, int patch) {
        return (variant << 29) | (major << 22) | (minor << 12) | patch;
    }

    public @NotNull ApiVariant api() {
        return ApiVariant.of(this.variant());
    }

    /**
     * Same as {@link org.lwjgl.vulkan.VK10#VK_API_VERSION_VARIANT(int) VK_API_VERSION_VARIANT(int)}.
     *
     * @return The variant component of the API version.
     */
    public int variant() {
        return this.value >>> 29;
    }

    /**
     * Same as {@link org.lwjgl.vulkan.VK10#VK_API_VERSION_MAJOR(int) VK_API_VERSION_MAJOR(int)}.
     *
     * @return The major component of the API version.
     */
    public int major() {
        return (this.value >>> 22) & 0x7F;
    }

    /**
     * Same as {@link org.lwjgl.vulkan.VK10#VK_API_VERSION_MINOR(int) VK_API_VERSION_MINOR(int)}.
     *
     * @return The minor component of the API version.
     */
    public int minor() {
        return (this.value >>> 12) & 0x3FF;
    }

    /**
     * Same as {@link org.lwjgl.vulkan.VK10#VK_API_VERSION_PATCH(int) VK_API_VERSION_PATCH(int)}.
     *
     * @return The patch component of the API version.
     */
    public int patch() {
        return this.value & 0xFFF;
    }

    public ApiVersion ignorePatch() {
        return ApiVersion.of(this.value & 0xFFFFF000);
    }

    public boolean supports(@NotNull ApiVersion o) {
        return this.variant() == o.variant() && this.compareTo(o) >= 0;
    }

    @Override
    public int compareTo(@NotNull ApiVersion o) {
        // Ignore the variants in Version comparison.
        return Integer.compare(this.value & 0x1FFFFFFF, o.value & 0x1FFFFFFF);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ApiVersion(int value1) && this.value == value1;
    }

    @Override
    public @NotNull String toString() {
        return MessageFormat.format("{0} {1}.{2}.{3}", this.api(), this.major(), this.minor(), this.patch());
    }
}
