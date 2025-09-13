package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.Vulkan;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.SequencedSet;

public class InstanceFactory {
    private final @NotNull InstanceDescriptor descriptor = new InstanceDescriptor();
    private final @NotNull SequencedSet<InstanceSetting> required = new LinkedHashSet<>();
    private final @NotNull SequencedSet<InstanceSetting> optional = new LinkedHashSet<>();

    InstanceFactory() {}

    public @NotNull InstanceFactory applicationInfo(@NotNull String name, int version) {
        this.descriptor.applicationInfo(name, version);
        return this;
    }

    public @NotNull InstanceFactory engineInfo(@NotNull String name, int version) {
        this.descriptor.engineInfo(name, version);
        return this;
    }

    public @NotNull InstanceFactory required(@NotNull InstanceSetting... settings) {
        this.required.addAll(Arrays.stream(settings).toList());
        return this;
    }

    public @NotNull InstanceFactory optional(@NotNull InstanceSetting... settings) {
        this.optional.addAll(Arrays.stream(settings).toList());
        return this;
    }

    public @NotNull Instance create() {
        var provider = Vulkan.getSupportedCapabilities();
        apply(this.required, provider, this.descriptor, true);
        apply(this.optional, provider, this.descriptor, false);

        return Instance.create(this.descriptor);
    }

    private static void apply(
        @NotNull SequencedSet<InstanceSetting> settings,
        @NotNull InstanceSettingProvider provider,
        @NotNull InstanceSettingAcceptor acceptor,
        boolean required) {
        for (var setting : settings) {
            if (!setting.enable(provider, acceptor) && required) {
                throw new IllegalStateException("unsupported instance setting: " + setting);
            }
        }
    }

    public @NotNull InstanceDescriptor descriptor() {
        return this.descriptor;
    }
}
