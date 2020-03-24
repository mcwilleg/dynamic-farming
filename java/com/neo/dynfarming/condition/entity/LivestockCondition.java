package com.neo.dynfarming.condition.entity;

import com.neo.dynfarming.util.Utils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

public class LivestockCondition extends EntityCondition {
	public LivestockCondition(LivingEntity entity) {
		super(entity, 8);
	}
	
	@Override
	public double getDropMultiplier() {
		double multiplier = 1;
		
		multiplier *= getTerrainMultiplier();
		multiplier *= getCrowdingMultiplier();
		
		return multiplier;
	}
	
	public double getTerrainMultiplier() {
		double result = 1;
		
		double grass = environment.getAmount(Material.GRASS_BLOCK) / area;
		grass = Utils.adjustScale(grass, 0.2, 1);
		
		double hay = environment.getAmount(Material.HAY_BLOCK) / volume;
		hay = Utils.adjustScale(hay / 0.1, 1, 2.5);
		
		double water = environment.getAmount(Material.WATER) / area;
		water = Utils.adjustScale(water / 0.5, 1, 2);
		
		result *= grass;
		result *= hay;
		result *= water;
		return Utils.adjustScale(result, 0.25, 5);
	}
	
	public double getCrowdingMultiplier() {
		double result;
		
		double populationFactor = getPopulationFactor();
		double distanceFactor = getDistanceFactor();
		
		result = populationFactor * distanceFactor / 3;
		result = Utils.clamp(result, 0, 1);
		return Utils.adjustScale(result, 0.1, 1);
	}
	
	public double getPopulationFactor() {
		int population = environment.getAreaPopulation();
		
		double minResult = 0.3;
		double maxResult = 2;
		double idealPopulation = 4;
		double maxPopulation = 8;
		return Utils.evaluateGradient(idealPopulation, maxPopulation, maxResult, minResult, population);
	}
	
	public double getDistanceFactor() {
		double avgDistance = environment.getAvgDistance();
		
		double minResult = 0.3;
		double maxResult = 2;
		double lowerDistance = 1;
		//     upperDistance = radius;
		return Utils.evaluateGradient(lowerDistance, radius, minResult, maxResult, avgDistance);
	}
}
