package org.randomcoders.economy.handlers.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.randomcoders.economy.core.EconomyMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketTrader
{
	public static void handleTradePacket(Packet250CustomPayload packet)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		int packetID;
		int action;
		
		try
		{
			packetID = inputStream.readInt();
			action = inputStream.read();
			
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
			
			inputStream.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}