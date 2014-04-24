package org.randomcoders.economy.core;

import java.util.logging.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.randomcoders.economy.core.proxies.CommonProxy;
import org.randomcoders.economy.handlers.HandlerBlocks;
import org.randomcoders.economy.handlers.trading.HandlerTradeDB;
import org.randomcoders.economy.handlers.packets.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = EconomyMod.modID, name = EconomyMod.modName, version = EconomyMod.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {PacketHandler.channel}, packetHandler = PacketHandler.class)
public class EconomyMod
{
	@SidedProxy(clientSide = "org.randomcoders.economy.core.proxies.ClientProxy", serverSide = "org.randomcoders.economy.core.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static final String modID = "rc_economy";
	public static final String modName = "Economy Mod";
	public static final String modVersion = "1.0";
	
	public static EconomyMod instance;
	
	public static Logger logger;
	
	public static CreativeTabs economyTab = new CreativeTabs("Economy")
	{
		public ItemStack getIconItemStack()
		{
			return new ItemStack(HandlerBlocks.blockTrader, 1, 0);
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		logger = event.getModLog();
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
