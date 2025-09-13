package org.ahraman.moltenmc.backend.vulkan.instance;

import com.google.common.collect.ImmutableSet;
import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.ahraman.moltenmc.backend.vulkan.util.VulkanUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkInstanceCreateInfo;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.Set;

import static org.lwjgl.vulkan.KHRPortabilityEnumeration.*;

public class InstanceDescriptor implements InstanceSettingAcceptor {
    private @NotNull ApiVersion version = ApiVersion.VULKAN_1_0;
    private final @NotNull SequencedSet<String> layers = new LinkedHashSet<>();
    private final @NotNull Set<InstanceExtension> extensions = EnumSet.noneOf(InstanceExtension.class);

    private @NotNull String applicationName = "application";
    private int applicationVersion = 0;
    private @NotNull String engineName = "engine";
    private int engineVersion = 0;

    public InstanceDescriptor() {}

    public @NotNull ApiVersion version() {
        return this.version;
    }

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

    public void applicationInfo(@NotNull String name, int version) {
        this.applicationName = name;
        this.applicationVersion = version;
    }

    public @NotNull String applicationName() {
        return this.applicationName;
    }

    public int applicationVersion() {
        return this.applicationVersion;
    }

    public void engineInfo(@NotNull String name, int version) {
        this.engineName = name;
        this.engineVersion = version;
    }

    public @NotNull String engineName() {
        return this.engineName;
    }

    public int engineVersion() {
        return this.engineVersion;
    }

    public @NotNull InstanceCapabilities getCapabilities() {
        return new InstanceCapabilities(this.version(),
                                        ImmutableSet.copyOf(this.layers()),
                                        ImmutableSet.copyOf(this.extensions()));
    }

    public @NotNull InstanceInfo getInfo() {
        return new InstanceInfo(this.applicationName(),
                                this.applicationVersion(),
                                this.engineName(),
                                this.engineVersion());
    }

    public @NotNull VkInstanceCreateInfo getCreateInfo(@NotNull MemoryStack stack) {
        var applicationInfo = this.getApplicationInfo(stack);
        var createInfo = VkInstanceCreateInfo.calloc(stack)
                                             .sType$Default()
                                             .pApplicationInfo(applicationInfo)
                                             .flags(this.getCreateFlags());

        var layers = VulkanUtils.bufferOfASCIIs(stack, this.layers());
        if (layers != null) {
            createInfo.ppEnabledLayerNames(layers);
        }

        var extensions = VulkanUtils.bufferOfASCIIs(stack,
                                                    this.extensions().stream().map(InstanceExtension::name).toList());
        if (extensions != null) {
            createInfo.ppEnabledExtensionNames(extensions);
        }

        return createInfo;
    }

    public int getCreateFlags() {
        int flag = 0;
        if (this.extensions().contains(InstanceExtension.VK_KHR_portability_enumeration)) {
            // Maybe make this optional, rather than mandatory whenever the extension is enabled. But then again, who
            // wants to enable `VK_KHR_portability_enumeration` if they DON'T want to additionally enumerate
            // nonconforming implementations?
            flag |= VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR;
        }

        return flag;
    }

    public @NotNull VkApplicationInfo getApplicationInfo(@NotNull MemoryStack stack) {
        return VkApplicationInfo.calloc(stack)
                                .sType$Default()
                                .pApplicationName(stack.UTF8(this.applicationName()))
                                .applicationVersion(this.applicationVersion())
                                .pEngineName(stack.UTF8(this.engineName()))
                                .engineVersion(this.engineVersion())
                                .apiVersion(this.version().value());
    }
}
