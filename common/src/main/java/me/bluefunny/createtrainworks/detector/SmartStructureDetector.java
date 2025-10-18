package me.bluefunny.createtrainworks.detector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.Set;

public class SmartStructureDetector {

    public static DetectionResult detectStructure(Level level, BlockPos clickedPos) {
        Set<BlockPos> candidateBlocks = BFSCollector.collectConnectedBlocks(
                level,
                clickedPos,
                BFSCollector.createStructureBlockPredicate()
        );

        if (candidateBlocks.size() < 4) {
            return DetectionResult.failure("Structure incomplete or incorrect (too few blocks)");
        }

        BlockPattern pattern = PatternDefinitions.getLShapePattern();

        for (BlockPos candidateOrigin : candidateBlocks) {
            for (Direction forwards : Direction.values()) {
                for (Direction up : Direction.values()) {
                    if (up != forwards && up != forwards.getOpposite()) {
                        BlockPattern.BlockPatternMatch match = pattern.matches(level, candidateOrigin, forwards, up);
                        if (match != null) {
                            return DetectionResult.success(
                                    match.getFrontTopLeft(),
                                    match.getForwards(),
                                    match.getUp()
                            );
                        }
                    }
                }
            }
        }

        return DetectionResult.failure("Structure incomplete or incorrect");
    }

    public static class DetectionResult {
        public final boolean found;
        public final BlockPos origin;
        public final Direction forwards;
        public final Direction up;
        public final String message;

        public DetectionResult(boolean found, BlockPos origin, Direction forwards, Direction up, String message) {
            this.found = found;
            this.origin = origin;
            this.forwards = forwards;
            this.up = up;
            this.message = message;
        }

        public static DetectionResult success(BlockPos origin, Direction forwards, Direction up) {
            return new DetectionResult(true, origin, forwards, up,
                    "Valid structure detected! Orientation: forwards=" + forwards + ", up=" + up);
        }

        public static DetectionResult failure(String reason) {
            return new DetectionResult(false, null, null, null, reason);
        }
    }
}
