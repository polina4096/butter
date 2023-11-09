package polina4096.butter.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import polina4096.butter.ButterMod;

@Mixin(value = InGameHud.class, priority = -1)
public abstract class InGameHudMixin {
    @Shadow
    private int scaledWidth;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Final
    @Shadow
    private MinecraftClient client;

    @Unique
    private double position = 0.0;

    @ModifyArg(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                    ordinal = 1
            ),
            index = 1,
            method = "renderHotbar"
    )
    private int selectedSlotPositionX(int originalX) {
        if (!ButterMod.config.getButterEnabled()) return originalX;

        int i = this.scaledWidth / 2;
        PlayerEntity playerEntity = this.getCameraPlayer();

        assert playerEntity != null;
        int selectedSlot = playerEntity.getInventory().selectedSlot;

        // Calculate the distance between the current position and the target slot
        double distance = Math.abs(position - selectedSlot);

        // If the distance is 0, no need to animate
        if (distance == 0) {
            return (int) (i - 91 - 1 + Math.round(position * 20));
        }

        // Calculate the speed based on the desired animation duration
        // The speed is distance divided by duration (in ticks)
        // For example, if you want the animation to last for 1 second (20 ticks in Minecraft), use 20.0
        double duration = 2.0;
        double speed = distance / duration;

        if (position > selectedSlot) {
            position -= client.getTickDelta() * speed;
            if (position < selectedSlot) {
                position = selectedSlot;
            }
        } else if (position < selectedSlot) {
            position += client.getTickDelta() * speed;
            if (position > selectedSlot) {
                position = selectedSlot;
            }
        }

        return (int) (i - 91 - 1 + Math.round(position * 20));
    }
}