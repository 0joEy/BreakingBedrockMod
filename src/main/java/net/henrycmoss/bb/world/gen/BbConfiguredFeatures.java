package net.henrycmoss.bb.world.gen;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.block.BbBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class BbConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>>
            SULFUR_ORE = key("sulfur_ore");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Bb.MODID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        RuleTest stoneOreReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> sulfurOre = List.of(
                OreConfiguration.target(stoneOreReplaceables, BbBlocks.SULFUR_ORE.get()
                        .defaultBlockState()),
                OreConfiguration.target(deepslateOreReplaceables, BbBlocks.SULFUR_ORE.get()
                        .defaultBlockState())
        );

        register(ctx, SULFUR_ORE, Feature.ORE, new OreConfiguration(sulfurOre, 12));
    }
}
