package cz.safaricraft.plugin.commands;

import java.util.List;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

public class WarpCommand {

	private final Main plugin = Main.getInstance();

	@CommandHandler(name = "warp", minimumArgs = 0, maximumArgs = -1, description = "Warp.", usage = "Write /warp to warp.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.warp")
	public void warpCommand(Player player, CommandContext args) {
		Location loc = player.getLocation();
		if (args.getArgs().isEmpty()) {
			player.sendMessage(ChatColor.GRAY + "**********" + ChatColor.AQUA
					+ " WARP PRIKAZY " + ChatColor.GRAY + "**********");
			player.sendMessage(ChatColor.AQUA + "/warp" + ChatColor.GRAY
					+ " <jmeno>" + ChatColor.RESET + " - "
					+ "Premisti Vas na dany warp");
			player.sendMessage(ChatColor.AQUA + "/warp list" + ChatColor.RESET
					+ " - " + "Vypise list warpu");
			if (player.hasPermission("safaricraft.command.warp.admin")) {
				player.sendMessage(ChatColor.AQUA + "/warp set"
						+ ChatColor.GRAY + " <jmeno>" + ChatColor.RESET + " - "
						+ "Vytvori warp na vasi pozici");
			}
		} else if (args.getArgs().size() == 1) {
			if (args.getArgs().get(0).equalsIgnoreCase("list")) {
				List<String> Warps = plugin.getConfig().getStringList(
						"warps.list");
				if (Warps != null) {
					player.sendMessage(ChatColor.GRAY + "**********"
							+ ChatColor.AQUA + " SEZNAM WARPU "
							+ ChatColor.GRAY + "**********");
					for (String s : Warps) {
						player.sendMessage(s);
					}
				} else {
					PlayerMsg.Error(player, "Nebyly nalezeny zadne warpy!");
				}
			} else {
				if (plugin.getConfig().getStringList("warps.list")
						.contains(args.getArgs().get(0))) {
					double x = plugin.getConfig().getDouble(
							"warps.ports." + args.getArgs().get(0) + ".x");
					double y = plugin.getConfig().getDouble(
							"warps.ports." + args.getArgs().get(0) + ".y");
					double z = plugin.getConfig().getDouble(
							"warps.ports." + args.getArgs().get(0) + ".z");
					World world = Bukkit.getWorld(plugin.getConfig().getString(
							"warps.ports." + args.getArgs().get(0) + ".world"));
					player.teleport(new Location(world, x, y, z));
					PlayerMsg.Success(player, "Byl jste teleportovan na: "
							+ args.getArgs().get(0));
				} else {
					PlayerMsg.Error(player, "Nebyl nalezen warp se jmenem: "
							+ args.getArgs().get(0));
				}
			}
		} else if (args.getArgs().size() == 2) {
			if (args.getArgs().get(0).equalsIgnoreCase("set")) {
				if (player.hasPermission("safaricraft.command.warp.admin")) {
					List<String> List = plugin.getConfig().getStringList(
							"warps.list");
					if (!List.contains(args.getArgs().get(1))) {
						double x = loc.getX();
						double y = loc.getY();
						double z = loc.getZ();
						String world = player.getWorld().getName();
						String name = args.getArgs().get(1);
						List<String> Warps = plugin.getConfig().getStringList(
								"warps.list");
						Warps.add(name);
						plugin.getConfig().createSection("warps.ports." + name);
						plugin.getConfig().createSection(
								"warps.ports." + name + ".x");
						plugin.getConfig().set("warps.ports." + name + ".x", x);
						plugin.getConfig().createSection(
								"warps.ports." + name + ".y");
						plugin.getConfig().set("warps.ports." + name + ".y", y);
						plugin.getConfig().createSection(
								"warps.ports." + name + ".z");
						plugin.getConfig().set("warps.ports." + name + ".z", z);
						plugin.getConfig().createSection(
								"warps.ports." + name + ".world");
						plugin.getConfig().set(
								"warps.ports." + name + ".world", world);
						plugin.getConfig().set("warps.list", Warps);
						plugin.saveConfig();
						PlayerMsg.Success(player,
								"Uspesne jste vytvoril warp: " + name);
					} else {
						PlayerMsg.Error(player, "Tento warp jiz existuje!");
					}
				} else {
					PlayerMsg.Error(player, "Nemate dostatecna opravneni!");
				}
			}
		}
	}

}
