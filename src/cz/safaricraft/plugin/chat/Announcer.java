package cz.safaricraft.plugin.chat;

import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import cz.safaricraft.plugin.Main;

public class Announcer extends Thread {

	/**
	 * @author FrostbiteCZ (Jason Skyedge)
	 */

	private List<String> announcements;
	private long interval;
	private volatile boolean running = false;
	private int key = 0;

	public Announcer(List<String> announcements, long interval) {
		super();
		for (int i = 0; i < announcements.size(); i++) {
			String announcement = announcements.get(i);
			announcement = ChatColor.translateAlternateColorCodes('&',
					announcement);
			announcements.set(i, announcement);
		}
		this.announcements = announcements;
		this.interval = interval * 1000;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(interval);
				if (!running)
					return;
				Bukkit.broadcastMessage(announcements.get(key));
				key++;
				if (key == announcements.size())
					key = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
				Main.logger.log(Level.WARNING,
						"Announcer thread was interrupted!");
				running = false;
			}
		}
	}

	public boolean running() {
		return running;
	}

	public void terminate() {
		running = false;
	}
}