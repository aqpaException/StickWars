package de.mserver.stickwars.resources;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import de.mserver.stickwars.Main;

public class MapChanges {
	
	Main main;
	
	public MapChanges(Main plugin) {
		this.main = plugin;
	}
	
	public ArrayList<Player> freezed = new ArrayList<>();
	public Integer countdown = Integer.valueOf(300);
	public Integer mapchangetask;
	public boolean running;
	
	public void start() {
		if(!running) {
			running = true;
			countdown = Integer.valueOf(300);
			mapchangetask = Integer.valueOf(Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					if(countdown.intValue() >= 1) {
						if(countdown.intValue() == 1500) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e25 Minuten");
							}
						}
						if(countdown.intValue() == 1200) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e20 Minuten");
							}
						}
						if(countdown.intValue() == 900) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e15 Minuten");
							}
						}
						if(countdown.intValue() == 600) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e10 Minuten");
							}
						}
						if(countdown.intValue() == 300) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e5 Minuten");
							}
						}
						if(countdown.intValue() == 120) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e2 Minuten");
							}
						}
						if(countdown.intValue() == 60) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e1 Minute");
							}
						}
						if(countdown.intValue() == 30 || countdown.intValue() == 15 || countdown.intValue() == 10) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e" + countdown.intValue() + " Sekunden");
							}
						}
						if(countdown.intValue() == 5) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendMessage("§3StickWars §8» §fDie Map wechselt in §e5 Sekunden");
								all.playSound(all.getEyeLocation(), Sound.CLICK, 0.8F, 0.8F);
							}
						}
						if(countdown.intValue() == 3 || countdown.intValue() == 2 || countdown.intValue() == 1) {
							for(Player all: Bukkit.getOnlinePlayers()) {
								all.sendTitle("§fMapwechsel in", "§e" + countdown.intValue() + " Sekunden");
								all.playSound(all.getEyeLocation(), Sound.CLICK, 0.8F, 0.8F);
								freezed.add(all);
							}
						}
					}
					if(countdown.intValue() == 0) {
						if(main.isMap1()) {
							main.setMap2();
							for(Player all: Bukkit.getOnlinePlayers()) {
								main.setupPlayer(all);
								all.playSound(all.getEyeLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
							}
						} else if (main.isMap2()) {
							main.setMap3();
							for(Player all: Bukkit.getOnlinePlayers()) {
								main.setupPlayer(all);
								all.playSound(all.getEyeLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
							}
						} else if (main.isMap3()) {
							main.setMap1();
							for(Player all: Bukkit.getOnlinePlayers()) {
								main.setupPlayer(all);
								all.playSound(all.getEyeLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
							}
						}
						countdown = Integer.valueOf(301);
						freezed.clear();
						for(Player all: Bukkit.getOnlinePlayers()) {
							all.sendMessage("§3StickWars §8» §aDie Map wurde gewechselt!");
							all.sendMessage("§3StickWars §8» §aAlle Spieler wurden teleportiert.");
							main.updateScoreboard(all);
						}
					}
					countdown = Integer.valueOf(countdown.intValue() - 1);
					for(Player all: Bukkit.getOnlinePlayers()) {
						double counter = countdown.intValue();
						int minutes = (int)(counter / 60.0D);
						int sekunden = countdown.intValue() - minutes * 60 + 1;
						if(sekunden <= 9) {
							main.sendActionBar(all, "§f« §c§lMapwechsel in §8: §6§l" + minutes + ".0" + sekunden + " Minuten §f»");
						} else {
							main.sendActionBar(all, "§f« §c§lMapwechsel in §8: §6§l" + minutes + "." + sekunden + " Minuten §f»");
						}
						if(minutes == 0) {
							if(sekunden <= 9) {
								main.sendActionBar(all, "§f« §c§lMapwechsel in §8: §6§l0" + sekunden + " Sekunden §f»");
							} else {
								main.sendActionBar(all, "§f« §c§lMapwechsel in §8: §6§l" + sekunden + " Sekunden §f»");
							}
						}
					}
				}
			}, 0L, 20L));
		}
	}
}
