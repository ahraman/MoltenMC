package org.ahraman.moltenmc.mixin.net.minecraft.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.ahraman.moltenmc.client.render.MoltenRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Unique
    private MoltenRenderer renderer;

    @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("RETURN"))
    private void constructor(RunArgs runArgs, CallbackInfo ci) {
        this.renderer = new MoltenRenderer();
    }

    @Inject(method = "close()V", at = @At("RETURN"))
    private void close(CallbackInfo ci) {
        this.renderer.cleanup();
        this.renderer = null;
    }
}
