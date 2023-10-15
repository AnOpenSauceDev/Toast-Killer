package com.github.anopensaucedev.toastkiller;

import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public class ToastKiller {

    @Inject(method = "draw",at = @At("HEAD"),cancellable = true)
    public void killToasts(CallbackInfo ci){
        //Debug.Log("Killed a toast that tried to draw");
        ci.cancel();
    }

    /**
     * @author AnOpenSauceDev
     * @reason forcefully overwrite toast behaviour
     */
    @Overwrite
    public void add(Toast toast){
        return;
    }


    @Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
    static class Entry<T extends Toast> { // inner class of ToastManager

        /**
         * @author AnOpenSauceDev
         * @reason prevent toasts rendering on the client
         */
        @Overwrite
        public boolean draw(int x, int y) { // lie about drawing
            return true;
        }
    }

}
