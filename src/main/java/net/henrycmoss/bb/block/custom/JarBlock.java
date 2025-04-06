package net.henrycmoss.bb.block.custom;

import net.henrycmoss.bb.block.entity.BlockEntityTypes;
import net.henrycmoss.bb.block.entity.JarBlockEntity;
import net.henrycmoss.bb.item.BbItems;
import net.henrycmoss.bb.util.BbTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class JarBlock extends BaseEntityBlock {
    public static final BooleanProperty FULL = BooleanProperty.create("full");
    public static final BooleanProperty FERMENTED = BooleanProperty.create("fermented");

    public static boolean e = false;

    private ItemStack fruit = ItemStack.EMPTY;
    public JarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FULL, false));
        this.registerDefaultState(this.defaultBlockState().setValue(FERMENTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FULL);
        pBuilder.add(FERMENTED);
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
                                 BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            pPlayer.sendSystemMessage(Component.literal(FACING.toString()));
            JarBlockEntity jarEntity = (JarBlockEntity) pLevel.getBlockEntity(pPos);
            ItemStack stack = pPlayer.getItemInHand(pHand);
            if (stack.is(BbTags.Items.FRUITS) && pState.getValue(FULL).equals(false)) {
                pLevel.setBlock(pPos, pState.setValue(FULL, true), 3);
                jarEntity.setResult(new ItemStack(stack.getItem(), 1));
                pPlayer.getInventory().removeItem(pPlayer.getInventory().selected, 1);
            } else if (pState.getValue(FULL) && !pPlayer.getItemInHand(pHand).is(BbTags.Items.FRUITS)) {
                if (!pPlayer.getInventory().add(new ItemStack(jarEntity.getResult().getItem(), 1))) {
                    pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(),
                            new ItemStack(jarEntity.getResult().getItem(), 1)));
                    pPlayer.sendSystemMessage(Component.literal(jarEntity.getResult().getDescriptionId()));
                }
                pLevel.setBlock(pPos, pState.setValue(FULL, false), 3);
                jarEntity.setResult(ItemStack.EMPTY);
                jarEntity.setAge(0);
            }
            if (jarEntity.getAge() >= 10) {
                pLevel.setBlock(pPos, pState.setValue(FERMENTED, true).setValue(FULL, true),
                        3);
                jarEntity.setResult(new ItemStack(BbItems.ALCOHOL_BOTTLE.get(), 1));
                pPlayer.sendSystemMessage(Component.literal(Integer.toString(jarEntity.getAge())));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void randomTick (BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom){
    }

    @Nullable
        @Override
    public BlockEntity newBlockEntity (BlockPos pPos, BlockState pState){
        return new JarBlockEntity(pPos, pState);
    }


    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    private static final VoxelShape SHAPE =
            Block.box(0, 0, 0, 16, 3, 16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
