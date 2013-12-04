package org.randomcoders.economy.handlers;

import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.inventory.ContainerTrader;
import org.randomcoders.economy.inventory.GuiTrader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class HandlerGui extends Handler implements IGuiHandler
{
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		NetworkRegistry.instance().registerGuiHandler(EconomyMod.instance, new HandlerGui());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		int blockID = world.getBlockId(x, y, z);
		if(blockID == HandlerBlocks.blockTrader.blockID)
		{
			return new ContainerTrader(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		int blockID = world.getBlockId(x, y, z);
		if(blockID == HandlerBlocks.blockTrader.blockID)
		{
			return new GuiTrader(player.inventory);
		}
		return null;
	}
}
