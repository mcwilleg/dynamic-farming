package com.neo.dynfarming.condition.block;

import com.neo.dynfarming.util.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CropCondition extends BlockCondition {
	public CropCondition(Block block) {
		super(block, 2);
	}
	
	@Override
	public double getDropMultiplier() {
		double multiplier = 1;
		
		multiplier *= getWaterMultiplier();
		multiplier *= getTemperatureMultiplier();
		multiplier *= getPopulationMultiplier();
		
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
		double population = environment.getAmount(Material.WHEAT);
		population += environment.getAmount(Material.POTATOES);
		population += environment.getAmount(Material.CARROTS);
		population += environment.getAmount(Material.PUMPKIN_STEM);
		population += environment.getAmount(Material.ATTACHED_PUMPKIN_STEM);
		population += environment.getAmount(Material.MELON_STEM);
		population += environment.getAmount(Material.ATTACHED_MELON_STEM);
		population += environment.getAmount(Material.SUGAR_CANE);
		
		String[] materials = new String[] {"BAMBOO", "SWEET_BERRY_BUSH"};
		for(String m : materials) {
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
