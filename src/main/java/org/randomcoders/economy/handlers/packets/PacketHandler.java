package org.randomcoders.economy.handlers.packets;

import org.randomcoders.economy.core.EconomyMod;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static void RegisterHandlers()
	{
		EconomyMod.instance.network.registerMessage(HandleServerEconomyPacket.class, PacketEconomy.class, 0, Side.SERVER);
		EconomyMod.instance.network.registerMessage(HandleClientEconomyPacket.class, PacketEconomy.class, 1, Side.CLIENT);
	}
	
	public static class HandleServerEconomyPacket implements IMessageHandler<PacketEconomy, IMessage>
	{
		@Override
		public IMessage onMessage(PacketEconomy message, MessageContext ctx)
		{
			message.HandlePacket(Side.SERVER);
			return null;
		}
		
	}
	
	public static class HandleClientEconomyPacket implements IMessageHandler<PacketEconomy, IMessage>
	{
		@Override
		public IMessage onMessage(PacketEconomy message, MessageContext ctx)
		{
			message.HandlePacket(Side.CLIENT);
			return null;
		}
		
	}
}
