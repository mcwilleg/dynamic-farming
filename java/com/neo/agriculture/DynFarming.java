package com.neo.agriculture;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynFarming extends JavaPlugin implements Listener {
	private static final boolean DEBUG = true;
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		if(DEBUG) {
			registerListener(this);
		}
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getHand() == EquipmentSlot.HAND && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if(block != null) {
				event.getPlayer().sendMessage(block.getType().toString());
			}
		}
	}
}
