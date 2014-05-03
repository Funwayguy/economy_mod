package org.randomcoders.economy.handlers.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import net.minecraft.network.packet.Packet250CustomPayload;

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
				case 0: // Get ItemStack information
				{
					break;
				}
				case 1: // Get next n Economy entries
				{
					
				}
			}

			inputStream.close();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
