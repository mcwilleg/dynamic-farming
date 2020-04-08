package com.neo.dynfarming.condition.block;

import com.neo.dynfarming.condition.Condition;
import com.neo.dynfarming.condition.environment.BlockEnvironment;
import org.bukkit.block.Block;

abstract class BlockCondition implements Condition {
	final Block block;
	final BlockEnvironment environment;
	final double radius;
	final double area;
	final double volume;
	
	BlockCondition(Block block, double radius) {
		this.block = block;
		this.environment = new BlockEnvironment(block.getLocation(), radius);
		
		this.radius = radius;
		this.area = environment.getArea();
		this.volume = environment.getVolume();
	}
}
