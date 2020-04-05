package com.neo.dynfarming;

import com.neo.dynfarming.condition.Condition;
import com.neo.dynfarming.condition.ConditionFactory;
import com.neo.dynfarming.condition.block.CropCondition;
import com.neo.dynfarming.condition.entity.LivestockCondition;
import com.neo.dynfarming.listener.HarvestListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class DynFarming extends JavaPlugin implements Listener {
	private static final boolean DEBUG = false;
	
	private static boolean COLOR = true;
	
	// TODO remove debug
	private static final DecimalFormat DECIMAL = new DecimalFormat("0.00");
	
	@Override
	public void onEnable() {
		// Plugin startup logic
		if(DEBUG) {
			registerListener(this);
		}
		registerListener(new HarvestListener());
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if(event instanceof PlayerInteractAtEntityEvent || event.getHand() != EquipmentSlot.HAND) {
			return;
		}
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		if(entity instanceof LivingEntity) {
			LivingEntity victim = (LivingEntity) entity;
			Condition condition = ConditionFactory.getCondition(victim);
			if(condition instanceof LivestockCondition) {
				ChatColor c = ChatColor.GRAY;
				if(COLOR) {
					c = ChatColor.DARK_GRAY;
				}
				COLOR = !COLOR;
				
				LivestockCondition lc = (LivestockCondition) condition;
				player.sendMessage(c + "Terrain: " + DECIMAL.format(lc.getTerrainMultiplier()));
				player.sendMessage(c + "Population: " + DECIMAL.format(lc.getPopulationFactor()));
				player.sendMessage(c + "Distance: " + DECIMAL.format(lc.getDistanceFactor()));
				player.sendMessage(c + "Crowding: " + DECIMAL.format(lc.getCrowdingMultiplier()));
				player.sendMessage(c + "Multiplier: " + DECIMAL.format(lc.getDropMultiplier()));
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
			return;
		}
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Condition condition = ConditionFactory.getCondition(block);
		if(condition instanceof CropCondition) {
			ChatColor c = ChatColor.GRAY;
			if(COLOR) {
				c = ChatColor.DARK_GRAY;
			}
			COLOR = !COLOR;
			
			CropCondition cc = (CropCondition) condition;
			player.sendMessage(c + "Water: " + DECIMAL.format(cc.getWaterMultiplier()));
			player.sendMessage(c + "Temperature: " + DECIMAL.format(cc.getTemperatureMultiplier()));
			player.sendMessage(c + "Population: " + DECIMAL.format(cc.getPopulationMultiplier()));
			player.sendMessage(c + "Multiplier: " + DECIMAL.format(cc.getDropMultiplier()));
		}
	}
}
