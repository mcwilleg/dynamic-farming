package com.neo.dynfarming.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public enum Message {
	NO_PERMISSION("&cYou do not have permission for %s."),
	PLAYER_ONLY("&cThis command is for players only.");
	
	private String message;
	
	Message(String message) {
		this.message = message;
	}
	
	public void send(CommandSender sender, Object... args) {
		if(sender == null) {
			return;
		}
		
		String msg = this.message;
		for(Object arg : args) {
			msg = msg.replaceFirst("%[ds]", String.valueOf(arg));
		}
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		
		sender.sendMessage(msg);
	}
	
	public void sendDelayed(Plugin plugin, long delay, CommandSender sender, Object... args) {
		new MessageRunnable(sender, args).runTaskLater(plugin, delay);
	}
	
	private class MessageRunnable extends BukkitRunnable {
		private CommandSender sender;
		private Object[] args;
		
		private MessageRunnable(CommandSender sender, Object... args) {
			this.sender = sender;
			this.args = args;
		}
		
		@Override
		public void run() {
			send(sender, args);
		}
	}
}
