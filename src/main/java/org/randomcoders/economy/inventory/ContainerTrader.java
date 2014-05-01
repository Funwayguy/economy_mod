package org.randomcoders.economy.inventory;

import org.randomcoders.economy.blocks.TileEntityTrader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTrader extends Container
{
	public TileEntityTrader traderInventory;
	public InventoryPlayer playerInv;
	public int pageNum;
	
	public ContainerTrader(InventoryPlayer playerInvo, TileEntityTrader trader)
	{
		this.traderInventory = trader;
		trader.openChest();
		
		this.addSlotToContainer(new Slot(trader, 0, 8, 18));
		
		int j;
		int k;
		
		for(j = 0; j < 3; ++j)
		{
			for(k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(playerInvo, k + j * 9 + 9, 8 + k * 18, 103 + j * 18));
			}
		}
		
		for(j = 0; j < 9; ++j)
		{
			this.addSlotToContainer(new Slot(playerInvo, j, 8 + j * 18, 161));
		}
	}
	
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.traderInventory.isUseableByPlayer(par1EntityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if(slotNum == 0)
			{
				if(!this.mergeItemStack(itemstack1, 1, 37, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			} else
			{
				if(slotNum >= 1 && slotNum < 37 && !this.mergeItemStack(itemstack1, 0, 1, false))
				{
					return null;
				}
			}
			
			if(itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			} else
			{
				slot.onSlotChanged();
			}
			
			if(itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, itemstack1);
		}
		
		return itemstack;
	}
	
	public void updatePage(int newPageNum)
	{
		this.pageNum = newPageNum;
		
		if(pageNum == 1)
		{
			Slot tSlot = this.getSlot(0);
			tSlot.xDisplayPosition = 8;
			tSlot.yDisplayPosition = 18;
			
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					Slot slot = this.getSlot((i * 9) + 9 + j + 1);
					slot.xDisplayPosition = 44 + (j * 18);
					slot.yDisplayPosition = 133 + (i * 18);
				}
			}
			
			for(int i = 0; i < 9; i++)
			{
				Slot slot = this.getSlot(i + 1);
				slot.xDisplayPosition = 44 + (i * 18);
				slot.yDisplayPosition = 191;
			}
		} else
		{
			for(int i = 0; i < this.inventorySlots.size(); i++)
			{
				Slot slot = this.getSlot(i);
				slot.xDisplayPosition = -999;
				slot.yDisplayPosition = -999;
			}
		}
	}
	
	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		ItemStack stack = this.traderInventory.getStackInSlotOnClosing(0);
		if(stack != null)
		{
			par1EntityPlayer.dropPlayerItem(stack);
		}
		this.traderInventory.closeChest();
	}
}
