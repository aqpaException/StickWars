package de.mserver.stickwars.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mserver.stickwars.Main;

public class COMMAND_swadmin implements CommandExecutor {
	
	Main main;
	
	public COMMAND_swadmin(Main plugin) {
		this.main = plugin;
	}
	
	public boolean onCommand(CommandSender commandsender, Command arg1, String arg2, String[] args) {
		if(!(commandsender instanceof Player)) {
			commandsender.sendMessage("§3StickWars §8» §cDiesen Befehl kann nur ein Spieler ausführen!");
			return false;
		}
		Player sender = (Player)commandsender;
		if(sender.hasPermission("misterserver.owner") || sender.hasPermission("misterserver.admin") || sender.getName().equalsIgnoreCase("ausgebildet")) {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("setlocation") && args[1].equalsIgnoreCase("Map1")) {
					main.getLocationhandler().setLocation("Map1", sender.getLocation());
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast die Location für §eMap1 §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setlocation") && args[1].equalsIgnoreCase("Map2")) {
					main.getLocationhandler().setLocation("Map2", sender.getLocation());
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast die Location für §eMap2 §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setlocation") && args[1].equalsIgnoreCase("Map3")) {
					main.getLocationhandler().setLocation("Map3", sender.getLocation());
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast die Location für §eMap3 §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setlocation") && args[1].equalsIgnoreCase("Topgolem")) {
					main.getLocationhandler().setLocation("TopGolem", sender.getLocation());
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast die Location für §eTopgolem §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setground") && args[1].equalsIgnoreCase("Map1")) {
					main.getLocationhandler().setGround(sender.getLocation(), "Map1");
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast den Ground für §eMap1 §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setground") && args[1].equalsIgnoreCase("Map2")) {
					main.getLocationhandler().setGround(sender.getLocation(), "Map2");
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast den Ground für §eMap2 §agesetzt!");
				} else if(args[0].equalsIgnoreCase("setground") && args[1].equalsIgnoreCase("Map3")) {
					main.getLocationhandler().setGround(sender.getLocation(), "Map3");
					sender.playSound(sender.getEyeLocation(), Sound.NOTE_BASS_DRUM, 0.8F, 0.8F);
					sender.sendMessage("§3StickWars §8» §aDu hast den Ground für §eMap3 §agesetzt!");
				} else {
					sender.sendMessage("§3StickWars §8» §7AdminCommand - Hilfeübersicht");
					sender.sendMessage("§3StickWars §8» ");
					sender.sendMessage("§3StickWars §8» §e/swadmin <setlocation> <Location>");
					sender.sendMessage("§3StickWars §8» §e/swadmin <setground> <Map>");
					sender.sendMessage("§3StickWars §8» ");
				}
			} else {
				sender.sendMessage("§3StickWars §8» §7AdminCommand - Hilfeübersicht");
				sender.sendMessage("§3StickWars §8» ");
				sender.sendMessage("§3StickWars §8» §e/swadmin <setlocation> <Location>");
				sender.sendMessage("§3StickWars §8» §e/swadmin <setground> <Map>");
				sender.sendMessage("§3StickWars §8» ");
			}
		} else {
			return false;
		}
		return true;
	}

}
