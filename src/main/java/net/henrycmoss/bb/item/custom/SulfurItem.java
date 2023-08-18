package net.henrycmoss.bb.item.custom;

import com.mojang.logging.LogUtils;
import net.henrycmoss.bb.block.BbBlocks;
import net.henrycmoss.bb.block.custom.fluid.BbFluidTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidType;

public class SulfurItem extends Item {

    public SulfurItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.isInFluidType(Fluids.WATER.getFluidType())) {
            Level level = entity.level();
            level.setBlock(entity.blockPosition(), BbBlocks.ACID.get().defaultBlockState(), 3);
            LogUtils.getLogger().info("touched");
            entity.kill();
        }

        return super.onEntityItemUpdate(stack, entity);
    }
}
