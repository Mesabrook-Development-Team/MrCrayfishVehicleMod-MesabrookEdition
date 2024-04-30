package com.mrcrayfish.vehicle.util;

import com.mrcrayfish.vehicle.VehicleMod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHelper 
{
	/**
	 * Converts entries from the Forge Ore Dictionary to a usable ItemStack.
	 *
	 * @param String dictionaryEntry
	 * @param int stackAmount
	 */
	public static ItemStack getItemStackFromOreDictionary(String dictionaryEntry, int stackAmount)
	{
		ItemStack[] stacks = OreDictionary.getOres(dictionaryEntry).toArray(new ItemStack[0]);
		if(stackAmount <= 0)
		{
			stackAmount = 1;
		}
		if (stacks.length > 0)
		{
			ItemStack stack = stacks[0].copy();
			stack.setCount(stackAmount);
			return stack;
		}
		return null;
	}
}
