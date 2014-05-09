package org.randomcoders.economy.handlers.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import org.randomcoders.economy.handlers.trading.ItemInfo;
import org.randomcoders.economy.inventory.GuiTrader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketEconomy
{
	public static void handleEconomyPacket(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try
		{
			int packetID = inputStream.readInt();
			int actionID = inputStream.readInt();
			
			switch(actionID)
			{
				case 0: // Request ItemInfo (process this serverside)
				{
					int dbIndex = inputStream.readInt();
					int itemID = inputStream.readInt();
					int entityID = inputStream.readInt();
					int worldDim = inputStream.readInt();
					
					if(MinecraftServer.getServer().isServerRunning())
					{
						Entity entity = MinecraftServer.getServer().worldServers[worldDim].getEntityByID(entityID);
						if(entity instanceof EntityPlayer)
						{
							EntityPlayer player = (EntityPlayer)entity;
							
							ItemInfo iInfo = null;
							
							if(dbIndex == 0)
							{
								iInfo = HandlerEconomy.economyDB.get(itemID);
							} else if(dbIndex == 1)
							{
								iInfo = HandlerEconomy.enchantDB.get(itemID);
							}
							
							if(iInfo == null)
							{
								iInfo = new ItemInfo(itemID, -1);
							}
						}
					}
					
					break;
				}
				
				case 1: // ItemInfo response (process this clientside)
				{
					int dbIndex = inputStream.readInt();
					int entityID = inputStream.readInt();
					ObjectInputStream objIn = new ObjectInputStream(inputStream);
					HashMap<String, HashMap> rawInfo = (HashMap<String, HashMap>)objIn.readObject();
					
					ItemInfo iInfo = rebuildInfo(rawInfo);
					
					GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
					
					if(curScreen != null && curScreen instanceof GuiTrader && iInfo != null)
					{
						GuiTrader trader = (GuiTrader)curScreen;
						
						if(dbIndex == 0)
						{
							trader.tmpEconDB.put(iInfo.itemID, iInfo);
						} else if(dbIndex == 1)
						{
							trader.tmpEnchDB.put(iInfo.itemID, iInfo);
						}
					}
					objIn.close();
					break;
				}
			}

			inputStream.close();
		} catch(IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static ItemInfo rebuildInfo(HashMap<String, HashMap> entry)
	{
		return null;
	}
}
