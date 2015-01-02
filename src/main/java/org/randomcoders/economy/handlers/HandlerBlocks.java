package org.randomcoders.economy.handlers;

import net.minecraft.block.material.Material;
import org.randomcoders.economy.blocks.BlockPostbox;
import org.randomcoders.economy.blocks.BlockTrader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerBlocks
{
	public static BlockTrader blockTrader = new BlockTrader(Material.iron);
	public static BlockPostbox blockPostbox = new BlockPostbox(Material.iron);
	
	public static void LoadBlocks()
	{
		GameRegistry.registerBlock(blockTrader, "block.trader");
		GameRegistry.registerBlock(blockPostbox, "block.postbox");
		
		LanguageRegistry.addName(blockTrader, "Trader");
		LanguageRegistry.addName(blockPostbox, "Postbox");
	}
}
