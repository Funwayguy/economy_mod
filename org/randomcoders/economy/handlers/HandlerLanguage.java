package org.randomcoders.economy.handlers;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerLanguage extends Handler
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		LanguageRegistry.instance().addStringLocalization("itemGroup.Economy", "en_US", "Economy");
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
	}
}
