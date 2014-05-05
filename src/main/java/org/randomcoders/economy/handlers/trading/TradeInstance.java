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
}