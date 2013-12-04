package org.randomcoders.economy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

public class BlockTrader extends Block
{
	public BlockTrader(int id, Material material)
	{
		super(id, material);
		setHardness(5);
		setStepSound(Block.soundMetalFootstep);
		setUnlocalizedName("economy.block.trader");
		setTextureName("economy:block_place_holder");
		setCreativeTab(CreativeTabs.tabRedstone);
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0);
	}
}
