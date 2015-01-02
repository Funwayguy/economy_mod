package org.randomcoders.economy.handlers.trading;

import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemInfo
{
	public String itemID;
	public long currentWorth;
	public long totalSpentToday = 0;
	public int totalSalesToday = 0;
	public HashMap<Integer, Long> costHistory;
	public HashMap<Integer, Integer> demandHistory;
	public HashMap<Integer, Integer> supplyHistory;
	
	public ItemInfo(String itemID, long initWorth)
	{
		this.itemID = itemID;
		this.currentWorth = initWorth;
	}
	
	public void LoadHistory()
	{
	}
	
	public void UpdatePrice(int day)
	{
		long avgWorth = Math.round((double)totalSpentToday/(double)totalSalesToday);
		
		if(avgWorth != currentWorth)
		{
			currentWorth = avgWorth;
		}
		
		costHistory.put(day, currentWorth);
	}
	
	/**
	 * Returns this ItemInfo instance in a format that can be serialized to file
	 * @return HashMap
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, HashMap> GetSerializableFormat()
	{
		HashMap<String, HashMap> formattedMap = new HashMap<String, HashMap>();
		
		HashMap<String, Object> generalMap = new HashMap<String, Object>();
		generalMap.put("itemID", this.itemID);
		generalMap.put("currentWorth", this.currentWorth);
		generalMap.put("totalSpentToday", this.totalSpentToday);
		generalMap.put("totalSalesToday", this.totalSalesToday);
		
		formattedMap.put("General", generalMap);
		formattedMap.put("CostHistory", this.costHistory);
		formattedMap.put("DamandHistory", this.demandHistory);
		formattedMap.put("SupplyHistory", this.supplyHistory);
		
		return formattedMap;
	}
	
	public ItemInfo(NBTTagList tagList)
	{
		NBTTagCompound genTags = tagList.getCompoundTagAt(0);
		
		this.itemID = genTags.getString("itemID");
		this.currentWorth = genTags.getLong("worth");
		this.totalSpentToday = genTags.getLong("spent");
		this.totalSalesToday = genTags.getInteger("sales");
		
		NBTTagCompound costTags = tagList.getCompoundTagAt(1);
		this.costHistory = new HashMap<Integer, Long>();
		
		for(int i = 0; i < costTags.getInteger("size"); i++)
		{
			costHistory.put(i, costTags.getLong("" + i));
		}
		
		NBTTagCompound demandTags = tagList.getCompoundTagAt(2);
		this.demandHistory = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < demandTags.getInteger("size"); i++)
		{
			demandHistory.put(i, demandTags.getInteger("" + i));
		}
		
		NBTTagCompound supplyTags = tagList.getCompoundTagAt(3);
		this.supplyHistory = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < supplyTags.getInteger("size"); i++)
		{
			supplyHistory.put(i, supplyTags.getInteger("" + i));
		}
	}
	
	public NBTTagCompound WriteToNBT(NBTTagCompound baseTags)
	{
		NBTTagList infoTags = new NBTTagList();
		
		NBTTagCompound generalTags = new NBTTagCompound();
		
		generalTags.setString("itemID", this.itemID);
		generalTags.setLong("worth", this.currentWorth);
		generalTags.setLong("spent", this.totalSpentToday);
		generalTags.setLong("sales", this.totalSalesToday);
		
		infoTags.appendTag(generalTags);
		
		// Cost History
		NBTTagCompound costTags = new NBTTagCompound();
		Integer[] costKeys = this.costHistory.keySet().toArray(new Integer[0]);
		costTags.setInteger("size", costKeys.length);
		
		for(int i = 0; i < costKeys.length; i++)
		{
			costTags.setLong("" + i, this.costHistory.get(costKeys[i]));
		}
		
		infoTags.appendTag(costTags);
		
		// Demand History
		NBTTagCompound demandTags = new NBTTagCompound();
		Integer[] demandKeys = this.demandHistory.keySet().toArray(new Integer[0]);
		demandTags.setInteger("size", demandKeys.length);
		
		for(int i = 0; i < demandKeys.length; i++)
		{
			demandTags.setInteger("" + i, this.demandHistory.get(demandKeys[i]));
		}
		
		infoTags.appendTag(demandTags);
		
		// Supply History
		NBTTagCompound supplyTags = new NBTTagCompound();
		Integer[] supplyKeys = this.supplyHistory.keySet().toArray(new Integer[0]);
		supplyTags.setInteger("size", supplyKeys.length);
		
		for(int i = 0; i < supplyKeys.length; i++)
		{
			supplyTags.setInteger("" + i, this.supplyHistory.get(supplyKeys[i]));
		}
		
		infoTags.appendTag(supplyTags);
		
		baseTags.setTag("info", infoTags);
		
		return baseTags;
	}
}
