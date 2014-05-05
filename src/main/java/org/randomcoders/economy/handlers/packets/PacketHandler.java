package org.randomcoders.economy.handlers.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.HandlerBlocks;
import org.randomcoders.economy.inventory.ContainerTrader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	public static final String channel = "Economy_CH";
	
	public static final int TRADER_ID = 0;
	public static final int ECONOMY_ID = 1;
	public static final int GUI_ID = 2;
	public static final int NOTICE_ID = 3;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if(packet.channel.equals(channel))
		{
			EconomyMod.logger.log(Level.INFO, "Recieved valid EconomyMod packet.");
			handlePacket(packet);
		}
	}
	
	public void handlePacket(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try
		{
			int packetID = inputStream.readInt();
			
			switch(packetID)
			{
				case TRADER_ID:
				{
					PacketTrader.handleTradePacket(packet);
					break;
				}
				
				case ECONOMY_ID:
				{
					PacketEconomy.handleEconomyPacket(packet);
					break;
				}
				
				case GUI_ID:
				{
					break;
				}
				
				case NOTICE_ID:
				{
					PacketNotice.handleNoticePacket(packet);
					break;
				}
			}
			
			inputStream.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
