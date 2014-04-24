package org.randomcoders.economy.handlers;

import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class HandlerEvents
{
	@ForgeSubscribe
	public void OnWorldLoad(Load event)
	{
		if(!event.world.isRemote && HandlerConfig.worldDir == null)
		{
			HandlerConfig.LoadLocalConfigs();
			HandlerEconomy.LoadDB();
		}
	}
	
	@ForgeSubscribe
	public void OnWorldLoad(Unload event)
	{
		if(!event.world.isRemote)
		{
			MinecraftServer server = MinecraftServer.getServer();
			
			if(!server.isServerRunning())
			{
				HandlerEconomy.SaveDB();
				HandlerConfig.worldDir = null;
			}
		}
	}
}
