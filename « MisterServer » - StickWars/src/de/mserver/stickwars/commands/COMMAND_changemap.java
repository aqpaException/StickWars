package de.mserver.stickwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mserver.stickwars.Main;

public class COMMAND_changemap implements CommandExecutor {
	
	Main main;
	
	public COMMAND_changemap(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender commandsender, Command arg1, String arg2, String[] args) {
		if(!(commandsender instanceof Player)) {
			commandsender.sendMessage("§3StickWars §8» §cDiesen Befehl kannst du nur als Spieler nutzen!");
			return false;
		}
		Player sender = (Player)commandsender;
		if(sender.hasPermission("misterserver.owner") || sender.hasPermission("misterserver.admin") || sender.hasPermission("misterserver.moderator") || sender.hasPermission("misterserver.tsupporter")
				|| sender.hasPermission("misterserver.entwickler") || sender.hasPermission("misterserver.builder") || sender.hasPermission("misterserver.hilfe") || sender.hasPermission("misterserver.freund") 
				|| sender.hasPermission("misterserver.vipplus")) {
			if(args.length == 0) {
				if(main.getMapchanges().countdown.intValue() >= 15) {
					for(Player all: Bukkit.getOnlinePlayers()) {
						all.sendMessage("§3StickWars §8» §e" + sender.getName() + " §fhat die Mapzeit verkürzt!");
					}
					main.getMapchanges().countdown = Integer.valueOf(5);
				} else {
					sender.sendMessage("§3StickWars §8» §cDie Mapzeit liegt unter 15 Sekunden");
				}
			} else {
				sender.sendMessage("§3StickWars §8» §cNutze §8[§e/changemap§8]");
			}
		} else {
			return false;
		}
		return true;
	}

}
