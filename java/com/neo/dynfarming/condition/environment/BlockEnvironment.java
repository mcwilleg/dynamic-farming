package com.neo.dynfarming.condition.environment;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockEnvironment extends Environment {
	private Map<Material, Integer> blocks;
	
	public BlockEnvironment(Location center, double radius) {
		super(center, radius);
		this.blocks = new HashMap<>();
		
		for(int x = minLocation.getBlockX(); x <= maxLocation.getBlockX(); x++) {
			for(int z = minLocation.getBlockZ(); z <= maxLocation.getBlockZ(); z++) {
				for(int y = minLocation.getBlockY(); y <= maxLocation.getBlockY(); y++) {
					Block block = center.getWorld().getBlockAt(x, y, z);
					if(block.getLocation().distance(center) <= radius) {
						Material material = block.getType();
						int amount = getAmount(material);
						blocks.put(material, amount + 1);
					}
				}
			}
		}
	}
	
	public final int getAmount(Material material) {
		return blocks.getOrDefault(material, 0);
	}
}
