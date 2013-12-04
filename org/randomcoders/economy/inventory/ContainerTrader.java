package org.randomcoders.economy.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerTrader extends Container
{
	public ContainerTrader(InventoryPlayer playerInvo)
	{
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
}
