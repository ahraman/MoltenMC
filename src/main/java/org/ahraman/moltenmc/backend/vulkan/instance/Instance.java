package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.util.Results;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkInstance;

import static org.lwjgl.vulkan.VK10.*;

public final class Instance {
    private final @NotNull VkInstance handle;
    private final @NotNull InstanceCapabilities capabilities;
    private final @NotNull InstanceInfo info;

    public static @NotNull InstanceFactory factory() {
        return new InstanceFactory();
    }

    public Instance(
        @NotNull VkInstance handle,
        @NotNull InstanceCapabilities capabilities,
        @NotNull InstanceInfo info) {
        this.handle = handle;
        this.capabilities = capabilities;
        this.info = info;
    }

    static @NotNull Instance create(@NotNull InstanceDescriptor descriptor) {
        try (var stack = MemoryStack.stackPush()) {
            var createInfo = descriptor.getCreateInfo(stack);

            var buffer = stack.mallocPointer(1);
            Results.checked(vkCreateInstance(createInfo, null, buffer), "create instance");

            long handle = buffer.get(0);
            return new Instance(new VkInstance(handle, createInfo), descriptor.getCapabilities(), descriptor.getInfo());
        }
    }

    public void destroy() {
        destroyHandle(this.handle);
    }

    private static void destroyHandle(@NotNull VkInstance handle) {
        vkDestroyInstance(handle, null);
    }

    public @NotNull VkInstance handle() {
        return this.handle;
    }

    public @NotNull InstanceCapabilities capabilities() {
        return this.capabilities;
    }

    public @NotNull InstanceInfo info() {
        return this.info;
    }
}
