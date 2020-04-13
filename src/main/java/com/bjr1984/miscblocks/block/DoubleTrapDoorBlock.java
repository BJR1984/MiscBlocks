package com.bjr1984.miscblocks.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DoubleTrapDoorBlock extends TrapDoorBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape BOTTOM_OPEN_N_S = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13, 0, 0, 16, 8, 16), Block.makeCuboidShape(0, 0, 0, 3, 8, 16), IBooleanFunction.OR);
    protected static final VoxelShape BOTTOM_OPEN_E_W = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 0, 0, 16, 8, 3), Block.makeCuboidShape(0, 0, 13, 16, 8, 16), IBooleanFunction.OR);
    protected static final VoxelShape TOP_OPEN_N_S = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(13, 8, 0, 16, 16, 16), Block.makeCuboidShape(0, 8, 0, 3, 16, 16), IBooleanFunction.OR);
    protected static final VoxelShape TOP_OPEN_E_W = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(0, 8, 0, 16, 16, 3), Block.makeCuboidShape(0, 8, 13, 16, 16, 16), IBooleanFunction.OR);
    protected static final VoxelShape BOTTOM_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.makeCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);


    public DoubleTrapDoorBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(OPEN, Boolean.valueOf(false)).with(HALF, Half.BOTTOM).with(POWERED, Boolean.valueOf(false)).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        if (!state.get(OPEN)) {
            return state.get(HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
        } else {
            switch(state.get(HORIZONTAL_FACING)) {
                case EAST:
                case WEST:
                    if (state.get(HALF) == Half.TOP) {
                        return TOP_OPEN_E_W;
                    } else {
                        return BOTTOM_OPEN_E_W;
                    }
                case NORTH:
                case SOUTH:
                    if (state.get(HALF) == Half.TOP) {
                        return TOP_OPEN_N_S;
                    } else {
                        return BOTTOM_OPEN_N_S;
                    }
                default:
                    return BOTTOM_OPEN_N_S;
            }
        }
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch(type) {
            case LAND:
                return state.get(OPEN);
            case WATER:
                return state.get(WATERLOGGED);
            case AIR:
                return state.get(OPEN);
            default:
                return false;
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (this.material == Material.IRON) {
            return ActionResultType.PASS;
        } else {
            state = state.cycle(OPEN);
            worldIn.setBlockState(pos, state, 2);
            if (state.get(WATERLOGGED)) {
                worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }

            this.playSound(player, worldIn, pos, state.get(OPEN));
            return ActionResultType.SUCCESS;
        }
    }

    protected void playSound(@Nullable PlayerEntity player, World worldIn, BlockPos pos, boolean p_185731_4_) {
        if (p_185731_4_) {
            int i = this.material == Material.IRON ? 1037 : 1007;
            worldIn.playEvent(player, i, pos, 0);
        } else {
            int j = this.material == Material.IRON ? 1036 : 1013;
            worldIn.playEvent(player, j, pos, 0);
        }

    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = worldIn.isBlockPowered(pos);
            if (flag != state.get(POWERED)) {
                if (state.get(OPEN) != flag) {
                    state = state.with(OPEN, Boolean.valueOf(flag));
                    this.playSound((PlayerEntity)null, worldIn, pos, flag);
                }

                worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(flag)), 2);
                if (state.get(WATERLOGGED)) {
                    worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
                }
            }

        }
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = this.getDefaultState();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction direction = context.getFace();
        if (!context.replacingClickedOnBlock() && direction.getAxis().isHorizontal()) {
            blockstate = blockstate.with(HORIZONTAL_FACING, direction).with(HALF, context.getHitVec().y - (double)context.getPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM);
        } else {
            blockstate = blockstate.with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
        }

        if (context.getWorld().isBlockPowered(context.getPos())) {
            blockstate = blockstate.with(OPEN, Boolean.valueOf(true)).with(POWERED, Boolean.valueOf(true));
        }

        return blockstate.with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN, HALF, POWERED, WATERLOGGED);
    }

    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isLadder(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, net.minecraft.entity.LivingEntity entity) {
        if (state.get(OPEN)) {
            BlockState down = world.getBlockState(pos.down());
            if (down.getBlock() == net.minecraft.block.Blocks.LADDER)
                return down.get(LadderBlock.FACING) == state.get(HORIZONTAL_FACING);
        }
        return false;
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }
}