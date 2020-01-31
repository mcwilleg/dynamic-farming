package com.neo.dynfarming.condition.block;

import com.neo.dynfarming.condition.HarvestCondition;
import org.bukkit.block.Block;

public class CropCondition extends HarvestCondition {
	public CropCondition(Block block) {
		super(block.getLocation());
	}
	
	@Override
	protected void getConditions() {
		// TODO
	}
}
