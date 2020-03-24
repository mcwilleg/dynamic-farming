package com.neo.dynfarming;

import com.neo.dynfarming.condition.Condition;
import com.neo.dynfarming.condition.ConditionFactory;
import com.neo.dynfarming.condition.entity.LivestockCondition;
import com.neo.dynfarming.listener.HarvestListener;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class DynFarming extends JavaPlugin implements Listener {
	private static final boolean DEBUG = true;
	
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
				LivestockCondition lc = (LivestockCondition) condition;
				player.sendMessage("Terrain: " + DECIMAL.format(lc.getTerrainMultiplier()));
				player.sendMessage("Population: " + DECIMAL.format(lc.getPopulationFactor()));
				player.sendMessage("Distance: " + DECIMAL.format(lc.getDistanceFactor()));
				player.sendMessage("Crowding: " + DECIMAL.format(lc.getCrowdingMultiplier()));
				player.sendMessage("Multiplier: " + DECIMAL.format(lc.getDropMultiplier()));
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(
				new AttributeModifier("testAttackSpeed", 64, AttributeModifier.Operation.ADD_NUMBER)
		);
	}
}
