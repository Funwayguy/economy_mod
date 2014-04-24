package org.randomcoders.economy.handlers.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.inventory.ContainerTrader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	public static final String channel = "Economy_CH";
	
	public static final int TRADER_ID = 0;
	public static final int ECONOMY_ID = 1;
	public static final int GUI_ID = 2;
	
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
				
				case GUI_ID: //Used for switching the server side ContainerTrader's page number and updating slot positions. May not be necessary.
				{
					if(MinecraftServer.getServer().isServerRunning())
					{
						int pageNum = inputStream.readInt();
						int posX = inputStream.readInt();
						int posY = inputStream.readInt();
						int userID = inputStream.readInt();
						int worldDim = inputStream.readInt();
						
						Entity entityUser = MinecraftServer.getServer().worldServers[worldDim].getEntityByID(userID);
						
						if(entityUser instanceof EntityPlayer)
						{
							EntityPlayer playerUser = (EntityPlayer)entityUser;
							if(playerUser.openContainer instanceof ContainerTrader)
							{
								ContainerTrader traderCon = (ContainerTrader)playerUser.openContainer;
								traderCon.updatePage(pageNum, posX, posY);
							}
						}
					}
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
