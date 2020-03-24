package com.neo.dynfarming.condition;

import com.neo.dynfarming.condition.entity.LivestockCondition;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;

public class ConditionFactory {
	public static Condition getCondition(LivingEntity entity) {
		String typeName = entity.getType().name();
		if(LIVESTOCK.contains(typeName)) {
			return new LivestockCondition(entity);
		}
		return null;
	}
	
	public static Condition getCondition(Block block) {
		return null;
	}
	
	private static final List<String> LIVESTOCK = Arrays.asList(
			"COW",
			"DONKEY",
			"HORSE",
			"LLAMA",
			"MULE",
			"MUSHROOM_COW",
			"PIG",
			"SHEEP",
			"TRADER_LLAMA"
	);
}
