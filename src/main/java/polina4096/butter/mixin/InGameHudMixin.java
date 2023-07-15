package polina4096.butter.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import polina4096.butter.ButterConfig;
import polina4096.butter.ButterMod;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Shadow
	private int scaledWidth;

	@Shadow
	private PlayerEntity getCameraPlayer() { return null; }

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
		double speed = ButterMod.config.getGlideSpeed();

		int i = this.scaledWidth / 2;
		PlayerEntity playerEntity = this.getCameraPlayer();

		assert playerEntity != null;
		int selectedSlot = playerEntity.getInventory().selectedSlot;

		if (position > selectedSlot + speed) {
			position -= client.getTickDelta() * speed;
		} else if (position < selectedSlot - speed) {
			position += client.getTickDelta() * speed;
		} else {
			position = selectedSlot;
		}

		return (int) (i - 91 - 1 + Math.round(position * 20));
	}
}