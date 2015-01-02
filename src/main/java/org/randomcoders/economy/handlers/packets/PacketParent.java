package org.randomcoders.economy.handlers.packets;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;

public class PacketParent implements IMessage
{
	protected NBTTagCompound tags = new NBTTagCompound();
	
	public PacketParent()
	{
	}
	
	public PacketParent(NBTTagCompound tags)
	{
		this.tags = tags;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, tags);
	}
	
	public void HandlePacket(Side side)
	{
	}
}
