package org.randomcoders.economy.handlers;

import java.io.File;
import org.randomcoders.economy.core.EconomyMod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

public class HandlerConfig
{
	public static File worldDir = null;
	
	public static void LoadGlobalConfigs(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		config.save();
	}
	
	public static void LoadLocalConfigs()
	{
		MinecraftServer server = MinecraftServer.getServer();
		
		if(server.isServerRunning())
		{
			if(EconomyMod.proxy.isClient())
			{
				worldDir = server.getFile("saves/" + server.getFolderName());
			} else
			{
				worldDir = server.getFile(server.getFolderName());
			}
		} else
		{
			return;
		}
		
		Configuration config = new Configuration(new File(worldDir.getAbsolutePath(), "Economy.cfg"));
		
		config.load();
		config.save();
	}
}
