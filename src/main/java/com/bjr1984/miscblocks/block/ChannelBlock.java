package com.bjr1984.miscblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class ChannelBlock extends GlazedTerracottaBlock {

    protected static final VoxelShape NORTHF = Stream.of( Block.makeCuboidShape(15, 0, 0, 16, 16, 15), Block.makeCuboidShape(0, 0, 0, 1, 16, 15),
            Block.makeCuboidShape(0, 0, 15, 16, 16, 16)).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    protected static final VoxelShape SOUTHF = Stream.of(Block.makeCuboidShape(0, 0, 1, 1, 16, 16), Block.makeCuboidShape(15, 0, 1, 16, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 16, 1)).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    protected static final VoxelShape EASTF = Stream.of(Block.makeCuboidShape(1, 0, 15, 16, 16, 16), Block.makeCuboidShape(1, 0, 0, 16, 16, 1),
            Block.makeCuboidShape(0, 0, 0, 1, 16, 16)).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    protected static final VoxelShape WESTF = Stream.of( Block.makeCuboidShape(0, 0, 0, 15, 16, 1), Block.makeCuboidShape(0, 0, 15, 15, 16, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 16, 16)).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public ChannelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        switch(state.get(HORIZONTAL_FACING)) {
                case EAST:
                    return EASTF;
                case WEST:
                    return WESTF;
                case NORTH:
                    return NORTHF;
                case SOUTH:
                    return SOUTHF;
                default:
                    return NORTHF;
            }
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }
}