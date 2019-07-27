package me.shedaniel.rcc.mixin;

import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextFieldWidget.class)
public abstract class MixinTextFieldWidget extends AbstractButtonWidget {
    
    public MixinTextFieldWidget(int int_1, int int_2, String string_1) {
        super(int_1, int_2, string_1);
    }
    
    @Shadow
    public abstract boolean isVisible();
    
    @Shadow
    public abstract void setText(String string_1);
    
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(double double_1, double double_2, int int_1, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.isVisible()) {
            boolean boolean_1 = double_1 >= (double) this.x && double_1 < (double) (this.x + this.width) && double_2 >= (double) this.y && double_2 < (double) (this.y + this.height);
            if (int_1 == 1) {
                setText("");
            }
        }
    }
    
}
