package org.randomcoders.economy.core.proxies;

import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.*;
import org.randomcoders.economy.handlers.packets.PacketHandler;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public static AchievementPage achievementPage = new AchievementPage(EconomyMod.modName);
	
	public boolean isClient()
	{
		return false;
	}
	
	public void preInit(FMLPreInitializationEvent event)
	{
		HandlerConfig.LoadGlobalConfigs(event);
		HandlerBlocks.LoadBlocks();
		HandlerItems.LoadItems();
		HandlerTileEntities.RegisterTileEntities();
		HandlerLanguage.LoadLocalisations();
		MinecraftForge.EVENT_BUS.register(new HandlerEvents());
	}
	
	public void init(FMLInitializationEvent event)
	{
		HandlerTicks.RegisterTickHandlers();
		HandlerGui.RegisterGuiHandlers();
		PacketHandler.RegisterHandlers();
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
