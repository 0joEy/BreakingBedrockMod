package net.henrycmoss.bb;

import com.mojang.logging.LogUtils;
import net.henrycmoss.bb.block.BbBlocks;
import net.henrycmoss.bb.block.custom.fluid.BbFluidTypes;
import net.henrycmoss.bb.block.custom.fluid.BbFluids;
import net.henrycmoss.bb.block.entity.BlockEntityTypes;
import net.henrycmoss.bb.datagen.WorldGenDataProvider;
import net.henrycmoss.bb.effect.BbEffects;
import net.henrycmoss.bb.entity.BbEntities;
import net.henrycmoss.bb.entity.client.HatManRenderer;
import net.henrycmoss.bb.entity.client.TntCannonRenderer;
import net.henrycmoss.bb.item.BbItems;
import net.henrycmoss.bb.tab.BbCreativeModeTabs;
import net.henrycmoss.bb.villager.BbVillagers;
import net.henrycmoss.bb.world.gen.BbPlacementModifiers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Bb.MODID)
public class Bb {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "bb";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Bb() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(EventPriority.LOW, Bb::gatherData);

        BbItems.register(modEventBus);
        BbBlocks.register(modEventBus);
        BbCreativeModeTabs.register(modEventBus);
        BbVillagers.register(modEventBus);
        BbEntities.register(modEventBus);
        BlockEntityTypes.register(modEventBus);
        BbEffects.register(modEventBus);
        BbFluids.register(modEventBus);
        BbFluidTypes.register(modEventBus);
        BbPlacementModifiers.register(modEventBus);
        //BbFeatures.register(modEventBus);
        //BbPlacementModifiers.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == BbCreativeModeTabs.BB.getKey()) {
            event.accept(BbItems.METHAMPHETAMINE);
            event.accept(BbItems.MARIJUANA);
            event.accept(BbItems.UNLIT_JOINT);
            event.accept(BbItems.JOINT);
            event.accept(BbItems.EPHEDRA_SEEDS);
            event.accept(BbItems.EPHEDRA_STEM);
            event.accept(BbItems.PLASTIC_BAG);
            event.accept(BbItems.ACID_BUCKET);
            event.accept(BbItems.TNT_CANNON);
            event.accept(BbItems.ALCOHOL_BOTTLE);
            event.accept(BbBlocks.COCAINE_TRAY);
            event.accept(BbBlocks.ETHER);
        }
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

        if (event.includeServer()) {
            gen.addProvider(true, WorldGenDataProvider.makeFactory(event.getLookupProvider()));
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(BbBlocks.EPHEDRA_CROP.get(), RenderType.cutout());
            EntityRenderers.register(BbEntities.TNT_CANNON.get(), TntCannonRenderer::new);
            EntityRenderers.register(BbEntities.HAT_MAN.get(), HatManRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(BbFluids.SOURCE_ACID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(BbFluids.FLOWING_ACID.get(), RenderType.translucent());
        }
    }
}
