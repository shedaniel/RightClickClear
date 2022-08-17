package me.shedaniel.rcc.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextFieldWidget.class)
public abstract class MixinTextFieldWidget extends ClickableWidget {
    
    public MixinTextFieldWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }
    
    @Shadow
    public abstract boolean isVisible();
    
    @Shadow
    public abstract void setText(String string_1);
    
    @Shadow @Final private TextRenderer textRenderer;
    
    @Shadow
    public abstract void setCursor(int cursor);
    
    @Shadow private int firstCharacterIndex;
    
    @Shadow private String text;
    
    @Shadow
    public abstract int getInnerWidth();
    
    @Shadow private boolean focusUnlocked;
    
    @Shadow
    public abstract void setTextFieldFocused(boolean selected);
    
    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.isVisible()) {
            boolean hovered = mouseX >= this.x && mouseX < (this.x + this.width) && mouseY >= this.y && mouseY < (this.y + this.height);
            if (hovered && button == 1) {
                setText("");
                if (this.focusUnlocked) {
                    this.setTextFieldFocused(true);
                }
                
                if (this.isFocused()) {
                    int i = MathHelper.floor(mouseX) - this.x - 4;
                    
                    String string = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
                    this.setCursor(this.textRenderer.trimToWidth(string, i).length() + this.firstCharacterIndex);
                    callbackInfo.setReturnValue(true);
                }
            }
        }
    }
    
}
