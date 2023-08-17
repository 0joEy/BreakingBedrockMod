package net.henrycmoss.bb.entity;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.custom.HatManEntity;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BbEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Bb.MODID);

    public static final RegistryObject<EntityType<TntCannonEntity>> TNT_CANNON = ENTITY_TYPES.register("tnt_cannon",
            () -> EntityType.Builder.of(TntCannonEntity::new, MobCategory.MISC)
                    .sized(1f, 1f).build(new ResourceLocation(Bb.MODID, "tnt_cannon")
                            .toString()));

    public static final RegistryObject<EntityType<HatManEntity>> HAT_MAN = ENTITY_TYPES.register("hat_man",
            () -> EntityType.Builder.of(HatManEntity::new, MobCategory.MISC)
                    .sized(1f, 3f).build(new ResourceLocation(Bb.MODID, "hat_man").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
