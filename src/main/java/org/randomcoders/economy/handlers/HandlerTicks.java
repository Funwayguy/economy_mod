package org.randomcoders.economy.handlers;

import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class HandlerTicks
{
	public static void RegisterTickHandlers()
	{
		FMLCommonHandler.instance().bus().register(new HandlerTicks());
	}
	
	@SubscribeEvent
	public void tickStart(TickEvent.WorldTickEvent tick)
	{
		if(tick.side == Side.SERVER)
		{
			HandlerEconomy.UpdateDay(tick.world);
		}
	}
}
