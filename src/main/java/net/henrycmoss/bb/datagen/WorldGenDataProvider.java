package net.henrycmoss.bb.datagen;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.world.gen.BbBiomeModifiers;
import net.henrycmoss.bb.world.gen.BbConfiguredFeatures;
import net.henrycmoss.bb.world.gen.BbPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenDataProvider extends DatapackBuiltinEntriesProvider {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) BbConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, BbPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BbBiomeModifiers::bootstrap);

    public WorldGenDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Bb.MODID));
    }

    public static DataProvider.Factory<WorldGenDataProvider> makeFactory(CompletableFuture<HolderLookup.Provider> registries) {
        return output -> new WorldGenDataProvider(output, registries);
    }

    @Override
    public String getName() {
        return "Bb's Worldgen Data";
    }
}
