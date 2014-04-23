package org.randomcoders.economy.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerTrader extends Container
{
	public GuiTrader trader;
	public InventoryPlayer playerInv;
	public int pageNum;
	public int x;
	public int y;
	
	public ContainerTrader(InventoryPlayer playerInvo)
	{
		this.playerInv = playerInvo;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}
	
	public void updatePage(int newPageNum, int newX, int newY)
	{
		this.inventorySlots.clear();
		this.pageNum = newPageNum;
		this.x = newX;
		this.y = newY;
		
		if(pageNum == 1)
		{
			for (int i = 0; i < 3; ++i)
	        {
	            for (int j = 0; j < 9; ++j)
	            {
	                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, x + 44 + j * 18, y + 133 + i * 18));
	            }
	        }
	
	        for (int i = 0; i < 9; ++i)
	        {
	            this.addSlotToContainer(new Slot(playerInv, i, x + 44 + i * 18, y + 191));
	        }
		}
	}
}
