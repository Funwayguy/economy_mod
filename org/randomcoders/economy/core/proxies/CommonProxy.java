package org.randomcoders.economy.core.proxies;

import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.*;

import com.nightfallstudios.alchemytrader.AlchemyTrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements IGuiHandler {

	public static HandlerBlocks handlerBlocks = new HandlerBlocks();
	public static HandlerConfig handlerConfig = new HandlerConfig();
	public static HandlerGui handlerGui = new HandlerGui();
	public static HandlerItems handlerItems = new HandlerItems();
	public static HandlerLanguage handlerLanguage = new HandlerLanguage();
	public static HandlerTicks handlerTicks = new HandlerTicks();
	public static HandlerTileEntities handlerTileEntities = new HandlerTileEntities();
	
	public static AchievementPage achievementPage = new AchievementPage(EconomyMod.modName);
	
	public void preInit(FMLPreInitializationEvent event) {
		//init modmetadata here
		
		handlerConfig.preInit(event);
		handlerBlocks.preInit(event);
		handlerItems.preInit(event);
		handlerTileEntities.preInit(event);
		handlerLanguage.preInit(event);
		handlerTicks.preInit(event);
		handlerGui.preInit(event);
	}
	
	public void init(FMLInitializationEvent event) {	
		handlerConfig.init(event);
		handlerBlocks.init(event);
		handlerItems.init(event);
		handlerTileEntities.init(event);
		handlerLanguage.init(event);
		handlerTicks.init(event);
		handlerGui.init(event);
		
		NetworkRegistry.instance().registerGuiHandler(EconomyMod.instance, this);	
	}
	
	public void postInit(FMLPostInitializationEvent event) {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
}
