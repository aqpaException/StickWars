package de.mserver.stickwars.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import de.mserver.stickwars.Main;

public class JoinEvent implements Listener {
	
	Main main;
	
	public JoinEvent(Main plugin) {
		this.main = plugin;
	}
	
	public HashMap<Player, Player> combat = new HashMap<>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.setJoinMessage("§a» §7" + event.getPlayer().getDisplayName());
		main.getScoreboardutil().createScoreboard(event.getPlayer());
		main.setupPlayer(event.getPlayer());
		if(Bukkit.getOnlinePlayers().size() >= 2) {
			main.getMapchanges().start();
		}
		if(main.getMapchanges().freezed.size() != 0) {
			main.getMapchanges().freezed.add(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage("§c« §7" + event.getPlayer().getDisplayName());
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		board.getObjective(event.getPlayer().getName()).unregister();
		if(Bukkit.getOnlinePlayers().size() <= 2) {
			if(main.getMapchanges().running) {
				Bukkit.getScheduler().cancelTask(main.getMapchanges().mapchangetask.intValue());
			}
			for(Player all: Bukkit.getOnlinePlayers()) {
				all.sendMessage("§3StickWars §8» §cDas automatische MapSystem wurde angehalten!");
				all.sendMessage("§3StickWars §8» §cEs sind zu wenig Spieler online!");
			}
			main.getMapchanges().running = false;
		}
		if(main.getMapchanges().freezed.contains(event.getPlayer())) {
			main.getMapchanges().freezed.remove(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(main.getMapchanges().freezed.contains(event.getPlayer())) {
			Location comeFrom = event.getFrom();
			Location goTo = event.getTo();
			double x = Math.floor(comeFrom.getX());
			double z = Math.floor(comeFrom.getZ());
			if ((Math.floor(goTo.getX()) != x) || (Math.floor(goTo.getZ()) != z)) {
		        event.getPlayer().teleport(comeFrom);
		      }
		}
		if(main.isMap1()) {
			if(event.getPlayer().getLocation().getY() <= main.getLocationhandler().getGround("Map1")) {
				event.getPlayer().setHealth(0);
			}
		} else if (main.isMap2()) {
			if(event.getPlayer().getLocation().getY() <= main.getLocationhandler().getGround("Map2")) {
				event.getPlayer().setHealth(0);
			}
		} else if (main.isMap3()) {
			if(event.getPlayer().getLocation().getY() <= main.getLocationhandler().getGround("Map3")) {
				event.getPlayer().setHealth(0);
			}
		}
	}
	
	@EventHandler
	public void onEntity(EntityDamageEvent event) {
		if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
			event.setCancelled(true);
		}
		if(event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
			event.setCancelled(true);
		}
		if(event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
			event.setCancelled(true);
		}
		if(event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		Player death = (Player)event.getEntity();
		main.respawn(death, 1);
		if(combat.containsKey(death)) {
			Player killer = (Player)combat.get(death);
			killer.setLevel(killer.getLevel() + 1);
			main.sendActionBar(death, "§c§l» §f§lDer Spieler §e" + killer.getName() + " §fhat dich getötet.");
			main.sendActionBar(killer, "§a§l» §f§lDu hast §e" + death.getName() + " §fgetötet.");
			combat.remove(death);
		} else {
			main.sendActionBar(death, "§c§l» §f§lDu bist gestorben.");
		}
		event.getDrops().clear();
		event.setDroppedExp(0);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		main.setupPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player damage = (Player)event.getEntity();
			damage.setHealth(20);
			combat.put(damage, (Player)event.getDamager());
			new BukkitRunnable() {
				public void run() {
					combat.remove(damage);
				}
			}.runTaskLater(main, 80L);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			event.setCancelled(false);
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeaves(LeavesDecayEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onLevel(PlayerLevelChangeEvent event) {
		Player player = event.getPlayer();
	    if ((player.getLevel() == 5) || (player.getLevel() == 10) || (player.getLevel() == 15) || (player.getLevel() == 20) || (player.getLevel() == 25) || 
	      (player.getLevel() == 30) || (player.getLevel() == 35) || (player.getLevel() == 40) || (player.getLevel() == 45))
	    {
	      for(Player all: Bukkit.getOnlinePlayers()) {
	    	  all.sendMessage("§3StickWars §8» §e" + player.getName() + " §fhat einen §b" + player.getLevel() + " §fKillstreak erreicht!");
	      
	      Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
	      FireworkEffect fireeffect = FireworkEffect.builder().withColor(Color.AQUA)
	        .withColor(Color.BLACK)
	        .withColor(Color.BLUE)
	        .withColor(Color.FUCHSIA)
	        .withColor(Color.GRAY)
	        .withColor(Color.GREEN)
	        .withColor(Color.LIME)
	        .withColor(Color.MAROON)
	        .withColor(Color.NAVY)
	        .withColor(Color.OLIVE)
	        .withColor(Color.ORANGE)
	        .withColor(Color.PURPLE)
	        .withColor(Color.RED)
	        .withColor(Color.SILVER)
	        .withColor(Color.TEAL)
	        .withColor(Color.YELLOW)
	        .withColor(Color.WHITE).flicker(true).trail(true).with(FireworkEffect.Type.STAR).build();
	      
	      FireworkMeta fmeta = firework.getFireworkMeta();
	      fmeta.addEffect(fireeffect);
	      fmeta.setPower(1);
	      firework.setFireworkMeta(fmeta);
	    }
	  }
	}
}
