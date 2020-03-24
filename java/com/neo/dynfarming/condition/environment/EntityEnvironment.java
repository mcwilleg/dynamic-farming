package com.neo.dynfarming.condition.environment;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EntityEnvironment extends BlockEnvironment {
	private Map<EntityType, Integer> entities;
	private int numNearby;
	private double avgDistance;
	
	public EntityEnvironment(LivingEntity entity, double radius) {
		super(entity.getLocation(), radius);
		this.entities = new HashMap<>();
		
		this.numNearby = 0;
		double totalDistance = 0;
		for(Entity nearby : entity.getNearbyEntities(radius, radius, radius)) {
			if(nearby instanceof LivingEntity && !(nearby instanceof Player) && !nearby.isDead()) {
				double distance = entity.getLocation().distance(nearby.getLocation());
				if(distance < radius) {
					EntityType type = nearby.getType();
					int amount = getAmount(type);
					entities.put(type, amount + 1);
					
					numNearby++;
					totalDistance += distance;
				}
			}
		}
		if(numNearby > 0) {
			this.avgDistance = totalDistance / numNearby;
		} else {
			this.avgDistance = radius;
		}
	}
	
	public final int getAmount(EntityType type) {
		return entities.getOrDefault(type, 0);
	}
	
	public final int getAreaPopulation() {
		return numNearby;
	}
	
	public final double getAvgDistance() {
		return avgDistance;
	}
}
