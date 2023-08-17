package net.henrycmoss.bb.block;

import net.henrycmoss.bb.Bb;
import net.henrycmoss.bb.block.custom.CocaineTrayBlock;
import net.henrycmoss.bb.block.custom.EphedraCropBlock;
import net.henrycmoss.bb.block.custom.EtherBlock;
import net.henrycmoss.bb.block.custom.JarBlock;
import net.henrycmoss.bb.block.custom.fluid.BbFluids;
import net.henrycmoss.bb.item.BbItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BbBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Bb.MODID);


    public static final RegistryObject<Block> COCAINE_TRAY = registerBlock("cocaine_tray",
            () -> new CocaineTrayBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4f).requiresCorrectToolForDrops().noCollission()));

    public static final RegistryObject<Block> EPHEDRA_CROP = registerBlock("ephedra_crop",
            () -> new EphedraCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Block> ETHER = registerBlock("ether",
            () -> new EtherBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<LiquidBlock> ACID = registerBlock("acid",
            () -> new LiquidBlock(BbFluids.SOURCE_ACID, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<Block> JAR = registerBlock("jar",
            () -> new JarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(1f).noOcclusion()));

    public static final RegistryObject<Block> SULFUR_ORE = registerBlock("sulfur_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return BbItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
