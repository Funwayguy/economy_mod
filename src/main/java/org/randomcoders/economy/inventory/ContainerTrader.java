package org.randomcoders.economy.inventory;

import java.util.logging.Level;
import org.randomcoders.economy.core.EconomyMod;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		return null;
	}
	
	public void updatePage(int newPageNum, int newX, int newY)
	{
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		this.pageNum = newPageNum;
		this.x = newX;
		this.y = newY;
		
		if(pageNum == 1)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					this.addSlotToContainer(new Slot(playerInv, (i * 9) + 9 + j, x + 44 + j * 18, y + 133 + i * 18));
					((Slot)this.inventorySlots.get(this.inventorySlots.size() - 1)).putStack(new ItemStack(Block.stone, (i * 9) + 9 + j));
					EconomyMod.logger.log(Level.INFO, "Adding slot '" + ((i * 9) + 9 + j) + " to ContainerTrader");
				}
			}
			
			for(int i = 0; i < 9; i++)
			{
				this.addSlotToContainer(new Slot(playerInv, i, x + 44 + i * 18, y + 191));
				((Slot)this.inventorySlots.get(this.inventorySlots.size() - 1)).putStack(new ItemStack(Block.stone, i));
				EconomyMod.logger.log(Level.INFO, "Adding slot '" + i + " to ContainerTrader");
			}
		}
	}
}
