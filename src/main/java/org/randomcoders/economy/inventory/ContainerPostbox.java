package org.randomcoders.economy.inventory;

import org.randomcoders.economy.blocks.TileEntityPostbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPostbox extends Container
{
	IInventory postboxInventory;
	public ContainerPostbox(IInventory playerInvo, TileEntityPostbox tile)
	{
		this.postboxInventory = tile;
		tile.openChest();
		
		int i = (3 - 4) * 18;
		int j;
		int k;
		
		for(j = 0; j < 3; ++j)
		{
			for(k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(tile, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}
		
		for(j = 0; j < 3; ++j)
		{
			for(k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(playerInvo, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
			}
		}
		
		for(j = 0; j < 9; ++j)
		{
			this.addSlotToContainer(new Slot(playerInvo, j, 8 + j * 18, 161 + i));
		}
	}
	
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.postboxInventory.isUseableByPlayer(par1EntityPlayer);
	}
	
	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(par2);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(par2 < 27)
			{
				if(!this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else if(!this.mergeItemStack(itemstack1, 0, 27, false))
			{
				return null;
			}
			
			if(itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		
		return itemstack;
	}
	
	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.postboxInventory.closeChest();
	}
}
