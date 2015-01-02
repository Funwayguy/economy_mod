package org.randomcoders.economy.handlers.packets;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.nbt.NBTTagCompound;

public class PacketNotice extends PacketParent
{
	public PacketNotice(NBTTagCompound tags)
	{
		super(tags);
	}
	
	@Override
	public void HandlePacket(Side side)
	{
	}
}
