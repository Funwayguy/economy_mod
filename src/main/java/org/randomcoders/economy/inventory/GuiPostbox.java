package org.randomcoders.economy.inventory;

import org.lwjgl.opengl.GL11;
import org.randomcoders.economy.blocks.TileEntityPostbox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPostbox extends GuiContainer
{
    private static final ResourceLocation resource = new ResourceLocation("textures/gui/container/generic_54.png");
	public GuiPostbox(InventoryPlayer playerInvo, TileEntityPostbox tile)
	{
		super(new ContainerPostbox(playerInvo, tile));
        this.allowUserInput = false;
        short short1 = 222;
        int i = short1 - 108;
        this.ySize = i + 3 * 18;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(resource);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 3 * 18 + 17);
        this.drawTexturedModalRect(k, l + 3 * 18 + 17, 0, 126, this.xSize, 96);
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString("Postbox", 8, 6, 4210752);
    }
}
