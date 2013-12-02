package org.randomcoders.economy.handlers;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

public class HandlerConfig extends Handler {

	private static Configuration config;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		config.save();
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}
}
