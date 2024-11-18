package com.yungnickyoung.minecraft.ribbits.mixin.mixins.client.supporters;

import com.yungnickyoung.minecraft.ribbits.client.supporters.RibbitOptionsJSON;
import com.yungnickyoung.minecraft.ribbits.client.supporters.SupportersJSON;
import com.yungnickyoung.minecraft.ribbits.services.Services;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

@Mixin(SkinCustomizationScreen.class)
public abstract class SkinCustomizationScreenMixin extends OptionsSubScreen {

    /**
     * Used to track the number of widgets added to the screen instead of simply capturing locals,
     * for the sake of mod compat.
     */
    @Unique
    private int numWidgets;

    public SkinCustomizationScreenMixin(Screen lastScreen, Options options, Component titleComponent) {
        super(lastScreen, options, titleComponent);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void ribbits$initWidgetCounter(CallbackInfo ci) {
        numWidgets = 0;
    }

    /**
     * Increments the widget counter every time addRenderableWidget is called.
     */
    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/SkinCustomizationScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private void ribbits$countWidgets(CallbackInfo ci) {
        numWidgets++;
    }

    /**
     * Adds a toggle button for the supporter hat to the skin customization screen.
     */
    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/SkinCustomizationScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void ribbits$addSupporterHatToggleOption(CallbackInfo ci, int i) {
        // Null checks just to be safe
        if (this.minecraft == null) return;
        UUID playerUUID = this.minecraft.getUser().getProfileId();
        if (playerUUID == null) return;

        // Only show the supporter hat toggle button if the player is a supporter, or if in development environment
        if (!Services.PLATFORM.isDevelopmentEnvironment() && !SupportersJSON.get().isSupporter(playerUUID)) return;

        if (i == PlayerModelPart.values().length + 1) {
            this.addRenderableWidget(
                    CycleButton.onOffBuilder(RibbitOptionsJSON.get().isSupporterHatEnabled())
                            .create(
                                    this.width / 2 - 155 + i % 2 * 160,
                                    this.height / 6 + 24 * (i >> 1),
                                    150,
                                    20,
                                    Component.translatable("ribbits.options.supporter_hat_button"),
                                    (button, enabled) -> RibbitOptionsJSON.get().setSupporterHatEnabled(enabled)
                            )
            );
            i++;
        }
    }

    /**
     * Adjusts the position of the "Done" button to account for the supporter hat toggle button.
     */
    @Redirect(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/Button$Builder;bounds(IIII)Lnet/minecraft/client/gui/components/Button$Builder;"))
    private Button.Builder ribbits$adjustDoneButtonPosition(Button.Builder builder, int x, int y, int width, int height) {
        int widgetOffset = numWidgets;
        if (++widgetOffset % 2 == 1) {
            widgetOffset++;
        }
        return builder.bounds(this.width / 2 - 100, this.height / 6 + 24 * (widgetOffset >> 1), 200, 20);
    }
}
