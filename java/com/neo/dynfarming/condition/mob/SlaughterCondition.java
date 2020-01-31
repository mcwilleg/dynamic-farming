package com.neo.dynfarming.condition.mob;

import com.neo.dynfarming.condition.HarvestCondition;
import org.bukkit.entity.LivingEntity;

public abstract class SlaughterCondition extends HarvestCondition {
	protected LivingEntity entity;
	
	public SlaughterCondition(LivingEntity entity) {
		super(entity.getLocation());
		this.entity = entity;
	}
}
