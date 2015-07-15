package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;
import cz.safaricraft.plugin.chat.ServerMsg;

public class BaseCommands {

	private final Main plugin = Main.getInstance();

	@CommandHandler(name = "help", aliases = { "?" }, minimumArgs = 0, maximumArgs = 0, description = "Displays all available commands.", usage = "Write /help or /? to display available commands.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.help")
	public void helpCommand(CommandSender sender, CommandContext args) {
		PlayerMsg.HelpHead(sender, "SafariCraft plugin");
		PlayerMsg.HelpMenu(sender, "/sethome", "", "- Nastavi home");
		PlayerMsg.HelpMenu(sender, "/home", "", "- Teleportuje Vas na home");
		PlayerMsg.HelpMenu(sender, "/warp", "", "- Zobrazi warp menu");
		PlayerMsg.HelpMenu(sender, "/spawn", "", "- Teleportuje vas na spawn");
		PlayerMsg.HelpMenu(sender, "/who", "", "-  Seznam online hracu.");
		PlayerMsg.HelpMenu(sender, "/getpos", "", "- Ukaze vasi pozici");
		PlayerMsg.HelpMenu(sender, "/afk", "", "- AFK");
		PlayerMsg.HelpMenu(sender, "/tell", "<player>",
				"- Napisete zpravu hraci");
		PlayerMsg.HelpMenu(sender, "/vip", "", "- Zobrazi VIP prikazy");
		PlayerMsg.HelpMenu(sender, "/help", "", "- Zobrazi tuto napovedu");
		PlayerMsg.HelpFoot(sender, "Created by SafariCraft.cz Team 2013");
	}

	@CommandHandler(name = "spawn", minimumArgs = 0, maximumArgs = 0, description = "Spawn.", usage = "Write /spawn to teleport spawn.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.spawn")
	public void spawnCommand(Player player, CommandContext args) {
		if (plugin.getConfig().getConfigurationSection("spawn") == null) {
			PlayerMsg.Error(player, "Spawn neni nastaven!");
		} else {
			World w = Bukkit.getWorld(plugin.getConfig().getString(
					"spawn.world"));
			double x = plugin.getConfig().getDouble("spawn.x");
			double y = plugin.getConfig().getDouble("spawn.y");
			double z = plugin.getConfig().getDouble("spawn.z");
			player.teleport(new Location(w, x, y, z));
			plugin.saveConfig();
			PlayerMsg.Success(player, "Byli jste teleportovani na spawn!");
		}
	}

	@CommandHandler(name = "tell", aliases = { "msg" }, minimumArgs = 0, maximumArgs = -1, description = "Send privte messages.", usage = "Write /tell <player> to send privte messages.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.tell")
	public void tellCommand(CommandSender sender, CommandContext args) {
		if (args.getArgs().size() < 2) {
			sender.sendMessage(ChatColor.RED + "/tell <player> <message>");
		}
		Player target = Bukkit.getPlayer(args.getArgs().get(0));
		if (sender instanceof ConsoleCommandSender) {
			if (target == null) {
				PlayerMsg.Error(sender, "Tento hrac je offline!");
			}
			StringBuilder msg = new StringBuilder();
			for (int i = 1; i < args.getArgs().size(); i++) {
				msg.append(" ");
				msg.append(args.getArgs().get(i));
			}
			target.sendMessage(ChatColor.RED + "[Server -> Vy]"
					+ ChatColor.RESET + msg);
		} else if (sender instanceof Player) {
			if (target == null) {
				PlayerMsg.Error(sender, "Tento hrac je offline!");
			}
			StringBuilder msg = new StringBuilder();
			for (int i = 1; i < args.getArgs().size(); i++) {
				msg.append(" ");
				msg.append(args.getArgs().get(i));
			}
			sender.sendMessage(ChatColor.YELLOW + "[Vy -> " + target.getName()
					+ "]" + ChatColor.RESET + msg);
			target.sendMessage(ChatColor.YELLOW + "[" + sender.getName()
					+ " -> Vy]" + ChatColor.RESET + msg);
		}
	}

	@CommandHandler(name = "getpos", minimumArgs = 0, maximumArgs = 0, description = "Get your pos.", usage = "Write /pos to get your position.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.getpos")
	public void PosCommand(Player player, CommandContext args) {
		Location loc = player.getLocation();
		PlayerMsg.HelpHead(player, "Vase pozice");
		PlayerMsg.Info(player, "X: " + loc.getBlockX());
		PlayerMsg.Info(player, "Y: " + loc.getBlockY());
		PlayerMsg.Info(player, "Z: " + loc.getBlockZ());
		PlayerMsg.Info(player, "Svet: " + loc.getWorld().getName());
	}

	@CommandHandler(name = "who", aliases = { "list" }, minimumArgs = 0, maximumArgs = 0, description = "Online players.", usage = "Write /who to display online players.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.who")
	public void WhoCommand(CommandSender sender, CommandContext args) {
		Player[] o = Bukkit.getOnlinePlayers();
		String list = "";
		int cap = Bukkit.getMaxPlayers();
		int i = 0;
		PlayerMsg.HelpHead(sender, "Online hraci " + o.length + "/" + cap);
		while (i < o.length) {
			Player p = o[i];
			{
				list += p.getName();
			}
			if (i == (o.length - 1)) {
				list += ".";
			} else {
				list += ", ";
			}
			i++;
		}
		sender.sendMessage(list);
	}

	@CommandHandler(name = "afk", minimumArgs = 0, maximumArgs = 0, description = "AFK.", usage = "Write /afk to on/off AFK.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.afk")
	public void AFKCommand(Player player, CommandContext args) {
		String name = player.getName();
		if (!Main.vanished.contains(name)) {
			Main.vanished.add(name);
			ServerMsg.Info("Hrac/ka " + name + " odesel/a od hry!");

		} else {
			Main.vanished.remove(name);
			ServerMsg.Info("Hrac/ka " + name + " je zpet!");
		}
	}
}
