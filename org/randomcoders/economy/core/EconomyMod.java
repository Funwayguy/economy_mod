package org.randomcoders.economy.core;

import java.util.logging.Logger;

import org.randomcoders.economy.core.proxies.CommonProxy;
import org.randomcoders.economy.handlers.trading.HandlerTradeDB;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = EconomyMod.modID, name = EconomyMod.modName, version = EconomyMod.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EconomyMod {
	public static final String modID = "rc_economy";
	public static final String modName = "Economy Mod";
	public static final String modVersion = "1.0";
	
	public static EconomyMod instance;
	
	public static Logger logger;
	
	@SidedProxy(clientSide = "org.randomcoders.economy.core.proxies.ClientProxy", serverSide = "org.randomcoders.economy.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
		logger = event.getModLog();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		
		//if(!proxy.isClient())
		{
			HandlerTradeDB.seedWithRandomTrades();
		}
	}
}
