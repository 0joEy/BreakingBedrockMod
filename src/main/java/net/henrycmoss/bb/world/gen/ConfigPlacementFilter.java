package net.henrycmoss.bb.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ConfigPlacementFilter extends PlacementFilter {

    public static final ConfigPlacementFilter INSTANCE = new ConfigPlacementFilter();
    public static final Codec<ConfigPlacementFilter> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return true;
    }

    @Override
    public PlacementModifierType<?> type() {
       return BbPlacementModifiers.CONFIG_FILTER.get();
    }
}
