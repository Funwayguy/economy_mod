package org.randomcoders.economy.handlers.trading;

import net.minecraft.item.ItemStack;

public class TradeInstance
{
	public ItemStack tradeItem;
	public int tradeValue;
	public String tradeOwner;
	
	public boolean allowNamed;
	public int damageType;
	public int damageValue;
	
	public TradeInstance(ItemStack item, int value, String owner)
	{
		this.tradeItem = item;
		this.tradeValue = value;
		this.tradeOwner = owner;
		this.allowNamed = false;
		this.damageType = 0;
	}
	
	public String getDisplayValue()
	{
		if(tradeValue < 1000)
		{
			return "" + tradeValue;
		} else if(tradeValue < 1000000)
		{
			return "" + (Math.round(tradeValue/100D))/10D + "K";
		} else
		{
			return "" + (Math.round(tradeValue/100000D))/10D + "M";
		}
	}
}