package org.randomcoders.economy.handlers;

import org.randomcoders.economy.blocks.BlockPostbox;
import org.randomcoders.economy.blocks.BlockTrader;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerBlocks
{
	public static BlockTrader blockTrader = new BlockTrader(500, Material.iron);
	public static BlockPostbox blockPostbox = new BlockPostbox(501, Material.iron);
	
	public static void LoadBlocks()
	{
		GameRegistry.registerBlock(blockTrader, "economy.block.trader");
		GameRegistry.registerBlock(blockPostbox, "economy.block.postbox");
		
		LanguageRegistry.addName(blockTrader, "Trader");
		LanguageRegistry.addName(blockPostbox, "Postbox");
	}
}
