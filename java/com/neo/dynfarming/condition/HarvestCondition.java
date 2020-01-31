package com.neo.dynfarming.condition;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public abstract class HarvestCondition {
	protected Location location;
	protected Map<Material, Integer> surroundings;
	
	public HarvestCondition(Location location) {
		this.location = location;
		this.surroundings = new HashMap<>();
		this.getConditions();
	}
	
	protected abstract void getConditions();
}
