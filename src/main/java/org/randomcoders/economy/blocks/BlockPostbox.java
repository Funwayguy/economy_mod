package org.randomcoders.economy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.randomcoders.economy.core.EconomyMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPostbox extends BlockContainer
{
	public IIcon frontIcon;
	public IIcon sideIcon;
	public IIcon topIcon;
	
	public BlockPostbox(Material par2Material)
	{
		super(par2Material);
		setHardness(5);
		setStepSound(Block.soundTypeMetal);
		this.setCreativeTab(EconomyMod.economyTab);
		setBlockTextureName("economy:block_postbox");
		this.setHarvestLevel("pickaxe", 1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.frontIcon = iconRegister.registerIcon(getTextureName() + "_front");
		this.sideIcon = iconRegister.registerIcon(getTextureName() + "_side");
		this.topIcon = iconRegister.registerIcon(getTextureName() + "_top");
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote)
		{
			//par5EntityPlayer.openGui(EconomyMod.instance, 1, par1World, par2, par3, par4);
			return true;
		} else
		{
			par5EntityPlayer.openGui(EconomyMod.instance, 1, par1World, par2, par3, par4);
			return true;
		}
	}

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.topIcon : (par1 == 0 ? this.topIcon : (par1 != par2 ? this.sideIcon : this.frontIcon));
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            Block l = par1World.getBlock(par2, par3, par4 - 1);
            Block i1 = par1World.getBlock(par2, par3, par4 + 1);
            Block j1 = par1World.getBlock(par2 - 1, par3, par4);
            Block k1 = par1World.getBlock(par2 + 1, par3, par4);
            byte b0 = 3;

            if (l.isOpaqueCube() && !i1.isOpaqueCube())
            {
                b0 = 3;
            }

            if (i1.isOpaqueCube() && !l.isOpaqueCube())
            {
                b0 = 2;
            }

            if (j1.isOpaqueCube() && !k1.isOpaqueCube())
            {
                b0 = 5;
            }

            if (k1.isOpaqueCube() && !j1.isOpaqueCube())
            {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }

        if (l == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }

        if (l == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }

        if (l == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityPostbox();
	}
}
