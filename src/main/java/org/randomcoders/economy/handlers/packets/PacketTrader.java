package org.randomcoders.economy.handlers.packets;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;

public class PacketTrader extends PacketParent
{
	public PacketTrader(NBTTagCompound tags)
	{
		super(tags);
	}
	
	@Override
	public void HandlePacket(Side side)
	{
		int action = this.tags.getInteger("action");
		
		//valid action indexes: 0 = buy, 1 = sell, 2 = request player's trade list;
		
		switch(action)
		{
			case 0:
			{
				break;
			}
			
			case 1:
			{
				break;
			}
			
			case 2:
			{
				break;
			}
		}
	}
}