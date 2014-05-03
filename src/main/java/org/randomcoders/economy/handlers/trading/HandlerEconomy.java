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
import java.util.logging.Level;
import org.randomcoders.economy.core.EconomyMod;
import org.randomcoders.economy.handlers.HandlerConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class HandlerEconomy
{
	public static HashMap<Integer, ItemInfo> economyDB = new HashMap<Integer, ItemInfo>();
	public static HashMap<Integer, ItemInfo> enchantDB = new HashMap<Integer, ItemInfo>();
	public static int prevDay;
	
	public static void LoadPriceList()
	{
		Random rand = new Random();
		
		for(int i = 0; i < Item.itemsList.length; i++)
		{
			if(Item.itemsList[i] != null)
			{
				ItemInfo iInfo = new ItemInfo(i, rand.nextInt(1000));
				
				economyDB.put(i, iInfo);
			}
		}
		
		for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
		{
			if(Enchantment.enchantmentsList[i] != null)
			{
				ItemInfo iInfo = new ItemInfo(i, rand.nextInt(1000));
				
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
			LoadPriceList();
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
				
				economyDB = new HashMap<Integer, ItemInfo>();
				enchantDB = new HashMap<Integer, ItemInfo>();
				
				Iterator<HashMap<String, HashMap>> iterator = loadedDB.iterator();
				
				while(iterator.hasNext())
				{
					HashMap<String, HashMap> entry = iterator.next();
					
					int dbType = (int)entry.get("General").get("DB");
					int itemID = (int)entry.get("General").get("itemID");
					int currentWorth = (int)entry.get("General").get("currentWorth");
					int totalSpentToday = (int)entry.get("General").get("totalSpentToday");
					int totalSalesToday = (int)entry.get("General").get("totalSalesToday");
					
					ItemInfo loadedInfo = new ItemInfo(itemID, currentWorth);
					loadedInfo.totalSpentToday = totalSpentToday;
					loadedInfo.totalSalesToday = totalSalesToday;
					loadedInfo.costHistory = entry.get("CostHistory");
					loadedInfo.demandHistory = entry.get("DemandHistory");
					loadedInfo.supplyHistory = entry.get("SupplyHistory");
					
					if(dbType == 1)
					{
						enchantDB.put(itemID, loadedInfo);
					} else
					{
						economyDB.put(itemID, loadedInfo);
					}
				}
			} catch(IOException | ClassNotFoundException | ClassCastException e)
			{
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
			EconomyMod.logger.log(Level.WARNING, "World directory could not be found!");;
			EconomyMod.logger.log(Level.WARNING, "Economy database failed to save as a result!");
			EconomyMod.logger.log(Level.WARNING, "Database will revert to last save on restart!");
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
			return;
		}
	}

	public static void SyncWithServer()
	{
		if(EconomyMod.proxy.isClient())
		{
			return;
		}
	}
}
