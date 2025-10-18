package me.bluefunny.createtrainworks.fabric.event;

import me.bluefunny.createtrainworks.detector.SmartStructureDetector;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class WrenchInteractionHandler {

    public static void register() {
        UseBlockCallback.EVENT.register(WrenchInteractionHandler::onUseBlock);
    }

    private static InteractionResult onUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos clickedPos = hitResult.getBlockPos();
        ItemStack heldItem = player.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        if (!isWrench(heldItem)) {
            return InteractionResult.PASS;
        }

        SmartStructureDetector.DetectionResult result = SmartStructureDetector.detectStructure(level, clickedPos);

        if (result.found) {
            player.displayClientMessage(
                Component.literal(result.message)
                    .withStyle(ChatFormatting.GREEN),
                false
            );
        } else {
            player.displayClientMessage(
                Component.literal(result.message)
                    .withStyle(ChatFormatting.RED),
                false
            );
        }

        return InteractionResult.PASS;
    }

    private static boolean isWrench(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return false;
        }

        String itemId = itemStack.getItem().getDescriptionId();
        return itemId.contains("wrench");
    }
}
