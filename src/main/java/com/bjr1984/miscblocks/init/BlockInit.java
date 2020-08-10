package com.bjr1984.miscblocks.init;

import com.bjr1984.miscblocks.block.ChannelBlock;
import com.bjr1984.miscblocks.block.DoubleTrapDoorBlock;
import com.bjr1984.miscblocks.MiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapDoorBlock;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    // Blocks
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MiscBlocks.MODID);

    public static final RegistryObject<TrapDoorBlock> OAK_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("oak_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.OAK_TRAPDOOR)));
    public static final RegistryObject<TrapDoorBlock> SPRUCE_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("spruce_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.SPRUCE_TRAPDOOR)));
    public static final RegistryObject<TrapDoorBlock> BIRCH_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("birch_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.BIRCH_TRAPDOOR)));
    public static final RegistryObject<TrapDoorBlock> JUNGLE_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("jungle_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.JUNGLE_TRAPDOOR)));
    public static final RegistryObject<TrapDoorBlock> ACACIA_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("acacia_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.ACACIA_TRAPDOOR)));
    public static final RegistryObject<TrapDoorBlock> DARK_OAK_DOUBLE_TRAPDOOR_BLOCK = BLOCKS.register("dark_oak_double_trapdoor", () -> new DoubleTrapDoorBlock(Block.Properties.from(Blocks.DARK_OAK_TRAPDOOR)));

    public static final RegistryObject<ChannelBlock> OAK_CHANNEL_BLOCK = BLOCKS.register("oak_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<ChannelBlock> SPRUCE_CHANNEL_BLOCK = BLOCKS.register("spruce_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<ChannelBlock> BIRCH_CHANNEL_BLOCK = BLOCKS.register("birch_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<ChannelBlock> JUNGLE_CHANNEL_BLOCK = BLOCKS.register("jungle_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<ChannelBlock> ACACIA_CHANNEL_BLOCK = BLOCKS.register("acacia_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<ChannelBlock> DARK_OAK_CHANNEL_BLOCK = BLOCKS.register("dark_oak_channel", () -> new ChannelBlock(Block.Properties.from(Blocks.DARK_OAK_PLANKS)));


}


