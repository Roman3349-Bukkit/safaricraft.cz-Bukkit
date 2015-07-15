package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.chat.PlayerMsg;
import cz.safaricraft.plugin.chat.ServerMsg;

public class TeamCommands {

	@CommandHandler(name = "tp", minimumArgs = 0, maximumArgs = 1, description = "Teleport to online player.", usage = "Write /tp to teleport to online player.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.tp")
	public void tpCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.Error(player, "Nezadali jste jmeno hrace!");
		}
		if (args.getArgs().size() == 1) {
			Player target = Bukkit.getPlayer(args.getArgs().get(0));
			if (target == null) {
				PlayerMsg.Error(player, "Tento hrac je offline!");
			} else {
				player.teleport(target.getLocation());
				PlayerMsg.Success(
						player,
						"Byl/a jste teleportovan/a na hrace/ku "
								+ target.getName());
			}
		}
	}

	@CommandHandler(name = "tphere", minimumArgs = 0, maximumArgs = 1, description = "Teleport online player to you.", usage = "Write /tphere to teleport online player to you.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.tp")
	public void tphereCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.Error(player, "Nezadali jste jmeno hrace!");
		}
		if (args.getArgs().size() == 1) {
			Player target = Bukkit.getPlayer(args.getArgs().get(0));
			if (target == null) {
				PlayerMsg.Error(player, "Tento hrac je offline!");
			} else {
				target.teleport(player.getLocation());
				PlayerMsg.Success(player, "Hrac " + target.getName()
						+ " byl teleportovan na vasi pozici");
			}
		}
	}

	@CommandHandler(name = "tppos", minimumArgs = 0, maximumArgs = 1, description = "Teleport to position.", usage = "Write /tppos to teleport to position.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.tp")
	public void tpposCommand(Player player, CommandContext args) {
		if (args.getArgs().size() == 3) {
			int x = Integer.parseInt(args.getArgs().get(0));
			int y = Integer.parseInt(args.getArgs().get(1));
			int z = Integer.parseInt(args.getArgs().get(2));
			player.teleport(new Location(player.getWorld(), x, y, z));
			PlayerMsg.Success(player,
					"Uspesne si by teleportovan na souradnice x=" + x + " y="
							+ y + " z=" + z);
		} else {
			PlayerMsg.Error(player, "/tppos <x> <y> <z>");
		}
	}

	@CommandHandler(name = "say", minimumArgs = 0, maximumArgs = -1, description = "Say message as server.", usage = "Write /say <msg> to send messages as server.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.say")
	public void sayCommand(CommandSender sender, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.Error(sender, "/say <msg>");
		} else if (args.getArgs().size() >= 1) {
			String msg = "";
			for (String part : args.getArgs()) {
				if (msg != "")
					msg += " ";
				msg += part;
			}
			ServerMsg.Say(msg);
		}
	}

	@CommandHandler(name = "gm", minimumArgs = 0, maximumArgs = 1, description = "Set gamemode.", usage = "Write /gm to set gamemode.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.gm")
	public void GMCommand(CommandSender sender, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.ModHead(sender, "Seznam gamemode prikazu");
			PlayerMsg.ModMenu(sender, "/gm 0", "",
					"- Zmeni gamemode na survival");
			PlayerMsg.ModMenu(sender, "/gm 1", "",
					"- Zmeni gamemode na creative");
			PlayerMsg.ModMenu(sender, "/gm 2", "",
					"- Zmeni gamemode na adventure");
		} else if (args.getArgs().size() == 1) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (args.getArgs().get(0).equalsIgnoreCase("0")) {
					player.setGameMode(GameMode.SURVIVAL);
					PlayerMsg.Info(sender,
							"Vas gamemode byl zmenen na survival!");
				} else if (args.getArgs().get(0).equalsIgnoreCase("1")) {
					player.setGameMode(GameMode.CREATIVE);
					PlayerMsg.Info(sender,
							"Vas gamemode byl zmenen na creative!");
				} else if (args.getArgs().get(1).equalsIgnoreCase("2")) {
					player.setGameMode(GameMode.ADVENTURE);
					PlayerMsg.Info(sender,
							"Vas gamemode byl zmenen na adventure!");
				}
			}
		}
	}
}
