package com.neo.dynfarming.condition.environment;

import org.bukkit.Location;

public abstract class Environment {
	private final Location center;
	
	private final double radius;
	private final double area;
	private final double volume;
	
	final Location minLocation;
	final Location maxLocation;
	
	Environment(Location center, double radius) {
		this.center = center;
		
		this.radius = radius;
		this.area = Math.PI * Math.pow(radius, 2);
		this.volume = (4 * Math.PI / 3) * Math.pow(radius, 3);
		
		this.minLocation = center.clone().add(-radius, -radius, -radius);
		this.maxLocation = center.clone().add(radius, radius, radius);
	}
	
	public Location getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getArea() {
		return area;
	}
	
	public double getVolume() {
		return volume;
	}
}
