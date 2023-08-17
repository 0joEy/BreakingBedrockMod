package net.henrycmoss.bb.events;

import com.mojang.logging.LogUtils;
import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.entity.BbEntities;
import net.henrycmoss.bb.entity.custom.HatManEntity;
import net.henrycmoss.bb.entity.custom.TntCannonEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = Bb.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BbModEvents {


        /*private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.BIOME, BbBiomesRegister::bootStrapBiomes);

        @SubscribeEvent
        public static void onGatherData(GatherDataEvent event)
        {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), BUILDER,
                    Set.of(Bb.MODID)));
        }
*/
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        LogUtils.getLogger().info("attribute event firing...");
        event.put(BbEntities.HAT_MAN.get(), HatManEntity.setAttributes().build());
        event.put(BbEntities.TNT_CANNON.get(), TntCannonEntity.setAttributes());
    }

}


        //private static final CustomBossEvent bar = new CustomBossEvent(new ResourceLocation("bsbar", Bb.MODID), Component.literal("test boss"));

        /*@SubscribeEvent
        public static void entityTickEvent(LivingEvent.LivingTickEvent event) {
            if (event.getEntity().getType() == EntityType.SLIME && event.getEntity().getTags().contains("testBoss")) {
                bar.setVisible(true);
                bar.setValue((int) event.getEntity().getHealth());
                bar.setColor(BossEvent.BossBarColor.RED);
                for (Player p : event.getEntity().level().players()) {
                    bar.addPlayer((ServerPlayer) p);
                }
            }*/

