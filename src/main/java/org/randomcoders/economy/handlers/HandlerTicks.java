package org.randomcoders.economy.handlers;

import java.util.EnumSet;
import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class HandlerTicks implements ITickHandler
{
	public static void RegisterTickHandlers()
	{
		TickRegistry.registerTickHandler(new HandlerTicks(), Side.SERVER);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		World world = (World)tickData[0];
		
		HandlerEconomy.UpdateDay(world);
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
	}
	
	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.WORLD);
	}
	
	@Override
	public String getLabel()
	{
		return "EconomyWorldTick";
	}
}
