package org.randomcoders.economy.handlers;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class HandlerLanguage
{
	public static void LoadLocalisations()
	{
		LanguageRegistry.instance().addStringLocalization("itemGroup.Economy", "en_US", "Economy");
	}
}
