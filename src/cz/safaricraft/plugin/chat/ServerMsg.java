package cz.safaricraft.plugin.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ServerMsg {

	public static void Info(String msg) {
		Bukkit.broadcastMessage(ChatColor.YELLOW + msg);
	}

	public static void Say(String msg) {
		Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.GREEN
				+ "SafariCraft" + ChatColor.WHITE + "] " + ChatColor.YELLOW
				+ msg);
	}

}
