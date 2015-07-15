package cz.safaricraft.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.safaricraft.plugin.chat.ChatManager;
import cz.safaricraft.plugin.commands.*;
import cz.safaricraft.plugin.listeners.*;
import cz.safaricraft.plugin.tools.AutoSaver;
import me.frostbitecz.frameworks.jcommands.CommandManager;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main instance;
	public static Logger logger = Logger.getLogger("Minecraft");
	public static ArrayList<String> unloged = new ArrayList<String>();
	public static ArrayList<String> unregistered = new ArrayList<String>();
	public static ArrayList<String> god = new ArrayList<String>();
	public static ArrayList<String> vanished = new ArrayList<String>();
	public static HashMap<String, Location> deaths = new HashMap<String, Location>();
	private static boolean _enabled = false;

	@Override
	public void onEnable() {
		logger.log(Level.INFO, "[SafariCraft] Registruji se prikazy!");
		CommandManager.registerCommands(this, new BaseCommands());
		CommandManager.registerCommands(this, new HomeCommand());
		CommandManager.registerCommands(this, new WarpCommand());
		CommandManager.registerCommands(this, new VIPCommands());
		CommandManager.registerCommands(this, new BuilderCommand());
		CommandManager.registerCommands(this, new HelperCommands());
		CommandManager.registerCommands(this, new ModCommands());
		CommandManager.registerCommands(this, new AdminCommands());
		CommandManager.registerCommands(this, new TeamCommands());

		logger.log(Level.INFO, "[SafariCraft] Registruji se eventy!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new EntityListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new VotifierListener(), this);
		logger.log(Level.INFO, "[SafariCraft] Nacita se config!");
		saveDefaultConfig();
		/*
		 * logger.log(Level.INFO,
		 * "[SafariCraft] Pripojuji se k MySQL databazi!"); while
		 * (!DatabaseManager.connected()) { if
		 * (DatabaseManager.getAttempsTillTimeout() == 0) {
		 * logger.log(Level.SEVERE, "[SafariCraft] Nelze se pripojit k db!");
		 * logger.log(Level.SEVERE, "[SafariCraft] Vypina se server!");
		 * getServer().shutdown(); return; } try {
		 * DatabaseManager.establishConnection(); } catch
		 * (ClassNotFoundException e) { logger.log(Level.SEVERE,
		 * "Attemp failed! Database driver not found!");
		 * logger.log(Level.SEVERE, e.toString()); } catch (SQLException e) {
		 * logger.log(Level.SEVERE,
		 * "Attemp failed! An error occured while connecting database!");
		 * logger.log(Level.SEVERE, e.toString()); } } logger.log(Level.INFO,
		 * "[SafariCraft] Pripojeni k db bylo vytvoreno!");
		 */
		logger.log(Level.INFO, "[SafariCraft] Loading announcements...");
		ChatManager.initializeAnnouncer();

		logger.log(Level.INFO, "[SafariCraft] Registruji se recepty!");
		Recipes.saddle();
		Recipes.nametag();
		Recipes.diamondhorse();
		Recipes.goldhorse();
		Recipes.ironhorse();

		logger.log(Level.INFO, "[SafariCraft] Aktivuje se AutoSaver!");
		AutoSaver.StartSaver();

		logger.log(Level.INFO, "[SafariCraft] Plugin byl aktivovan!");
	}

	@Override
	public void onDisable() {
		logger.log(Level.INFO, "[SafariCraft] Stopping announcer...");
		ChatManager.stopAnnouncer();
		logger.log(Level.INFO, "[SafariCraft] Plugin byl deaktivovan!");
	}

	public Main() {
		instance = this;
	}

	public static Main getInstance() {
		return instance;
	}

	public static boolean is_enabled() {
		return _enabled;
	}
}
