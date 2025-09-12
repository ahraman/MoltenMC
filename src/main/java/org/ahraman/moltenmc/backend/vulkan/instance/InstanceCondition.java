package org.ahraman.moltenmc.backend.vulkan.instance;

import org.ahraman.moltenmc.backend.vulkan.util.ApiVersion;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public abstract class InstanceCondition {
    public abstract boolean test(@NotNull InstanceSettingProvider provider);

    abstract boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor);

    public static @NotNull InstanceCondition.Any any(@NotNull InstanceCondition... conditions) {
        return new InstanceCondition.Any(conditions);
    }

    public static @NotNull InstanceCondition.All all(@NotNull InstanceCondition... conditions) {
        return new InstanceCondition.All(conditions);
    }

    public static @NotNull InstanceCondition.Version version(@NotNull ApiVersion version) {
        return version(version, false);
    }

    public static @NotNull InstanceCondition.Version version(@NotNull ApiVersion version, boolean change) {
        return new InstanceCondition.Version(version, change);
    }

    public static @NotNull InstanceCondition.Layer layer(@NotNull String layer) {
        return new InstanceCondition.Layer(layer);
    }

    public static @NotNull InstanceCondition.Extension extension(@NotNull InstanceExtension extension) {
        return extension(extension, false);
    }

    public static @NotNull InstanceCondition.Extension extension(
        @NotNull InstanceExtension extension,
        boolean layerDependent) {
        return new InstanceCondition.Extension(extension, layerDependent);
    }

    public static @NotNull InstanceCondition.Setting setting(@NotNull InstanceSetting setting) {
        return new InstanceCondition.Setting(setting);
    }

    public static final class Any extends InstanceCondition {
        private final @NotNull InstanceCondition[] conditions;

        private Any(@NotNull InstanceCondition[] conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return Arrays.stream(this.conditions).anyMatch(cond -> cond.test(provider));
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            return Arrays.stream(this.conditions).anyMatch(cond -> cond.enable(provider, acceptor));
        }
    }

    public static final class All extends InstanceCondition {
        private final @NotNull InstanceCondition[] conditions;

        private All(@NotNull InstanceCondition[] conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return Arrays.stream(this.conditions).allMatch(cond -> cond.test(provider));
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            return Arrays.stream(this.conditions).allMatch(cond -> cond.enable(provider, acceptor));
        }
    }

    public static final class Version extends InstanceCondition {
        private final @NotNull ApiVersion version;
        private final boolean change;

        private Version(@NotNull ApiVersion version, boolean change) {
            this.version = version;
            this.change = change;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return provider.version().supports(this.version);
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            if (this.test(provider)) {
                if (this.change) {
                    acceptor.version(this.version);
                }

                return true;
            }

            return false;
        }
    }

    public static final class Layer extends InstanceCondition {
        private final @NotNull String layer;

        private Layer(@NotNull String layer) {
            this.layer = layer;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return provider.layers().contains(this.layer);
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            if (this.test(provider)) {
                acceptor.layers().add(this.layer);
                return true;
            }

            return false;
        }
    }

    public static final class Extension extends InstanceCondition {
        private final @NotNull InstanceExtension extension;
        private final boolean layerDependent;

        private Extension(@NotNull InstanceExtension extension, boolean layerDependent) {
            this.extension = extension;
            this.layerDependent = layerDependent;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return provider.extensions().contains(this.extension);
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            if (provider.extensions().contains(this.extension)) {
                acceptor.extensions().add(this.extension);
                return true;
            } else if ((this.layerDependent && acceptor.layers().stream().anyMatch(layer -> Objects.requireNonNull(
                provider.extensions(layer)).contains(this.extension)))) {
                acceptor.extensions().add(this.extension);
                return true;
            }

            return false;
        }
    }

    public static final class Setting extends InstanceCondition {
        private final @NotNull InstanceSetting setting;

        private Setting(@NotNull InstanceSetting setting) {
            this.setting = setting;
        }

        @Override
        public boolean test(@NotNull InstanceSettingProvider provider) {
            return this.setting.test(provider);
        }

        @Override
        boolean enable(@NotNull InstanceSettingProvider provider, @NotNull InstanceSettingAcceptor acceptor) {
            return this.setting.enable(provider, acceptor);
        }
    }
}
