package org.randomcoders.economy.handlers;

import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.inventory.ContainerTrader;
import org.randomcoders.economy.inventory.GuiTrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class HandlerGui implements IGuiHandler
{
	public static void RegisterGuiHandlers()
	{
		NetworkRegistry.instance().registerGuiHandler(EconomyMod.instance, new HandlerGui());
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if(id == 0)
		{
			return new ContainerTrader(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			return new GuiTrader(player.inventory);
		}
		return null;
	}
}
