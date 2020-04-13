package com.bjr1984.miscblocks;

import com.bjr1984.miscblocks.block.ChannelBlock;
import com.bjr1984.miscblocks.init.BlockInit;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("miscblocks")
@Mod.EventBusSubscriber(modid = MiscBlocks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscBlocks
    {
        private static final Logger LOGGER = LogManager.getLogger();
        public static final String MODID = "miscblocks";
        public static MiscBlocks instance;

    public MiscBlocks() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        BlockInit.BLOCKS.register(modEventBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

        private void setup(final FMLCommonSetupEvent event) { }

        private void doClientStuff(final FMLClientSetupEvent event) {
            RenderTypeLookup.setRenderLayer(BlockInit.OAK_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockInit.BIRCH_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockInit.SPRUCE_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockInit.ACACIA_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockInit.JUNGLE_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockInit.DARK_OAK_DOUBLE_TRAPDOOR_BLOCK.get(), RenderType.getCutout());
        }

        @SubscribeEvent
        public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = event.getRegistry();

            BlockInit.BLOCKS.getEntries().stream().filter(block -> (block.get() instanceof TrapDoorBlock))
                    .map(RegistryObject::get).forEach(block -> {
                final Item.Properties properties = new Item.Properties().group(ItemGroup.REDSTONE);
                final BlockItem blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });

            BlockInit.BLOCKS.getEntries().stream().filter(block -> (block.get() instanceof ChannelBlock))
                    .map(RegistryObject::get).forEach(block -> {
                final Item.Properties properties = new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
                final BlockItem blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });

            LOGGER.debug("Registered BlockItems!");
        }
}