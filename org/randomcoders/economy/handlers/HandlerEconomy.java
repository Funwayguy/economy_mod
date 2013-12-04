package org.randomcoders.economy.handlers;

import java.util.HashMap;

import org.randomcoders.economy.core.proxies.CommonProxy;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class HandlerEconomy extends Handler {

	private HashMap<String, Integer> economyValues = new HashMap<String, Integer>();
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}
	
	public void recalculateValues() {
		economyValues.clear();
		System.out.println("Item Recipes found to process: " + CraftingManager.getInstance().getRecipeList().size());
		
		//to scroll item/block list and see if possible? //todothink
		
		boolean roundComplete = false, loopComplete= false;
		
		do {
			loopComplete = true;
			
			do {
				roundComplete = true;
				
				for(Object objectRecipe : CraftingManager.getInstance().getRecipeList()) {
					int economyValue = 0;
					if(objectRecipe instanceof ShapedRecipes) {
						ShapedRecipes recipe = (ShapedRecipes)objectRecipe;

						if(!economyValueExists(recipe.getRecipeOutput())) {

							for(int i = 0; i < recipe.recipeItems.length; i++) {
								if(recipe.recipeItems[i] != null) {
									if(economyValueExists(recipe.recipeItems[i])) {
										economyValue += getEconomyValue(recipe.recipeItems[i]);
									} else {
										economyValue = 0;
										break;
									}
								}
							}

							if(economyValue != 0) {
								addEconomyValue(recipe.getRecipeOutput(), economyValue);
								roundComplete = false;
								loopComplete = false;
							}
						}
					} else if(objectRecipe instanceof ShapelessRecipes) {
						ShapelessRecipes recipe = (ShapelessRecipes)objectRecipe;

						if(!economyValueExists(recipe.getRecipeOutput())) {

							for(Object itemObject : recipe.recipeItems) {
								ItemStack item = (ItemStack)itemObject;

								if(economyValueExists(item)) {
									economyValue += getEconomyValue(item);
								} else {
									economyValue = 0;
									break;
								}
							}

							if(economyValue != 0) {
								addEconomyValue(recipe.getRecipeOutput(), economyValue);
								roundComplete = false;
								loopComplete = false;
							}
						}
					}
				}
			} while(!roundComplete);
		} while(!loopComplete);
	}
	
	public void addEconomyValue(ItemStack item, int value) {
		addEconomyValue(item, value, true);
	}
	public void addEconomyValue(ItemStack item, int value, boolean recalculate) {
		addEconomyValue(getKey(item), value, recalculate);
	}
	public void addEconomyValue(String item, int value) {
		addEconomyValue(item, value, true);
	}
	public void addEconomyValue(String item, int value, boolean recalculate) {
		addEconomyValue(item, value, recalculate, false);
	}
	public void addEconomyValue(String item, int value, boolean recalculate, boolean replace) {
		if(!economyValueExists(item) || replace) {
			economyValues.put(item, value);

			if(recalculate) {
				recalculateValues();
			}
		}
	}
	
	public int getEconomyValue(ItemStack item) {
		if(economyValueExists(getKey(item))) {
			return getEconomyValue(getKey(item));
		} else if(economyValueExists(item.itemID + ":*")) {
			return getEconomyValue(item.itemID + ":*");
		}
		return -1;
	}
	
	public int getEconomyValue(String item) {
		if(economyValueExists(item)) {
			return economyValues.get(item);
		}
		return -1;
	}
	
	public boolean economyValueExists(ItemStack item) {
		return economyValueExists(getKey(item));
	}
	
	public boolean economyValueExists(String item) {
		if(economyValues.containsKey(item)) {
			return true;
		}
		return false;
	}
	
	public String getKey(ItemStack item) {
		return item.itemID + ":" + item.getItemDamage();
	}

}
