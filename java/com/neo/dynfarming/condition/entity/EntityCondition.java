package com.neo.dynfarming.condition.entity;

import com.neo.dynfarming.condition.Condition;
import com.neo.dynfarming.condition.environment.EntityEnvironment;
import org.bukkit.entity.LivingEntity;

abstract class EntityCondition implements Condition {
	final EntityEnvironment environment;
	final double radius;
	final double area;
	final double volume;
	
	EntityCondition(LivingEntity entity, double radius) {
		this.environment = new EntityEnvironment(entity, radius);
		
		this.radius = radius;
		this.area = environment.getArea();
		this.volume = environment.getVolume();
	}
}
