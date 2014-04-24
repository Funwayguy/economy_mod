package org.randomcoders.economy.handlers;

import org.randomcoders.economy.blocks.BlockTrader;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerBlocks
{
	public static BlockTrader blockTrader = new BlockTrader(500, Material.iron);
	
	public static void LoadBlocks()
	{
		GameRegistry.registerBlock(blockTrader, "economy.block.trader");
		
		LanguageRegistry.addName(blockTrader, "Trader");
	}
}
