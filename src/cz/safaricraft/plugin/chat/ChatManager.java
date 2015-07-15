package cz.safaricraft.plugin.chat;

import java.util.List;
import java.util.logging.Level;

import cz.safaricraft.plugin.Main;

public class ChatManager {

	/**
	 * @author FrostbiteCZ (Jason Skyedge)
	 */
	
	private static Main plugin = Main.getInstance();
	private static Announcer chatAnnouncer = null;

	public static void initializeAnnouncer() {
		List<String> announcements = plugin.getConfig().getStringList(
				"chat.announcer.messages");
		int interval = plugin.getConfig().getInt("chat.announcer.interval");
		if (announcements.isEmpty()) {
			Main.logger
					.log(Level.INFO,
							"Did not found any announces, stopping announcer initialization.");
			return;
		}
		chatAnnouncer = new Announcer(announcements, interval);
		chatAnnouncer.start();
		Main.logger.log(Level.INFO, "Announcer succesfuly initialized! ("
				+ interval + " seconds delay with " + announcements.size()
				+ " messages.)");
	}

	public static void stopAnnouncer() {
		if (chatAnnouncer == null)
			return;
		chatAnnouncer.terminate();
		chatAnnouncer = null;
	}

	public static Announcer getChatAnnouncer() {
		return chatAnnouncer;
	}
}