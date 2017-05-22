package de.mserver.stickwars.resources;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.mserver.stickwars.Main;

public class LocationHandler {
	
	Main main;
	
	public LocationHandler(Main plugin) {
		this.main = plugin;
	}

	public void setLocation(String locationname, Location location) {
		File file = new File("plugins/LobbySystem", "locations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set(locationname + ".world", location.getWorld().getName());
		cfg.set(locationname + ".x", Double.valueOf(location.getX()));
	    cfg.set(locationname + ".y", Double.valueOf(location.getY()));
	    cfg.set(locationname + ".z", Double.valueOf(location.getZ()));
	    cfg.set(locationname + ".yaw", Float.valueOf(location.getYaw()));
	    cfg.set(locationname + ".pitch", Float.valueOf(location.getPitch()));
	    try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Location getLocation(String locationname) {
		File file = new File("plugins/LobbySystem", "locations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Location location = null;
		String world = cfg.getString(locationname + ".world");
		double x = cfg.getDouble(locationname + ".x");
	    double y = cfg.getDouble(locationname + ".y");
	    double z = cfg.getDouble(locationname + ".z");
	    float yaw = (float)cfg.getDouble(locationname + ".yaw");
	 	float pitch = (float)cfg.getDouble(locationname + ".pitch");
		location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		return location;
	}
	
	public void setGround(Location location, String map) {
		File file = new File("plugins/LobbySystem", "locations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		try {
			cfg.set("StickWars.Ground." + map, location.getY());
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getGround(String map) {
		File file = new File("plugins/LobbySystem", "locations.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		double ground = cfg.getDouble("StickWars.Ground." + map);
		return ground;
	}
}
