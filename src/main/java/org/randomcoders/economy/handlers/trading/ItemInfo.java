package org.randomcoders.economy.handlers.trading;

import java.util.HashMap;

public class ItemInfo
{
	public int itemID;
	public long currentWorth;
	public long totalSpentToday = 0;
	public int totalSalesToday = 0;
	public HashMap<Integer, Long> costHistory;
	public HashMap<Integer, Integer> demandHistory;
	public HashMap<Integer, Integer> supplyHistory;
	
	public ItemInfo(int itemID, long initWorth)
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
}
