package me.bluefunny.createtrainworks.detector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class BFSCollector {

    private static final int MAX_BLOCKS = 100;
    private static final int MAX_DEPTH = 10;

    public static Set<BlockPos> collectConnectedBlocks(Level level, BlockPos startPos, Predicate<BlockState> blockPredicate) {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> candidateBlocks = new HashSet<>();

        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty() && candidateBlocks.size() < MAX_BLOCKS) {
            BlockPos currentPos = queue.poll();

            if (Math.abs(currentPos.getX() - startPos.getX()) > MAX_DEPTH ||
                    Math.abs(currentPos.getY() - startPos.getY()) > MAX_DEPTH ||
                    Math.abs(currentPos.getZ() - startPos.getZ()) > MAX_DEPTH) {
                continue;
            }

            BlockState blockState = level.getBlockState(currentPos);

            if (blockPredicate.test(blockState)) {
                candidateBlocks.add(currentPos);

                for (Direction direction : Direction.values()) {
                    BlockPos neighborPos = currentPos.relative(direction);

                    if (!visited.contains(neighborPos)) {
                        visited.add(neighborPos);
                        queue.add(neighborPos);
                    }
                }
            }
        }

        return candidateBlocks;
    }

    public static Predicate<BlockState> createStructureBlockPredicate() {
        return blockState -> {
            String blockId = blockState.getBlock().getDescriptionId();

            return blockId.contains("analog_lever") ||
                    blockId.contains("brass_casing") ||
                    blockId.contains("lever") ||
                    blockId.contains("stressometer") ||
                    blockState.isAir();
        };
    }
}
