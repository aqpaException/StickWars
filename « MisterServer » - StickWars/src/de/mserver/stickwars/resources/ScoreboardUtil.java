package de.mserver.stickwars.resources;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import de.mserver.stickwars.Main;
import net.minecraft.server.v1_8_R3.Packet;

public class ScoreboardUtil {
	
	Main main;
	
	public ScoreboardUtil(Main plugin) {
		this.main = plugin;
	}
	
	public void createScoreboard(Player player) {
		
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective obj = board.registerNewObjective(player.getName(), player.getName());
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lDeine Statistiken");
		
		Score leer1 = obj.getScore("§1");
		Score kills = obj.getScore("§cDeine Kills §8:");
		Score kills1 = obj.getScore("§8» §6289 Kills");
		
		Score leer2 = obj.getScore("§2");
		Score tode = obj.getScore("§cDeine Tode §8:");
		Score tode1 = obj.getScore("§8» §691 Tode");
		
		Score leer3 = obj.getScore("§3");
		Score kd = obj.getScore("§cDeine K/D §8:");
		Score kd1 = obj.getScore("§8» §62.81");
		
		Score leer4 = obj.getScore("§4");
		Score Ranking = obj.getScore("§cRanking §8:");
		Score Ranking1 = obj.getScore("§8» §6Platz 11");
		
		Score leer5 = obj.getScore("§5");
		Score line = obj.getScore("§7---------------");
		Score line1 = obj.getScore("§3misterserver.tv");
		
		leer1.setScore(8);
		kills.setScore(7);
		kills1.setScore(6);
		leer2.setScore(5);
		tode.setScore(4);
		tode1.setScore(3);
		leer3.setScore(2);
		kd.setScore(1);
		kd1.setScore(0);
		leer4.setScore(-1);
		Ranking.setScore(-2);
		Ranking1.setScore(-3);
		leer5.setScore(-4);
		line.setScore(-5);
		line1.setScore(-6);
		
		player.setScoreboard(board);
		
	}
	
	public void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}

}
