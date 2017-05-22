package de.mserver.stickwars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import de.mserver.stickwars.commands.COMMAND_changemap;
import de.mserver.stickwars.commands.COMMAND_swadmin;
import de.mserver.stickwars.events.JoinEvent;
import de.mserver.stickwars.resources.ItemBuilder;
import de.mserver.stickwars.resources.LocationHandler;
import de.mserver.stickwars.resources.MapChanges;
import de.mserver.stickwars.resources.Maps;
import de.mserver.stickwars.resources.ScoreboardUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Main extends JavaPlugin {
	
	public Maps maps;
	private LocationHandler locationhandler;
	private ScoreboardUtil scoreboardutil;
	private MapChanges mapchanges;
	
	public void onEnable() {
		
		this.locationhandler = new LocationHandler(this);
		this.scoreboardutil = new ScoreboardUtil(this);
		this.mapchanges = new MapChanges(this);
		
		setMap1();
		
		if(Bukkit.getOnlinePlayers().size() >= 2) {
			getMapchanges().start();
		}
		
		getCommand("swadmin").setExecutor(new COMMAND_swadmin(this));
		getCommand("changemap").setExecutor(new COMMAND_changemap(this));
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
		
	}
	
	public void onDisable() {
		
	}
	
	public boolean isMap1() {
		if(maps == Maps.MAP1) {
			return true;
		}
		return false;
	}
	
	public boolean isMap2() {
		if(maps == Maps.MAP2) {
			return true;
		}
		return false;
	}
	
	public boolean isMap3() {
		if(maps == Maps.MAP3) {
			return true;
		}
		return false;
	}
	
	public void setMap1() {
		maps = Maps.MAP1;
	}
	
	public void setMap2() {
		maps = Maps.MAP2;
	}
	
	public void setMap3() {
		maps = Maps.MAP3;
	}
	
	public LocationHandler getLocationhandler() {
		return locationhandler;
	}
	
	public ScoreboardUtil getScoreboardutil() {
		return scoreboardutil;
	}
	
	public void teleport(Player player, Location location) {
		new BukkitRunnable() {
			public void run() {
				try {
					player.teleport(location);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskLater(this, 3L);
	}
	
	public void setupPlayer(Player player) {
		player.getInventory().clear();
		player.setMaxHealth(20);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.SURVIVAL);
		player.setLevel(0);
		player.getInventory().setArmorContents(null);
		player.getInventory().setItem(0, new ItemBuilder(Material.STICK).setDisplayName("§6§lMagicStick").addUnsafeEnchantment(Enchantment.KNOCKBACK, 1).getStack());
		player.setExp(0);
		if(isMap1()) {
			teleport(player, getLocationhandler().getLocation("Map1"));
		} else if (isMap2()) {
			teleport(player, getLocationhandler().getLocation("Map2"));
		} else if (isMap3()) {
			teleport(player, getLocationhandler().getLocation("Map3"));
		}
	}
	
	public void sendActionBar(Player p, String message) {
	    PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
	    
	    IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
	    PacketPlayOutChat packet = new PacketPlayOutChat(icbc, (byte)2);
	    
	    connection.sendPacket(packet);
	}
	
	public void updateScoreboard(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		board.getObjective(player.getName()).unregister();
		getScoreboardutil().createScoreboard(player);
	}
	
	public MapChanges getMapchanges() {
		return mapchanges;
	}
	
	public void respawn(Player player, int time) {
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			public void run() {
				((CraftPlayer)player).getHandle().playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
			}
		}, time);
	}
}
