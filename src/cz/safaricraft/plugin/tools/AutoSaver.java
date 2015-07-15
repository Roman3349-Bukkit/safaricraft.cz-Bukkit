package cz.safaricraft.plugin.tools;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.World;

import cz.safaricraft.plugin.Main;

public class AutoSaver {
	
	public static void StartSaver() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {
				Main.logger.log(Level.INFO, "[SafariCraft] Ukladam data.");
				SaveWorlds();
			}
		}, 18000, 18000);
	}

	public static void SaveWorlds() {
		for (World world : Bukkit.getWorlds()) {
			world.save();
			Main.logger.log(Level.INFO,
					"[SafariCraft] Ukladam svet " + world.getName() + ".");
		}
		Bukkit.savePlayers();
	}

}
