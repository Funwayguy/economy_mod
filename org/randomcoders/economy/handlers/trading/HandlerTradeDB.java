package org.randomcoders.economy.handlers.trading;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import org.randomcoders.economy.core.EconomyMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HandlerTradeDB
{
	public static ArrayList<TradeInstance> buyList = new ArrayList<TradeInstance>();
	public static ArrayList<TradeInstance> sellList = new ArrayList<TradeInstance>();
	
	public static int purchase(int Amount, int offer, TradeInstance trade)
	{
		int result = 2;
		
		
		
		return result; // 0 = Success, 1 = Can't afford, 2 = Unknown error
	}
	
	public static void sortBuyList()
	{
		if(buyList.size() <= 1)
		{
			return;
		}
		
		EconomyMod.logger.log(Level.INFO, "Sorting buy list...");
		
		for(int i = 1; i < buyList.size(); i++)
		{
			if(buyList.get(i).tradeValue < buyList.get(i-1).tradeValue)
			{
				for(int j = i - 1; j >= 0; j--)
				{
					if(j == 0 || (buyList.get(i).tradeValue < buyList.get(j).tradeValue && buyList.get(i).tradeValue >= buyList.get(j - 1).tradeValue))
					{
						TradeInstance tmpTrade = buyList.get(i);
						buyList.remove(i);
						buyList.add(j, tmpTrade);
						break;
					}
				}
			}
		}
		
		EconomyMod.logger.log(Level.INFO, "Buy list sorted");
	}
	
	public static void sortSellList()
	{
		if(sellList.size() <= 1)
		{
			return;
		}
		
		EconomyMod.logger.log(Level.INFO, "Sorting sell list...");
		
		for(int i = 1; i < sellList.size(); i++)
		{
			if(sellList.get(i).tradeValue < sellList.get(i-1).tradeValue)
			{
				for(int j = i - 1; j >= 0; j--)
				{
					if(j == 0 || (sellList.get(i).tradeValue < sellList.get(j).tradeValue && sellList.get(i).tradeValue >= sellList.get(j - 1).tradeValue))
					{
						TradeInstance tmpTrade = sellList.get(i);
						sellList.remove(i);
						sellList.add(j, tmpTrade);
						break;
					}
				}
			}
		}
		
		EconomyMod.logger.log(Level.INFO, "Sell list sorted.");
	}
	
	public static void seedWithRandomTrades()
	{
		Random rand = new Random();
		
		for(int i = rand.nextInt(5) + 5; i >= 0; i--)
		{
			ItemStack tradeItem = new ItemStack(Item.pickaxeDiamond);
			
			tradeItem.setItemDamage(rand.nextInt(Item.pickaxeDiamond.getMaxDamage()));
			
			if(rand.nextBoolean() && rand.nextBoolean())
			{
				tradeItem.addEnchantment(Enchantment.efficiency, 1 + rand.nextInt(5));
			}
			
			if(rand.nextBoolean() && rand.nextBoolean())
			{
				tradeItem.addEnchantment(Enchantment.unbreaking, 1 + rand.nextInt(3));
			}
			
			if(rand.nextBoolean() && rand.nextBoolean())
			{
				tradeItem.addEnchantment(Enchantment.fortune, 1 + rand.nextInt(3));
			}
			
			TradeInstance newTrade = new TradeInstance(tradeItem, rand.nextInt(10000), "Villager #" + (rand.nextInt(10) + 1));
			if(rand.nextBoolean())
			{
				buyList.add(newTrade);
			} else
			{
				sellList.add(newTrade);
			}
		}
		
		sortSellList();
		sortBuyList();
	}
}
