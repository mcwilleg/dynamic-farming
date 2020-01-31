package com.neo.agriculture;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HarvestListener implements Listener {
	private DynFarming plugin;
	
	public HarvestListener(DynFarming plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity victim = event.getEntity();
		if(victim.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) victim.getLastDamageCause();
			if(damageEvent.getDamager() instanceof Player) {
				Player damager = (Player) damageEvent.getDamager();
				StringBuilder message = new StringBuilder();
				for(ItemStack drop : event.getDrops()) {
					if(message.length() != 0)
						message.append(", ");
					message.append(drop.getType().toString());
					message.append("x");
					message.append(drop.getAmount());
				}
				damager.sendMessage(message.toString());
			}
		}
	}
}
