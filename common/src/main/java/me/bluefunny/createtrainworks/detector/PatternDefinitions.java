package me.bluefunny.createtrainworks.detector;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;

public class PatternDefinitions {

    public static BlockPattern getLShapePattern() {
        return BlockPatternBuilder.start()
                .aisle(
                        "...",
                        "BBS",
                        "..."
                )
                .aisle(
                        "...",
                        "LAS",
                        "..."
                )
                .where('.', blockInWorld -> blockInWorld.getState().isAir())
                .where('A', blockInWorld -> isAnalogLever(blockInWorld.getState()))
                .where('B', blockInWorld -> isBrassCasing(blockInWorld.getState()))
                .where('L', blockInWorld -> isLever(blockInWorld.getState()))
                .where('S', blockInWorld -> isStressometer(blockInWorld.getState()))
                .build();
    }

    private static boolean isAnalogLever(BlockState state) {
        return state.getBlock().getDescriptionId().contains("analog_lever");
    }

    private static boolean isBrassCasing(BlockState state) {
        return state.getBlock().getDescriptionId().contains("brass_casing");
    }


    private static boolean isLever(BlockState state) {
        return state.getBlock().getDescriptionId().contains("lever");
    }

    private static boolean isStressometer(BlockState state) {
        return state.getBlock().getDescriptionId().contains("stressometer");
    }
}
