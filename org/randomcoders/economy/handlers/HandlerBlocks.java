package org.randomcoders.economy.handlers;

import org.randomcoders.economy.blocks.BlockTrader;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerBlocks extends Handler
{
	public static BlockTrader blockTrader = new BlockTrader(500, Material.iron);
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		GameRegistry.registerBlock(blockTrader, "economy.block.trader");
		
		LanguageRegistry.addName(blockTrader, "Trader");
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
	}
}
