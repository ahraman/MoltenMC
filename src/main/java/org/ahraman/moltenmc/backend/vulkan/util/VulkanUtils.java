package org.ahraman.moltenmc.backend.vulkan.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.util.Collection;

public final class VulkanUtils {
    private VulkanUtils() {}

    public static @Nullable PointerBuffer bufferOfASCIIs(
        @NotNull MemoryStack stack,
        @NotNull Collection<? extends CharSequence> strings) {
        if (strings.isEmpty()) {
            return null;
        } else {
            var buffer = stack.mallocPointer(strings.size());

            for (var string : strings) {
                buffer.put(stack.ASCII(string));
            }

            return buffer.flip();
        }
    }
}
