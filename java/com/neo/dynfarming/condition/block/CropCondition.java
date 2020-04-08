package com.neo.dynfarming.condition.block;

import com.neo.dynfarming.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;

public class CropCondition extends BlockCondition {
	private static final String[] CROP_MATERIALS;
	
	static {
		if(Utils.isVersionBefore(1, 13, 0)) {
			// 1.12 crop names
			CROP_MATERIALS = new String[] {
					"CROPS", "POTATO", "CARROT", "PUMPKIN_STEM", "MELON_STEM", "SUGAR_CANE_BLOCK"
			};
		} else {
			// 1.13+ crop names
			CROP_MATERIALS = new String[] {
					"WHEAT", "POTATOES", "CARROTS", "PUMPKIN_STEM", "ATTACHED_PUMPKIN_STEM", "MELON_STEM",
					"ATTACHED_MELON_STEM", "SUGAR_CANE", "BAMBOO", "SWEET_BERRY_BUSH"
			};
		}
	}
	
	public CropCondition(Block block) {
		super(block, 2);
	}
	
	@Override
	public double getDropMultiplier() {
		double multiplier = 1;
		
		BlockData data = block.getBlockData();
		if(data instanceof Ageable) {
			Ageable ageable = (Ageable) data;
			if(ageable.getAge() == ageable.getMaximumAge()) {
				multiplier *= getWaterMultiplier();
				multiplier *= getTemperatureMultiplier();
				multiplier *= getPopulationMultiplier();
			}
		}
		return multiplier;
	}
	
	public double getWaterMultiplier() {
		double water = environment.getAmount(Material.WATER);
		water = Utils.evaluateIdealNormalRange(water, 0, 3, 6, 8);
		water = Utils.adjustScale(water, 0.5, 4);
		return water;
	}
	
	public double getTemperatureMultiplier() {
		Location location = environment.getCenter();
		double temperature = location.getWorld().getTemperature(location.getBlockX(), location.getBlockZ());
		temperature = Utils.evaluateGradient(-0.5, 2.0, 0, 1, temperature);
		temperature = Utils.evaluateIdealNormal(temperature, 0, 0.6, 1);
		temperature = Utils.adjustScale(temperature, 0.5, 1.5);
		return temperature;
	}
	
	public double getPopulationMultiplier() {
		double population = 0;
		for(String m : CROP_MATERIALS) {
			try {
				population += environment.getAmount(Material.valueOf(m));
			} catch(Exception ex) {
				// ignore
			}
		}
		
		population = Utils.evaluateGradient(7, 10, 1, 0, population);
		population = Utils.adjustScale(population, 0.7, 1);
		return population;
	}
}
