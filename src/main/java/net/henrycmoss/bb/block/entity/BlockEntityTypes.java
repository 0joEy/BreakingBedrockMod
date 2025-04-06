package net.henrycmoss.bb.block.entity;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.block.BbBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES , Bb.MODID);

    public static final RegistryObject<BlockEntityType<JarBlockEntity>> JAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("jar_block_entity",
            () -> BlockEntityType.Builder.of(JarBlockEntity::new, BbBlocks.JAR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
