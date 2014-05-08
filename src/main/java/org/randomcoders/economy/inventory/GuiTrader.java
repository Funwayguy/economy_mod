package org.randomcoders.economy.inventory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.randomcoders.economy.blocks.TileEntityTrader;
import org.randomcoders.economy.handlers.trading.HandlerEconomy;
import org.randomcoders.economy.handlers.trading.HandlerTradeDB;
import org.randomcoders.economy.handlers.trading.ItemInfo;
import org.randomcoders.economy.handlers.trading.TradeInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiTrader extends GuiContainer
{
	public static ResourceLocation noGui = new ResourceLocation("economy", "textures/gui/gui_blank.png");
	
	public static ResourceLocation buyGui = new ResourceLocation("economy", "textures/gui/gui_trader_buy.png");
	public static ResourceLocation buyOptionsGui = new ResourceLocation("economy", "textures/gui/gui_trader_buy_options.png");
	public static int buySizeX = 248;
	public static int buySizeY = 184;

	public static ResourceLocation sellGui = new ResourceLocation("economy", "textures/gui/gui_trader_sell.png");
	public static int sellSizeX = 248;
	public static int sellSizeY = 248;

	public static ResourceLocation tradesGui = new ResourceLocation("economy", "textures/gui/gui_trader_trades.png");
	public static int tradesSizeX = 248;
	public static int tradesSizeY = 184;

	public static ResourceLocation marketGui = new ResourceLocation("economy", "textures/gui/gui_trader_market.png");
	public static int marketSizeX = 248;
	public static int marketSizeY = 184;
	
	public GuiButton buyButton;
	public GuiButton sellButton;
	public GuiButton tradesButton;
	public GuiButton marketButton;
	
	public GuiButton confirmButton;
	
	public GuiButton optionsButton;
	public GuiButton damageButton;
	public GuiButton namesButton;
	public GuiButton backButton;
	public GuiButton pgLeftButton;
	public GuiButton pgRightButton;
	
	public boolean allowNames;
	public int damageType;
	public HashMap<Integer, Integer> buyEnchants = new HashMap<Integer, Integer>();
	public ArrayList<Integer> dispEnchants = new ArrayList<Integer>();
	public ArrayList<ItemStack> dispItems = new ArrayList<ItemStack>();
	public GuiTextField optionDamageBox;
	
	public GuiButton deliverButton;
	public GuiButton pickupButton;
	
	public int curPage;
	public int scrollPos = 0;
	public boolean buttonCooldown = false;
	
	public GuiTextField sellOfferBox;
	
	public ItemStack buyItem;
	public GuiTextField buySearchBox;
	public GuiTextField buyAmountBox;
	public GuiTextField buyOfferBox;
	
	public GuiTextField marketSearchBox;
	public ContainerTrader containerTrader;
	
	public EntityPlayer playerUser;
	
	public HashMap<Integer, ItemInfo> tmpEconDB = new HashMap<Integer, ItemInfo>();
	public HashMap<Integer, ItemInfo> tmpEnchDB = new HashMap<Integer, ItemInfo>();
	public int requestCooldown = 0;
	public int curDay;
	
	public GuiTrader(InventoryPlayer playerInvo, TileEntityTrader tile)
	{
		super(new ContainerTrader(playerInvo, tile));
		playerUser = playerInvo.player;
		curDay = (int)Math.floor(playerUser.worldObj.getWorldTime()/24000D);
		containerTrader = (ContainerTrader)this.inventorySlots;
		
		curPage = 0;
		
		xSize = buySizeX;
		ySize = buySizeY;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		reloadButtons();
		reloadTextFields();
		reloadContainer();
	}
	
	public void actionPerformed(GuiButton button)
	{
		if(buttonCooldown)
		{
			buttonCooldown = false;
			return;
		}
		
		int oldPage = curPage;
		
		switch(button.id)
		{
			case 0:
			{
				if(curPage != 0)
				{
					xSize = buySizeX;
					ySize = buySizeY;
					curPage = 0;
					
					if(buyItem != null)
					{
						buyItem = new ItemStack(buyItem.getItem(), buyItem.stackSize);
						
						for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
						{
							if(buyEnchants.containsKey(i))
							{
								buyItem.addEnchantment(Enchantment.enchantmentsList[i], buyEnchants.get(i));
							}
						}
					}
					this.initGui();
				}
				break;
			}
			case 1:
			{
				if(curPage != 1)
				{
					xSize = sellSizeX;
					ySize = sellSizeY;
					curPage = 1;
					this.initGui();
				}
				break;
			}
			case 2:
			{
				if(curPage != 2)
				{
					xSize = tradesSizeX;
					ySize = tradesSizeY;
					curPage = 2;
					this.initGui();
				}
				break;
			}
			case 3:
			{
				if(curPage != 3)
				{
					xSize = marketSizeX;
					ySize = marketSizeY;
					curPage = 3;
					this.initGui();
				}
				break;
			}
			case 4:
			{
				if(curPage != 4)
				{
					xSize = buySizeX;
					ySize = buySizeY;
					curPage = 4;
					this.initGui();
				}
				break;
			}
			case 9:
			{
				if(damageType < 0 || damageType >= 2)
				{
					damageType = 0;
				} else
				{
					damageType += 1;
				}
				
				damageButton.displayString = damageType == 0? "Equals" : (damageType == 1? "Below" : "Above");
				break;
			}
			case 10:
			{
				allowNames = !allowNames;
				namesButton.displayString = "Allow Names: " + (allowNames? "Yes" : "No");
				break;
			}
			case 11:
			{
				if(scrollPos > 0)
				{
					scrollPos--;
				}
				break;
			}
			case 12:
			{
				scrollPos++;
				break;
			}
		}
		
		if(oldPage != curPage)
		{
			scrollPos = 0;
		}
		
		buttonCooldown = true;
	}
	
	public void reloadContainer()
	{
		containerTrader.updatePage(curPage);
	}
	
	public void reloadTextFields()
	{
		
		int x = this.getPosX();
		int y = this.getPosY();
		
		String tmpTxt = buySearchBox != null? buySearchBox.getText() : "";
		buySearchBox = new GuiTextField(fontRenderer, x + 25, y + 49, 78, 14);
		buySearchBox.setText(tmpTxt);

		tmpTxt = buyAmountBox != null? buyAmountBox.getText() : "1";
		buyAmountBox = new GuiTextField(fontRenderer, x + 113, y + 65, 30, 14);
		buyAmountBox.setText(tmpTxt);
		buyAmountBox.setMaxStringLength(3);
		
		tmpTxt = buyOfferBox != null? buyOfferBox.getText() : "$0";
		buyOfferBox = new GuiTextField(fontRenderer, x + 161, y + 65, 62, 14);
		buyOfferBox.setText(tmpTxt);

		tmpTxt = sellOfferBox != null? sellOfferBox.getText() : "$0";
		sellOfferBox = new GuiTextField(fontRenderer, x + 25, y + 97, 94, 14);
		sellOfferBox.setText(tmpTxt);

		tmpTxt = marketSearchBox != null? marketSearchBox.getText() : "";
		marketSearchBox = new GuiTextField(fontRenderer, x + 25, y + 49, 78, 14);
		marketSearchBox.setText(tmpTxt);

		tmpTxt = optionDamageBox != null? optionDamageBox.getText() : "100";
		optionDamageBox = new GuiTextField(fontRenderer, x + 177, y + 41, 30, 14);
		optionDamageBox.setText(tmpTxt);
		optionDamageBox.setMaxStringLength(3);
		
		buySearchBox.setFocused(false);
		buySearchBox.setVisible(false);
		buySearchBox.setEnabled(false);
		buySearchBox.setCanLoseFocus(true);
		
		buyAmountBox.setFocused(false);
		buyAmountBox.setVisible(false);
		buyAmountBox.setEnabled(false);
		buyAmountBox.setCanLoseFocus(true);
		
		buyOfferBox.setFocused(false);
		buyOfferBox.setVisible(false);
		buyOfferBox.setEnabled(false);
		buyOfferBox.setCanLoseFocus(true);
		
		sellOfferBox.setFocused(false);
		sellOfferBox.setVisible(false);
		sellOfferBox.setEnabled(false);
		sellOfferBox.setCanLoseFocus(true);
		
		marketSearchBox.setFocused(false);
		marketSearchBox.setVisible(false);
		marketSearchBox.setEnabled(false);
		marketSearchBox.setCanLoseFocus(true);
		
		optionDamageBox.setFocused(false);
		optionDamageBox.setVisible(false);
		optionDamageBox.setEnabled(false);
		optionDamageBox.setCanLoseFocus(true);
		
		switch(curPage)
		{
			case 0:
			{
				buySearchBox.setVisible(true);
				buySearchBox.setEnabled(true);
				
				buyAmountBox.setVisible(true);
				buyAmountBox.setEnabled(true);
				
				buyOfferBox.setVisible(true);
				buyOfferBox.setEnabled(true);
				break;
			}
			
			case 1:
			{
				sellOfferBox.setVisible(true);
				sellOfferBox.setEnabled(true);
				break;
			}
			
			case 3:
			{
				marketSearchBox.setVisible(true);
				marketSearchBox.setEnabled(true);
				break;
			}
			
			case 4:
			{
				optionDamageBox.setVisible(true);
				optionDamageBox.setEnabled(true);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void reloadButtons()
	{
		this.buttonList.clear();
		
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		
		if(curPage != 4)
		{
			buyButton = new GuiButton(0, posX + 24, posY + 24, 40, 20, "Buy");
			sellButton = new GuiButton(1, posX + 72, posY + 24, 40, 20, "Sell");
			tradesButton = new GuiButton(2, posX + 120, posY + 24, 48, 20, "Trades");
			marketButton = new GuiButton(3, posX + 176, posY + 24, 48, 20, "Market");
		}
		
		switch(curPage)
		{
			case 0:
			{
				optionsButton = new GuiButton(4, posX + 24, posY + 152, 80, 20, "Options");
				deliverButton = new GuiButton(5, posX + 112, posY + 88, 48, 20, "Deliver");
				pickupButton = new GuiButton(6, posX + 176, posY + 88, 48, 20, "Pickup");
				confirmButton = new GuiButton(7, posX + 112, posY + 152, 112, 20, "Confirm");
				this.buttonList.add(optionsButton);
				this.buttonList.add(deliverButton);
				this.buttonList.add(pickupButton);
				this.buttonList.add(confirmButton);
				break;
			}
			case 1:
			{
				confirmButton = new GuiButton(7, posX + 24, posY + 216, 200, 20, "Confirm");
				this.buttonList.add(confirmButton);
				break;
			}
			case 2:
			{
				break;
			}
			case 3:
			{
				break;
			}
			case 4:
			{
				damageButton = new GuiButton(9, posX + 120, posY + 38, 48, 20, damageType == 0? "Equals" : (damageType == 1? "Below" : "Above"));
				namesButton = new GuiButton(10, posX + 120, posY + 62, 104, 20, "Allow Names: " + (allowNames? "Yes" : "No"));
				backButton = new GuiButton(0, posX + 120, posY + 152, 104, 20, "Back");
				pgLeftButton = new GuiButton(11, posX + 24, posY + 38, 40, 20, "<");
				pgRightButton = new GuiButton(12, posX + 64, posY + 38, 40, 20, ">");
				this.buttonList.add(damageButton);
				this.buttonList.add(namesButton);
				this.buttonList.add(backButton);
				this.buttonList.add(pgLeftButton);
				this.buttonList.add(pgRightButton);
				break;
			}
		}
		
		if(curPage != 4)
		{
			this.buttonList.add(buyButton);
			this.buttonList.add(sellButton);
			this.buttonList.add(tradesButton);
			this.buttonList.add(marketButton);
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float f)
	{
		super.drawScreen(par1, par2, f);
		
		buySearchBox.drawTextBox();
		buyAmountBox.drawTextBox();
		buyOfferBox.drawTextBox();
		
		sellOfferBox.drawTextBox();
		
		marketSearchBox.drawTextBox();
		
		optionDamageBox.drawTextBox();
		
		if(curPage == 0)
		{
			if(this.isPointInRegion(121, 121, 14, 14, par1, par2) && buyItem != null)
			{
				this.drawItemStackTooltip(buyItem, par1, par2);
			}
			
			for(int i = 0; i < dispItems.size() && i < 12; i++)
			{
				if(this.isPointInRegion(39 + (i%3)*20, 69 + 18*(i/3), 14, 14, par1, par2))
				{
					this.drawItemStackTooltip(dispItems.get(i), par1, par2);
				}
			}
		} else if(curPage == 2)
		{
			for(int i = 0; i < HandlerTradeDB.buyList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.buyList.get(i);
				
				if(this.isPointInRegion(28, 68 + (i * 16) + 1, 14, 14, par1, par2))
				{
					this.drawItemStackTooltip(trade.tradeItem, par1, par2);
				}
			}
			
			for(int i = 0; i < HandlerTradeDB.sellList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.sellList.get(i);
				
				if(this.isPointInRegion(92, 68 + (i * 16) + 1, 14, 14, par1, par2))
				{
					this.drawItemStackTooltip(trade.tradeItem, par1, par2);
				}
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		switch(curPage)
		{
			case 0:
			{
				fontRenderer.drawString("Trader", 8, 8, 4210752);
				fontRenderer.drawString("No.", 112, 52, 4210752);
				fontRenderer.drawString("Offer", 160, 52, 4210752);
				break;
			}
			case 1:
			{
				fontRenderer.drawString("Trader", 8, 8, 4210752);
				fontRenderer.drawString("Price Per Item", 24, 84, 4210752);
				fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 43, 120, 4210752);
				break;
			}
			case 2:
			{
				fontRenderer.drawString("Trader", 8, 8, 4210752);
				fontRenderer.drawString("Buying", 24, 52, 4210752);
				fontRenderer.drawString("Selling", 88, 52, 4210752);
				fontRenderer.drawString("Complete", 160, 52, 4210752);
				break;
			}
			case 3:
			{
				fontRenderer.drawString("Trader", 8, 8, 4210752);
				fontRenderer.drawString("Market History", 112, 52, 4210752);
				break;
			}
			case 4:
			{
				fontRenderer.drawString("Trade Options", 8, 8, 4210752);
				fontRenderer.drawString("Durability", 120, 24, 4210752);
				fontRenderer.drawString("Enchantments", 24, 24, 4210752);
			}
		}
		
		buttonCooldown = false;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		boolean requestFlag = false;
		
		if(requestCooldown > 0)
		{
			requestCooldown--;
		} else if(requestCooldown < 0)
		{
			requestCooldown = 0;
		}
		
		if((int)Math.floor(playerUser.worldObj.getWorldTime()/24000D) != curDay)
		{
			curDay = (int)Math.floor(playerUser.worldObj.getWorldTime()/24000D);
			tmpEconDB.clear();
			tmpEnchDB.clear();
			requestCooldown = 0;
		}
		
		ResourceLocation texture = noGui;
		
		switch(curPage)
		{
			case 0:
			{
				texture = buyGui;
				break;
			}
			case 1:
			{
				texture = sellGui;
				break;
			}
			case 2:
			{
				texture = tradesGui;
				break;
			}
			case 3:
			{
				texture = marketGui;
				break;
			}
			case 4:
			{
				texture = buyOptionsGui;
				break;
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		if(buyItem == null)
		{
			buyEnchants.clear();
			optionDamageBox.setText("100");
			damageType = 0;
		}
		
		long damage = this.GetAmountFromString(optionDamageBox.getText());
		if(damage > 100L)
		{
			damage = 100L;
		} else if(damage <= 0L)
		{
			damage = 1L;
		}
		
		if(buyItem != null)
		{
			long iDamage = (!buyItem.isItemStackDamageable()? 100L: damage);
			damage = (damageType == 2 || !buyItem.isItemStackDamageable()? 100L: damage);
			if(buyItem.isItemStackDamageable())
			{
				buyItem.setItemDamage(buyItem.getMaxDamage() - (int)Math.round((double)buyItem.getMaxDamage() * ((double)iDamage/100D)));
			}
			buyItem.stackSize = (int)this.GetAmountFromString(this.buyAmountBox.getText());
			
			if(buyItem.stackSize > buyItem.getMaxStackSize())
			{
				buyAmountBox.setText("" + buyItem.getMaxStackSize());
				buyAmountBox.setCursorPositionEnd();
				buyItem.stackSize = buyItem.getMaxStackSize();
			} else if(buyItem.stackSize <= 0)
			{
				buyAmountBox.setText("1");
				buyAmountBox.setCursorPositionEnd();
				buyItem.stackSize = 1;
			}
		} else
		{
			damage = 100L;
		}
		
		if(curPage == 4)
		{
			ArrayList<Enchantment> enchList = new ArrayList<Enchantment>();
			long eCost = 0;
			boolean bannedEnchant = false;
			
			for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
			{
				if(Enchantment.enchantmentsList[i] != null && buyItem != null)
				{
					if(Enchantment.enchantmentsList[i].canApply(buyItem) || buyItem.getItem() instanceof ItemEnchantedBook)
					{
						enchList.add(Enchantment.enchantmentsList[i]);
					}
				}
				
				if(buyEnchants.containsKey(i))
				{
					ItemInfo eInfo = this.requestEnchantInfo(i);
					
					if(eInfo != null && eInfo.currentWorth > -1)
					{
						eCost += eInfo.currentWorth * buyEnchants.get(i);
					} else if(eInfo == null)
					{
						requestFlag = true;
					} else
					{
						bannedEnchant = true;
					}
				}
			}
			
			if(scrollPos*3 > enchList.size())
			{
				scrollPos--;
			}
			
			dispEnchants.clear();
			
			for(int i = 0; i + (scrollPos*3) < enchList.size() && i < 3; i++)
			{
				int index = i + (scrollPos*3);
				int eID = enchList.get(index).effectId;
				dispEnchants.add(eID);
				fontRenderer.drawSplitString(StatCollector.translateToLocal(enchList.get(index).getName()), x + 32, y + 64 + (i*32), 64, 14737632);
				fontRenderer.drawString(buyEnchants.containsKey(eID)? "Lvl " + buyEnchants.get(eID) : "None", x + 32, y + 84 + (i*32), 14737632);
				this.mc.renderEngine.bindTexture(texture);
				this.drawTexturedModalRect(x + 80, y + 80 + (i*32), 0, 184, 16, 8);
				this.drawTexturedModalRect(x + 80, y + 88 + (i*32), 16, 184, 16, 8);
			}
			
			if(!requestFlag && !bannedEnchant)
			{
				fontRenderer.drawString("Enchants: " + HandlerEconomy.GetDisplayCost(eCost), x + 128, y + 96, 14737632);
				fontRenderer.drawString("Discount: " + EnumChatFormatting.RED + "-" + (100 - damage) + "%", x + 128, y + 104, 14737632);
				fontRenderer.drawString("", x + 128, y + 112, 14737632);
				fontRenderer.drawString("Total: $" + Math.round((double)eCost * ((double)damage/100D)), x + 128, y + 128, 14737632);
			} else if(bannedEnchant)
			{
				fontRenderer.drawString("Enchants: " + HandlerEconomy.GetDisplayCost(eCost), x + 128, y + 96, 14737632);
				fontRenderer.drawString("Discount: " + EnumChatFormatting.RED + "-" + (100 - damage) + "%", x + 128, y + 104, 14737632);
				fontRenderer.drawString("", x + 128, y + 112, 14737632);
				fontRenderer.drawString(EnumChatFormatting.RED + "UNAVAILABLE", x + 128, y + 128, 14737632);
			} else
			{
				fontRenderer.drawString("Loading prices...", x + 128, y + 104, 14737632);
			}
		} else if(curPage == 3)
		{
			if(this.isPointInRegion(112, 72, 104, 92, par2, par3))
			{
				ArrayList<String> toolTip = new ArrayList<String>();
				toolTip.add("MouseX: " + par2);
				toolTip.add("MouseY: " + par3);
				this.drawHoveringText(toolTip, par2, par3, fontRenderer);
			}
		} else if(curPage == 2)
		{
			for(int i = 0; i < HandlerTradeDB.buyList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.buyList.get(i);
				
				fontRenderer.drawString(HandlerEconomy.GetDisplayCost(trade.tradeValue), x + 52, y + 68 + (i * 16) + 4, 14737632);
			}
			
			for(int i = 0; i < HandlerTradeDB.sellList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.sellList.get(i);
				
				fontRenderer.drawString(HandlerEconomy.GetDisplayCost(trade.tradeValue), x + 116, y + 68 + (i * 16) + 4, 14737632);
			}
			
			for(int i = 0; i < HandlerTradeDB.buyList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.buyList.get(i);
				
				this.drawItemStack(trade.tradeItem, x + 28, y + 68 + (i * 16), trade.tradeItem.stackSize > 1? "" + trade.tradeItem.stackSize : "");
			}
			
			for(int i = 0; i < HandlerTradeDB.sellList.size(); i++)
			{
				TradeInstance trade = HandlerTradeDB.sellList.get(i);
				
				this.drawItemStack(trade.tradeItem, x + 92, y + 68 + (i * 16), trade.tradeItem.stackSize > 1? "" + trade.tradeItem.stackSize : "");
			}
		} else if(curPage == 1)
		{
			ItemStack sellStack = containerTrader.getSlot(0).getStack();
			
			if(sellStack != null)
			{
				int sDamage = sellStack.isItemStackDamageable()? (int)Math.floor((double)sellStack.getItemDamage()/(double)sellStack.getMaxDamage()*100D) : 100;
				
				long[] sellPrices = this.getSellCosts(sellStack);
				
				if(sellPrices[2] == 2)
				{
					confirmButton.enabled = false;
					sellOfferBox.setTextColor(Color.WHITE.hashCode());
					
					fontRenderer.drawString(EnumChatFormatting.RED + "UNAVAILABLE", x + 56, y + 60, 14737632);
				} else if(sellPrices[2] == 1)
				{
					confirmButton.enabled = false;
					sellOfferBox.setTextColor(Color.WHITE.hashCode());
					requestFlag = true;
					
					fontRenderer.drawString("Loading...", x + 56, y + 60, 14737632);
				} else
				{
					confirmButton.enabled = true;
					fontRenderer.drawString(HandlerEconomy.GetDisplayCost(sellPrices[0] + sellPrices[1]) + " x" + sellStack.stackSize, x + 56, y + 52, 14737632);
					if(sellStack.isItemDamaged())
					{
						fontRenderer.drawString("Damage: " + (sellStack.isItemDamaged()? EnumChatFormatting.RED + "" + sDamage + "%" : ""), x + 56, y + 60, 14737632);
					}
					double damagePercent = 1F - (sellStack.isItemDamaged()? ((double)sellStack.getItemDamage()/(double)sellStack.getMaxDamage()) : 0F);
					long finalCost = (long)Math.round((double)(sellPrices[0] + sellPrices[1]) * damagePercent * sellStack.stackSize);
					fontRenderer.drawString("$" + finalCost, x + 56, y + 68, 14737632);
					
					if(Math.abs(finalCost - this.GetAmountFromString(sellOfferBox.getText())) <= finalCost * 0.1D)
					{
						confirmButton.enabled = true;
						confirmButton.displayString = "Confirm";
						sellOfferBox.setTextColor(Color.WHITE.hashCode());
					} else
					{
						confirmButton.enabled = false;
						sellOfferBox.setTextColor(Color.RED.hashCode());
					}
				}
			} else
			{
				confirmButton.enabled = false;
			}
		} else if(curPage == 0)
		{
			ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
			
			for(int i = 0; i < Item.itemsList.length; i++)
			{
				if(Item.itemsList[i] != null && Item.itemsList[i].getCreativeTab() != null)
				{
					if(Item.itemsList[i].getHasSubtypes())
					{
						Item iMain = Item.itemsList[i];
						ArrayList<ItemStack> subTypes = new ArrayList<ItemStack>();
						iMain.getSubItems(iMain.itemID, iMain.getCreativeTab(), subTypes);
						
						for(int j = 0; j < subTypes.size(); j++)
						{
							boolean flag = false;
							Iterator<String> iterator = subTypes.get(j).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
							
							while (true)
				            {
				                if (iterator.hasNext())
				                {
				                    String s1 = iterator.next();

				                    if (!s1.toLowerCase().contains(buySearchBox.getText().toLowerCase()))
				                    {
				                        continue;
				                    }

				                    flag = true;
				                }

				                if (flag)
				                {
									itemList.add(subTypes.get(j));
				                }

				                break;
				            }
						}
					} else
					{
						boolean flag = false;
						Iterator<String> iterator = new ItemStack(Item.itemsList[i]).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
						
						while (true)
			            {
			                if (iterator.hasNext())
			                {
			                    String s1 = iterator.next();

			                    if (!s1.toLowerCase().contains(buySearchBox.getText().toLowerCase()))
			                    {
			                        continue;
			                    }

			                    flag = true;
			                }

			                if (flag)
			                {
								itemList.add(new ItemStack(Item.itemsList[i]));
			                }

			                break;
			            }
					}
				}
			}
			
			{
				boolean flag = false;
				Iterator<String> iterator = new ItemStack(Item.enchantedBook).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
				
				while (true)
	            {
	                if (iterator.hasNext())
	                {
	                    String s1 = iterator.next();

	                    if (!s1.toLowerCase().contains(buySearchBox.getText().toLowerCase()))
	                    {
	                        continue;
	                    }

	                    flag = true;
	                }

	                if (flag)
	                {
						itemList.add(new ItemStack(Item.enchantedBook));
	                }

	                break;
	            }
			}
			
			if(buyItem != null)
			{
				long[] costs = this.getBuyCosts();
				
				if(costs[2] == 2)
				{
					confirmButton.enabled = false;
					buyOfferBox.setTextColor(Color.WHITE.hashCode());
					
					fontRenderer.drawString(EnumChatFormatting.RED + "UNAVAILABLE", x + 144, y + 124, 14737632);
				} else if(costs[2] == 1)
				{
					confirmButton.enabled = false;
					buyOfferBox.setTextColor(Color.WHITE.hashCode());
					
					fontRenderer.drawString("Loading costs...", x + 144, y + 124, 14737632);
					requestFlag = true;
				} else
				{
					long finalCost = (long)Math.round((double)((costs[0] + costs[1]) * buyItem.stackSize) * ((double)damage/100D));
					fontRenderer.drawString(HandlerEconomy.GetDisplayCost(costs[0] + costs[1]) + " x" + buyItem.stackSize, x + 144, y + 116, 14737632);
					fontRenderer.drawString((buyItem.isItemDamaged() && damageType != 2? "Discount: " + EnumChatFormatting.RED + "-" + (100 - damage) + "%" : ""), x + 144, y + 124, 14737632);
					fontRenderer.drawString("= $" + finalCost, x + 144, y + 132, 14737632);
					
					if(Math.abs(finalCost - this.GetAmountFromString(buyOfferBox.getText())) <= (double)finalCost * 0.1D)
					{
						confirmButton.enabled = true;
						buyOfferBox.setTextColor(Color.WHITE.hashCode());
					} else
					{
						confirmButton.enabled = false;
						buyOfferBox.setTextColor(Color.RED.hashCode());
					}
				}
			} else
			{
				confirmButton.enabled = false;
			}
			
			if(scrollPos*3 + 12 > itemList.size())
			{
				scrollPos = (int)Math.ceil((double)(itemList.size() - 12)/3D);
				if(scrollPos < 0)
				{
					scrollPos = 0;
				}
			}

			int scrollBarPos = 0;
			scrollBarPos = (int)Math.ceil(72D*((double)scrollPos/Math.ceil((double)(itemList.size() - 12)/3D)));
			if(scrollBarPos > 72)
			{
				scrollBarPos = 72;
			} else if(scrollBarPos < 0)
			{
				scrollBarPos = 0;
			}

			this.mc.renderEngine.bindTexture(texture);
			this.drawTexturedModalRect(x + 24, y + 64 + scrollBarPos, 0, 184, 8, 8);
			
			dispItems.clear();
			
			for(int i = 0; i + (scrollPos*3) < itemList.size() && i < 12; i++)
			{
				RenderHelper.disableStandardItemLighting();
				dispItems.add(itemList.get(i + scrollPos*3));
				this.drawItemStack(itemList.get(i + scrollPos*3), x + 38 + (i%3)*20, y + 68 + 18*(i/3), "");
				RenderHelper.enableStandardItemLighting();
			}
			
			if(buyItem != null)
			{
				RenderHelper.disableStandardItemLighting();
				this.drawItemStack(buyItem, x + 120, y + 120, "" + (buyItem.stackSize > 1? buyItem.stackSize : ""));
				RenderHelper.enableStandardItemLighting();
			}
		}
		
		if(requestFlag && requestCooldown <= 0)
		{
			requestCooldown = 60;
		}
	}

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        
        switch(curPage)
        {
        	case 0:
        	{
        		buySearchBox.mouseClicked(par1, par2, par3);
        		buyAmountBox.mouseClicked(par1, par2, par3);
        		buyOfferBox.mouseClicked(par1, par2, par3);
        		
        		if(par3 == 0)
        		{
        			for(int i = 0; i < dispItems.size() && i < 12; i++)
        			{
        				if(this.isPointInRegion(39 + (i%3)*20, 69 + 18*(i/3), 14, 14, par1, par2))
        				{
        					buyItem = dispItems.get(i);
        					buyEnchants.clear();
        					optionDamageBox.setText("100");
        					damageType = 0;
        					break;
        				}
        			}
        		}
        		break;
        	}
        	
        	case 1:
        	{
        		sellOfferBox.mouseClicked(par1, par2, par3);
        		break;
        	}
        	
        	case 3:
        	{
        		marketSearchBox.mouseClicked(par1, par2, par3);
        		break;
        	}
        	
        	case 4:
        	{
        		optionDamageBox.mouseClicked(par1, par2, par3);
        		
        		if(par3 == 0)
        		{
	        		for(int i = 0; i < dispEnchants.size(); i++)
	        		{
                        int eID = dispEnchants.get(i);
                        
                        if(Enchantment.enchantmentsList[eID] == null)
                        {
                        	System.out.println("Unable to find enchantment with ID " + eID);
                        	continue;
                        }
                        
	        			if(this.isPointInRegion(80, 80 + (i*32), 16, 8, par1, par2))
	        			{
	                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	                        
	        				if(buyEnchants.containsKey(eID))
	        				{
	        					if(buyEnchants.get(eID) < Enchantment.enchantmentsList[eID].getMaxLevel())
	        					{
	        						buyEnchants.put(eID, buyEnchants.get(eID) + 1);
	        					}
	        				} else
	        				{
	        					buyEnchants.put(eID, 1);
	        				}
	        				
	        				break;
	        			} else if(this.isPointInRegion(80, 88 + (i*32), 16, 8, par1, par2))
	        			{
	                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	                        
	        				if(buyEnchants.containsKey(eID))
	        				{
	        					if(buyEnchants.get(eID) > 1)
	        					{
	        						buyEnchants.put(eID, buyEnchants.get(eID) - 1);
	        					} else
	        					{
	        						buyEnchants.remove(eID);
	        					}
	        				}
	        				
	        				break;
	        			}
	        		}
        		}
        		break;
        	}
        }
    }
    
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    	
    	if(curPage == 0 || curPage == 3)
    	{
	    	int scrollDir = (int)Math.signum(Mouse.getEventDWheel());
	    	
	    	if(scrollPos - scrollDir <= 0)
	    	{
	    		scrollPos = 0;
	    	} else
	    	{
	    		scrollPos -= scrollDir;
	    	}
    	}
    }
    
    public void keyTyped(char c, int i)
    {
    	if(i == 1)
    	{
    		this.mc.thePlayer.closeScreen();
    	}
    	
    	int keyType = !Character.isAlphabetic(c) && !Character.isDigit(c)? 0 : Character.isDigit(c)? 1 : 2;
    	
    	if(curPage == 0)
    	{
    		if(buySearchBox.isFocused())
    		{
    			buySearchBox.textboxKeyTyped(c, i);
    			scrollPos = 0;
    		} else if(buyAmountBox.isFocused() && keyType != 2)
    		{
    			buyAmountBox.textboxKeyTyped(c, i);
    			
    			int curPos = buyAmountBox.getCursorPosition();
    			
    			buyAmountBox.setText(this.StringToAmount(buyAmountBox.getText()));
    			
    			if(curPos > buyAmountBox.getText().length())
    			{
    				curPos = buyAmountBox.getText().length();
    			}
    			
    			buyAmountBox.setCursorPosition(curPos);
    		} else if(buyOfferBox.isFocused() && keyType != 2)
    		{
    			buyOfferBox.textboxKeyTyped(c, i);
    			
    			int curPos = buyOfferBox.getCursorPosition();
    			
    			buyOfferBox.setText(this.StringToPrice(buyOfferBox.getText()));
    			
    			if(curPos > buyOfferBox.getText().length())
    			{
    				curPos = buyOfferBox.getText().length();
    			}
    			
    			buyOfferBox.setCursorPosition(curPos);
    		}
    	} else if(curPage == 1)
    	{
    		if(sellOfferBox.isFocused() && keyType != 2)
    		{
    			sellOfferBox.textboxKeyTyped(c, i);
    			
    			int curPos = sellOfferBox.getCursorPosition();
    			
    			sellOfferBox.setText(this.StringToPrice(sellOfferBox.getText()));
    			
    			if(curPos > sellOfferBox.getText().length())
    			{
    				curPos = sellOfferBox.getText().length();
    			}
    			
    			sellOfferBox.setCursorPosition(curPos);
    		}
    	} else if(curPage == 3)
    	{
    		if(marketSearchBox.isFocused())
    		{
    			marketSearchBox.textboxKeyTyped(c, i);
    		}
    	} else if(curPage == 4)
    	{
    		if(optionDamageBox.isFocused() && keyType != 2)
    		{
    			optionDamageBox.textboxKeyTyped(c, i);
    			
    			int curPos = optionDamageBox.getCursorPosition();
    			
    			optionDamageBox.setText(this.StringToAmount(optionDamageBox.getText()));
    			
    			if(curPos > optionDamageBox.getText().length())
    			{
    				curPos = optionDamageBox.getText().length();
    			}
    			
    			optionDamageBox.setCursorPosition(curPos);
    		}
    	}
    }
    
    public String StringToPrice(String orig)
    {
    	char[] cArray = orig.toCharArray();
    	StringBuilder sOut = new StringBuilder();
    	
    	for(int i = 0; i < cArray.length; i++)
    	{
    		if(Character.isDigit(cArray[i]))
    		{
    			sOut.append(cArray[i]);
    		}
    	}
    	
    	if(sOut.length() <= 0)
    	{
    		return "$";
    	} else
    	{
    		return "$" + sOut.toString();
    	}
    }
    
    public String StringToAmount(String orig)
    {
    	char[] cArray = orig.toCharArray();
    	StringBuilder sOut = new StringBuilder();
    	
    	for(int i = 0; i < cArray.length; i++)
    	{
    		if(Character.isDigit(cArray[i]))
    		{
    			sOut.append(cArray[i]);
    		}
    	}
    	
    	if(sOut.length() <= 0)
    	{
    		return "";
    	} else
    	{
    		return sOut.toString();
    	}
    }
    
    public long GetAmountFromString(String sIn)
    {
    	String numStr = this.StringToAmount(sIn);
    	long numOut = 0;
    	
    	try
    	{
    		numOut = Long.parseLong(numStr);
    	} catch(NumberFormatException e)
    	{
    	}
    	
    	return numOut;
    }

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = null;
        if (par1ItemStack != null) font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        if (font == null) font = fontRenderer;
        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
        itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3, par4Str);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }

	public int getPosX()
	{
		return (this.width - xSize) / 2;
	}

	public int getPosY()
	{
		return (this.height - ySize) / 2;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        return null;
    }
	
	public long[] getSellCosts(ItemStack sellItem)
	{
		long[] totalCosts = new long[]{0,0,0};
		
		NBTTagList enchTags = sellItem.getEnchantmentTagList();
		
		if(sellItem.itemID == Item.enchantedBook.itemID)
		{
			enchTags = Item.enchantedBook.func_92110_g(sellItem);
		}
		
		if(enchTags != null)
		{
			for(int i = 0; i < enchTags.tagCount(); i++)
			{
                short enID = ((NBTTagCompound)enchTags.tagAt(i)).getShort("id");
                short enLVL = ((NBTTagCompound)enchTags.tagAt(i)).getShort("lvl");
				
				ItemInfo eInfo = this.requestEnchantInfo(enID);
				
				if(eInfo != null && eInfo.currentWorth > -1)
				{
					totalCosts[1] += eInfo.currentWorth * enLVL;
				} else if(eInfo == null && totalCosts[2] != 2)
				{
					totalCosts[2] = 1;
				} else
				{
					totalCosts[2] = 2;
				}
			}
		}
		
		ItemInfo iInfo = requestItemInfo(sellItem.itemID);
		
		if(iInfo != null && iInfo.currentWorth != -1)
		{
			totalCosts[0] = iInfo.currentWorth;
		} else if(iInfo == null && totalCosts[2] != 2)
		{
			totalCosts[2] = 1;
		} else
		{
			totalCosts[2] = 2;
		}
		
		return totalCosts;
	}
	
	public long[] getBuyCosts()
	{
		long totalCosts[] = new long[]{0,0,0};
		
		if(buyEnchants.size() > 0)
		{
			for(int i = 0; i < Enchantment.enchantmentsList.length; i++)
			{
				if(buyEnchants.containsKey(i))
				{
					ItemInfo eInfo = requestEnchantInfo(i);
					if(eInfo != null && eInfo.currentWorth > -1)
					{
						totalCosts[1] += (eInfo.currentWorth * buyEnchants.get(i));
					} else if(eInfo == null)
					{
						totalCosts[2] = 1;
					} else
					{
						totalCosts[2] = 2;
					}
				}
			}
		}
		
		if(buyItem != null)
		{
			ItemInfo iInfo = requestItemInfo(buyItem.itemID);
			
			if(iInfo != null && iInfo.currentWorth != -1)
			{
				totalCosts[0] = iInfo.currentWorth;
			} else if(iInfo == null && totalCosts[2] != 2)
			{
				totalCosts[2] = 1;
			} else
			{
				totalCosts[2] = 2;
			}
		}
		
		return totalCosts;
	}
	
	public ItemInfo requestItemInfo(int itemID)
	{
		if(tmpEconDB.containsKey(itemID))
		{
			return tmpEconDB.get(itemID);
		}
		
		if(requestCooldown == 0)
		{
			// TODO: Send packet to server requesting this item's info profile
			return HandlerEconomy.economyDB.get(itemID);
		}
		return null;
	}
	
	public ItemInfo requestEnchantInfo(int enchantID)
	{
		if(tmpEnchDB.containsKey(enchantID))
		{
			return tmpEnchDB.get(enchantID);
		}
		
		if(requestCooldown == 0)
		{
			// TODO: Send packet to server requesting this enchantment's info profile
			return HandlerEconomy.enchantDB.get(enchantID);
		}
		
		return null;
	}
}
