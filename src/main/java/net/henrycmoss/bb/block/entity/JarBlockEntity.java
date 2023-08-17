package net.henrycmoss.bb.block.entity;

import net.henrycmoss.bb.block.custom.JarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class JarBlockEntity extends BlockEntity{

    private int age;
    private ItemStack fruit = ItemStack.EMPTY;
    public JarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypes.JAR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }



    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("bb.age", this.age);
        pTag.put("bb.storedItem", this.getResult().save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.age = pTag.getInt("bb.age");
        this.setResult(ItemStack.of(pTag.getCompound("bb.storedItem")));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, JarBlockEntity bEntity) {
        if(!level.isClientSide()) {
            if (bEntity.age >= 10) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                lightningBolt.setPos(new Vec3(pos.getX(), pos.getY(), pos.getZ()));
                level.addFreshEntity(lightningBolt);
                level.setBlock(pos, state.setValue(JarBlock.FERMENTED, true), 3);
            }
            if ((new Random().nextInt(0, 201) >= 197) && state.getValue(JarBlock.FULL) &&
                    !state.getValue(JarBlock.FERMENTED)) {
                bEntity.age++;
            }
            bEntity.serializeNBT().put("bb.storedItem", bEntity.getResult().save(new CompoundTag()));
        }
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public ItemStack getResult() {
        return this.fruit;
    }

    public void setResult(ItemStack stack) {
        this.fruit = stack;
    }
}
