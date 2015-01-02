package org.randomcoders.economy.handlers.packets;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.Level;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import org.randomcoders.economy.handlers.trading.ItemInfo;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;

public class PacketEconomy extends PacketParent implements IMessage
{
	public PacketEconomy()
	{
	}
	
	public PacketEconomy(NBTTagCompound tags)
	{
		super(tags);
	}
	
	@Override
	public void HandlePacket(Side side)
	{
		if(!tags.hasKey("action"))
		{
			System.out.println("Packet is invalid!");
			return;
		}
		
		int actionID = this.tags.getInteger("action");
		
		switch(actionID)
		{
			case 0: // Get ItemStack information
			{
				if(side == Side.SERVER)
				{
					System.out.println("Recieved serverside...");
					this.ReturnInfo();
				} else
				{
					System.out.println("Recieved clientside...");
					this.RecieveInfo();
				}
				break;
			}
			case 1: // Get next n Economy entries
			{
				
			}
		}
	}
	
	public void ReturnInfo()
	{
		WorldServer world = MinecraftServer.getServer().worldServerForDimension(tags.getInteger("world"));
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(tags.getString("player"));
		String req_type = tags.getString("req_type");
		
		if(player == null || !(player instanceof EntityPlayerMP))
		{
			EconomyMod.logger.log(Level.ERROR, "Unable to find player while processing request!");
			return;
		}
		
		NBTTagCompound retTags = new NBTTagCompound();
		
		retTags.setInteger("action", 0);
		retTags.setString("req_type", req_type);
		retTags.setString("req_id", tags.getString("req_id"));
		
		if(req_type.equals("item"))
		{
			ItemInfo info = HandlerEconomy.economyDB.get(tags.getString("req_id"));
			
			if(info != null)
			{
				info.WriteToNBT(retTags);
			}
		} else if(req_type.equals("enchant"))
		{
			ItemInfo info = HandlerEconomy.enchantDB.get(Integer.parseInt(tags.getString("req_id")));
			
			if(info != null)
			{
				info.WriteToNBT(retTags);
			}
		}
		
		EconomyMod.instance.network.sendTo(new PacketEconomy(retTags), (EntityPlayerMP)player);
	}
	
	public void RecieveInfo()
	{
		ItemInfo info = new ItemInfo(tags.getString("req_id"), -1L);
		if(tags.hasKey("info"))
		{
			info = new ItemInfo(tags.getTagList("info", 10));
		}
		
		if(tags.getString("req_type").equals("enchant"))
		{
			HandlerEconomy.enchantDB.put(Integer.parseInt(info.itemID), info);
		} else if(tags.getString("req_type").equals("item"))
		{
			HandlerEconomy.economyDB.put(info.itemID, info);
		}
	}
}
