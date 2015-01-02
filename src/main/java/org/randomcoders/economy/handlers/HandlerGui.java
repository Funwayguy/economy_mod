package org.randomcoders.economy.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.randomcoders.economy.blocks.TileEntityPostbox;
import org.randomcoders.economy.blocks.TileEntityTrader;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.inventory.ContainerPostbox;
import org.randomcoders.economy.inventory.ContainerTrader;
import org.randomcoders.economy.inventory.GuiPostbox;
import org.randomcoders.economy.inventory.GuiTrader;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class HandlerGui implements IGuiHandler
{
	public static void RegisterGuiHandlers()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(EconomyMod.instance, new HandlerGui());
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(id == 0 && tile != null)
		{
			return new ContainerTrader(player.inventory, (TileEntityTrader)tile);
		} else if(id == 1 && tile != null)
		{
			return new ContainerPostbox(player.inventory, (TileEntityPostbox)tile);
		} else
		{
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(ID == 0 && tile != null)
		{
			return new GuiTrader(player.inventory, (TileEntityTrader)tile);
			/*if(world.isRemote)
			{
				return new GuiTrader(player.inventory);
			} else
			{
				return new ContainerTrader(player.inventory);
			}*/
		} else if(ID == 1 && tile != null)
		{
			return new GuiPostbox(player.inventory, (TileEntityPostbox)tile);
		} else
		{
			return null;
		}
	}
}
