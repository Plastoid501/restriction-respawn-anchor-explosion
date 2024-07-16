package net.plastoid501.rrae.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.RespawnAnchorBlock.CHARGES;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void onInteractBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (player.isSneaking()) {
            return;
        }

        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = player.clientWorld.getBlockState(blockPos);

        if (!blockState.isOf(Blocks.RESPAWN_ANCHOR) || blockState.get(CHARGES) == 0) {
            return;
        }
        ItemStack itemStack = player.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND && !itemStack.isOf(Items.GLOWSTONE) && player.getStackInHand(Hand.OFF_HAND).isOf(Items.GLOWSTONE)) {
            return;
        }
        if (itemStack.isOf(Items.GLOWSTONE) && blockState.get(CHARGES) < 4) {
            return;
        }
        if (!player.clientWorld.getDimension().respawnAnchorWorks()) {
            cir.setReturnValue(ActionResult.CONSUME);
            cir.cancel();
            player.sendMessage(Text.of("Restriction RespawnAnchor Explosion"), true);
        }

    }

}
