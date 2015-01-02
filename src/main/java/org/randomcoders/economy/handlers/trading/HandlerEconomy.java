package org.randomcoders.economy.handlers.trading;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.apache.logging.log4j.Level;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.HandlerConfig;
import org.randomcoders.economy.handlers.packets.PacketEconomy;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class HandlerEconomy
{
	public static HashMap<String, ItemInfo> economyDB = new HashMap<String, ItemInfo>();
	public static HashMap<Integer, ItemInfo> enchantDB = new HashMap<Integer, ItemInfo>();
	public static int prevDay;
	
	public static void LoadPriceList()
	{
		Random rand = new Random();
		
		Iterator<Item> iterator = Item.itemRegistry.iterator();
		
		while(iterator.hasNext())
		{
			Item item = iterator.next();
			if(item != null)
			{
				ItemInfo iInfo = new ItemInfo(Item.itemRegistry.getNameForObject(item), rand.nextInt(10000000));
				
				economyDB.put(Item.itemRegistry.getNameForObject(item), iInfo);
			}
		}
		
		for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
		{
			if(Enchantment.enchantmentsList[i] != null)
			{
				ItemInfo iInfo = new ItemInfo("" + i, rand.nextInt(10000000));
				
				enchantDB.put(i, iInfo);
			}
		}
	}
	
	/**
	 * Updates the economic prices of all items at the end of a day in the given world
	 * @param world
	 */
	public static void UpdateDay(World world)
	{
		long time = world.getWorldTime();
		int curDay = (int)Math.floor(time/24000D);
		
		if(curDay != prevDay)
		{
			prevDay = curDay;
			
			Iterator<ItemInfo> iterator = economyDB.values().iterator();
			
			while(iterator.hasNext())
			{
				ItemInfo entry = iterator.next();
				entry.UpdatePrice(curDay);
			}
			
			Iterator<ItemInfo> iterator2 = enchantDB.values().iterator();
			
			while(iterator2.hasNext())
			{
				ItemInfo entry = iterator2.next();
				entry.UpdatePrice(curDay);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void LoadDB()
	{
		File ecoDB = new File(HandlerConfig.worldDir.getAbsolutePath(), "EconomyDB");
		
		if(!ecoDB.exists())
		{
			//LoadPriceList();
			return;
		} else
		{
			try
			{
				FileInputStream fileIn = new FileInputStream(ecoDB);
				BufferedInputStream buffer = new BufferedInputStream(fileIn);
				ObjectInputStream objIn = new ObjectInputStream(buffer);
				
				ArrayList<HashMap<String, HashMap>> loadedDB = (ArrayList<HashMap<String, HashMap>>)objIn.readObject();
				
				objIn.close();
				buffer.close();
				fileIn.close();
				
				Iterator<HashMap<String, HashMap>> iterator = loadedDB.iterator();
				
				while(iterator.hasNext())
				{
					HashMap<String, HashMap> entry = iterator.next();
					
					int dbType = (Integer)(entry.get("General").get("DB"));
					String itemID = (String)entry.get("General").get("itemID");
					long currentWorth = (Long)entry.get("General").get("currentWorth");
					long totalSpentToday = (Long)entry.get("General").get("totalSpentToday");
					int totalSalesToday = (Integer)entry.get("General").get("totalSalesToday");
					
					ItemInfo loadedInfo = new ItemInfo(itemID, currentWorth);
					loadedInfo.totalSpentToday = totalSpentToday;
					loadedInfo.totalSalesToday = totalSalesToday;
					loadedInfo.costHistory = entry.get("CostHistory");
					loadedInfo.demandHistory = entry.get("DemandHistory");
					loadedInfo.supplyHistory = entry.get("SupplyHistory");
					
					if(dbType == 1)
					{
						enchantDB.put(Integer.parseInt(itemID), loadedInfo);
					} else
					{
						economyDB.put(itemID, loadedInfo);
					}
				}
			} catch(Exception e)
			{
				EconomyMod.logger.log(Level.WARN, "An error occured while attempting to load economy database!");
				e.printStackTrace();
				return;
			}
		}
	}
	
	public static void SaveDB()
	{
		if(economyDB == null || enchantDB == null)
		{
			return;
		}
		
		if(HandlerConfig.worldDir == null)
		{
			EconomyMod.logger.log(Level.WARN, "World directory could not be found!");;
			EconomyMod.logger.log(Level.WARN, "Economy database failed to save as a result!");
			EconomyMod.logger.log(Level.WARN, "Database will revert to last save on restart!");
			return;
		}
		
		File ecoDB = new File(HandlerConfig.worldDir.getAbsolutePath(), "EconomyDB");
		
		if(!ecoDB.exists())
		{
			try
			{
				ecoDB.createNewFile();
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream(ecoDB);
			BufferedOutputStream buffer = new BufferedOutputStream(fileOut);
			ObjectOutputStream objOut = new ObjectOutputStream(buffer);
			
			ArrayList<HashMap<String, HashMap>> saveDB = new ArrayList<HashMap<String, HashMap>>();
			
			Iterator<ItemInfo> iterator = economyDB.values().iterator();
			
			while(iterator.hasNext())
			{
				ItemInfo entry = iterator.next();
				HashMap<String, HashMap> formattedInfo = entry.GetSerializableFormat();
				formattedInfo.get("General").put("DB", 0);
				saveDB.add(formattedInfo);
			}
			
			Iterator<ItemInfo> iterator2 = enchantDB.values().iterator();
			
			while(iterator2.hasNext())
			{
				ItemInfo entry = iterator2.next();
				HashMap<String, HashMap> formattedInfo = entry.GetSerializableFormat();
				formattedInfo.get("General").put("DB", 1);
				saveDB.add(formattedInfo);
			}
			
			objOut.writeObject(saveDB);
			
			objOut.close();
			buffer.close();
			fileOut.close();
		} catch(IOException e)
		{
			EconomyMod.logger.log(Level.WARN, "An error occured while attempting to save economy database!");
			e.printStackTrace();
			return;
		}
	}
	
	public static String GetDisplayCost(long value)
	{
		if(value < 1000L)
		{
			return "$" + value;
		} else if(value < 1000000L)
		{
			return "$" + (Math.round(value/100D))/10D + "K";
		} else if(value < 1000000000L)
		{
			return "$" + (Math.round(value/100000D))/10D + "M";
		} else
		{
			return "$" + (Math.round(value/100000000D))/10D + "B";
		}
	}
	
	/**
	 * Makes are request to the server to obtain the economic information for
	 * the given item and send it to the requesting player's database
	 * @return
	 */
	public static void RequestInfo(EntityPlayer player, Item item)
	{
		if(!player.worldObj.isRemote)
		{
			return;
		}
		NBTTagCompound reqTags = new NBTTagCompound();
		reqTags.setString("player", player.getCommandSenderName());
		reqTags.setInteger("world", player.worldObj.provider.dimensionId);
		reqTags.setInteger("action", 0);
		reqTags.setString("req_type", "item");
		reqTags.setString("req_id", Item.itemRegistry.getNameForObject(item));
		
		PacketEconomy ecoPacket = new PacketEconomy(reqTags);
		
		EconomyMod.instance.network.sendToServer(ecoPacket);
	}
	
	/**
	 * Makes are request to the server to obtain the economic information for
	 * the given enchantment and send it to the requesting player's database
	 * @return
	 */
	public static void RequestInfo(EntityPlayer player, Enchantment enchant)
	{
		if(!player.worldObj.isRemote)
		{
			return;
		}
		NBTTagCompound reqTags = new NBTTagCompound();
		reqTags.setString("player", player.getCommandSenderName());
		reqTags.setInteger("world", player.worldObj.provider.dimensionId);
		reqTags.setInteger("action", 0);
		reqTags.setString("req_type", "enchant");
		reqTags.setString("req_id", "" + enchant.effectId);
		
		PacketEconomy ecoPacket = new PacketEconomy(reqTags);
		
		EconomyMod.instance.network.sendToServer(ecoPacket);
	}
}
