package cz.safaricraft.plugin.listeners;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {

	private Main plugin = Main.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		String name = player.getName().toLowerCase();
		String IP = e.getAddress().toString();
		if (name.equals("")) {
			e.disallow(Result.KICK_OTHER, "Prosim zvolte si jmeno!");
			return;
		}
		if (!name.matches("[a-zA-Z0-9_\\-\\.]{2,16}")) {
			e.disallow(Result.KICK_OTHER,
					"Prosim zvolte si jmeno slozene jen z povolenych znaku! a-z A-Z 0-9 ' _ - .");
			return;
		}
		if (player.isOnline()
				&& !(Main.unregistered.contains(name) || Main.unloged
						.contains(name))) {
			e.disallow(Result.KICK_OTHER, "Hrac s timto jmenem uz je online!");
			return;
		}
		if (IP.equals("195.210.247.") || IP.equals("109.182.75.")
				|| IP.equals("95.173.207.") || IP.equals("194.165.119.")) {
			e.disallow(Result.KICK_OTHER,
					"Vase IP je zabanovana z duvodu hackerskych utoku.");
			return;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		PlayerInventory pi = player.getInventory();
		String name = player.getName();
		String p = name.toLowerCase();
		Location loc = player.getLocation();
		String IP = player.getAddress().getAddress().getHostAddress();
		String wName = loc.getWorld().getName();
		String sName = plugin.getConfig().getString("serverName");
		if (!(player.hasPlayedBefore())) {
			plugin.getConfig().createSection("player." + p + ".ip");
			plugin.getConfig().set("player." + p + ".ip", IP);
			plugin.getConfig().createSection("player." + p + ".home." + "x");
			plugin.getConfig().set("player." + p + ".home.x", loc.getX());
			plugin.getConfig().createSection("player." + p + ".home." + "y");
			plugin.getConfig().set("player." + p + ".home.y", loc.getY());
			plugin.getConfig().createSection("player." + p + ".home." + "z");
			plugin.getConfig().set("player." + p + ".home.z", loc.getZ());
			plugin.getConfig()
					.createSection("player." + p + ".home." + "world");
			plugin.getConfig().set("player." + p + ".home.world", wName);
			plugin.saveConfig();
			pi.addItem(new ItemStack(Material.STONE_SWORD, 1));
			pi.addItem(new ItemStack(Material.STONE_PICKAXE, 1));
			pi.addItem(new ItemStack(Material.STONE_SPADE, 1));
			pi.addItem(new ItemStack(Material.STONE_AXE, 1));
			pi.addItem(new ItemStack(Material.PUMPKIN_PIE, 16));
			pi.setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
			pi.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
			pi.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
			pi.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
			e.setJoinMessage(ChatColor.YELLOW + "Hrac/ka " + name
					+ " se pripojil/a poprve!");
		} else {
			e.setJoinMessage(ChatColor.YELLOW + "Hrac/ka " + name
					+ " se pripojil/a!");
		}
		PlayerMsg.Welcome(player, sName);
		Player[] players = Bukkit.getOnlinePlayers();
		String toSend = "";
		int i = 0;
		while (i < players.length) {
			if (i == 0) {
				toSend += players[i].getName();
			} else {
				toSend += ", " + players[i].getName();
			}
			i++;
		}
		player.sendMessage(ChatColor.DARK_GREEN + "Online hraci: "
				+ ChatColor.GRAY + toSend + ".");
		if (plugin.getConfig().getString("player." + p + ".password") == null) {
			Main.unregistered.add(p);
			PlayerMsg.Error(player,
					"Zaregistrujte se pomoci <heslo> <heslo>");
		} else {
			Main.unloged.add(p);
			PlayerMsg.Error(player, "Prihlaste se pomoci /login <heslo>");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		String pName = player.getName();
		String name = pName.toLowerCase();
		e.setQuitMessage(ChatColor.YELLOW + "Hrac/ka " + pName
				+ " se odpojil/a!");
		Main.vanished.remove(name);
		if (Main.unloged.contains(name)) {
			Main.unloged.remove(name);
		}
		if (Main.unregistered.contains(name)) {
			Main.unregistered.remove(name);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent e) {
		Player player = e.getPlayer();
		String pName = player.getName();
		String name = pName.toLowerCase();
		e.setLeaveMessage(ChatColor.YELLOW + "Hrac/ka " + pName
				+ " se odpojila!");
		Main.vanished.remove(name);
		if (Main.unloged.contains(name)) {
			Main.unloged.remove(name);
		}
		if (Main.unregistered.contains(name)) {
			Main.unregistered.remove(name);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if (player instanceof Player) {
			String name = player.getName().toLowerCase();
			Location loc = player.getLocation();
			if (Main.deaths.containsKey(name)) {
				Main.deaths.remove(name);
				Main.deaths.put(name, loc);
			} else {
				Main.deaths.put(name, loc);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		String name = e.getPlayer().getName().toLowerCase();
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		String name = player.getName().toLowerCase();
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			Location from = e.getFrom();
			Location to = e.getTo();
			if (!(from.getX() == to.getX() && from.getY() == to.getY() && from
					.getZ() == to.getZ())) {
				if (!player.teleport(from)) {
					e.setCancelled(true);
				}
			}

		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		String name = e.getPlayer().getName().toLowerCase();
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String name = player.getName().toLowerCase();
		String msg = e.getMessage();
		String cmd = msg.split(" ")[0];
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			if (cmd.equalsIgnoreCase("/login")
					|| cmd.equalsIgnoreCase("/register")
					|| cmd.equalsIgnoreCase("/help")) {
			} else {
				e.setCancelled(true);
				PlayerMsg
						.Error(player, "Prihlaste se pomoci /login <heslo>");
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		String name = player.getName().toLowerCase();
		String msg = e.getMessage();
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			e.setCancelled(true);
		}
		e.setMessage(ChatColor.translateAlternateColorCodes('&', msg));
		if (msg.startsWith("@")) {
			e.setCancelled(true);
			player.chat("/tell " + msg.substring(1));
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		String name = e.getPlayer().getName().toLowerCase();
		if (Main.unloged.contains(name) || Main.unregistered.contains(name)) {
			e.setCancelled(true);
		}
	}
}
