package org.randomcoders.economy.inventory;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiTrader extends GuiContainer
{
	public static int buySizeX = 248;
	public static int buySizeY = 184;
	
	public static int sellSizeX = 248;
	public static int sellSizeY = 248;
	
	public GuiTrader(InventoryPlayer playerInvo)
	{
		super(new ContainerTrader(playerInvo));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		//fontRenderer.drawString("Trader", 8, 6, 4210752);
		//fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		xSize = sellSizeX;
		ySize = sellSizeY;
		ResourceLocation texture = new ResourceLocation("economy","textures/gui/gui_trader_sell.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
}
