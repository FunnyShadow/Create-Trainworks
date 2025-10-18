package me.bluefunny.createtrainworks.forge.event;

import me.bluefunny.createtrainworks.detector.SmartStructureDetector;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "createtrainworks", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WrenchInteractionHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos clickedPos = event.getPos();
        ItemStack heldItem = player.getItemInHand(event.getHand());

        if (level.isClientSide()) {
            return;
        }

        if (!isWrench(heldItem)) {
            return;
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
    }

    private static boolean isWrench(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return false;
        }

        String itemId = itemStack.getItem().getDescriptionId();
        return itemId.contains("wrench");
    }
}
