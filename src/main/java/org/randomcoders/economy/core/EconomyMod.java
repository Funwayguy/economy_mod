package org.randomcoders.economy.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Logger;
import org.randomcoders.economy.core.proxies.CommonProxy;
import org.randomcoders.economy.handlers.HandlerBlocks;
import org.randomcoders.economy.handlers.trading.HandlerTradeDB;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = EconomyMod.modID, name = EconomyMod.modName, version = EconomyMod.modVersion)
public class EconomyMod
{
	@SidedProxy(clientSide = "org.randomcoders.economy.core.proxies.ClientProxy", serverSide = "org.randomcoders.economy.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static final String modID = "rc_economy";
	public static final String modName = "Economy Mod";
	public static final String modVersion = "1.0";
	public static final String modChannel = "ECO_NET_CH";
	
	public static EconomyMod instance;
	
	public SimpleNetworkWrapper network;
	
	public static Logger logger;
	
	public static CreativeTabs economyTab = new CreativeTabs("Economy")
	{
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(HandlerBlocks.blockTrader);
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		logger = event.getModLog();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(modChannel);
		proxy.preInit(event);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
		
		//if(!proxy.isClient())
		{
			HandlerTradeDB.seedWithRandomTrades();
			
		}
	}
}
