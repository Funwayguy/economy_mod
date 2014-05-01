package org.randomcoders.economy.handlers;

import org.randomcoders.economy.blocks.TileEntityPostbox;
import org.randomcoders.economy.blocks.TileEntityTrader;
import cpw.mods.fml.common.registry.GameRegistry;

public class HandlerTileEntities
{
	public static void RegisterTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityPostbox.class, "economy.tile.postbox");
		GameRegistry.registerTileEntity(TileEntityTrader.class, "economy.tile.trader");
	}
}
