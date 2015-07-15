package cz.safaricraft.plugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import cz.safaricraft.plugin.Main;

public class BlockListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		for (int i = 0; i <= 3; i++) {
			String linie = e.getLine(i);
			linie = linie.replace("&", "§");
			e.setLine(i, linie);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		String name = e.getPlayer().getName().toLowerCase();
		if (Main.unloged.contains(name) || (Main.unregistered.contains(name))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		String name = e.getPlayer().getName().toLowerCase();
		if (Main.unloged.contains(name) || (Main.unregistered.contains(name))) {
			e.setCancelled(true);
		}
	}
}
