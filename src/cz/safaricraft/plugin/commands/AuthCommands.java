package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.entity.Player;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

public class AuthCommands {
	
	private final Main plugin = Main.getInstance();

	@CommandHandler(name = "register", minimumArgs = 0, maximumArgs = 2, description = "Register.", usage = "Write /register to register.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.register")
	public void registerCommand(Player player, CommandContext args) {
		String name = player.getName().toLowerCase();
		if (args.getArgs().isEmpty()) {
			if (Main.unregistered.contains(name)) {
				PlayerMsg.Error(player,
						"Registrujte se pomoci /register <heslo> <heslo>");
			} else {
				PlayerMsg.Error(player, "Jiz jste zaregistorvan.");
			}
		} else if (args.getArgs().size() == 1) {
			if (Main.unregistered.contains(name)) {
				PlayerMsg.Error(player,
						"Registrujte se pomoci /register <heslo> <heslo>");
			} else {
				PlayerMsg.Error(player, "Jiz jste zaregistorvan.");
			}
		} else if (args.getArgs().size() == 2) {
			if (Main.unregistered.contains(name)) {
				if (args.getArgs().get(0).equals(args.getArgs().get(1))) {
					plugin.getConfig().set("player." + name + ".password",
							args.getArgs().get(0));
					plugin.saveConfig();
					PlayerMsg.Success(player,
							"Uspesne jste se zaregistroval/a!");
					Main.unregistered.remove(name);
				} else {
					PlayerMsg.Error(player, "Hesla se neshoduji!");
				}
			} else {
				PlayerMsg.Error(player, "Jiz jste zaregistorvan.");
			}
		}
	}

	@CommandHandler(name = "login", minimumArgs = 0, maximumArgs = 1, description = "Login.", usage = "Write /login to login.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.register")
	public void loginCommand(Player player, CommandContext args) {
		String name = player.getName().toLowerCase();
		if (args.getArgs().isEmpty()) {
			if (Main.unloged.contains(name)) {
				PlayerMsg
						.Error(player, "Prihlaste se pomoci /login <heslo>");
			} else {
				PlayerMsg.Error(player, "Jiz jste prihlaseny");
			}
		} else if (args.getArgs().size() == 1) {
			if (Main.unloged.contains(name)) {
				if (args.getArgs()
						.get(0)
						.equals(plugin.getConfig().getString(
								"player." + name + ".password"))) {
					PlayerMsg.Success(player, "Uspesne jste se prihlasil!");
					Main.unloged.remove(name);
				} else {
					PlayerMsg.Error(player, "Spatne heslo!");
				}
			} else {
				PlayerMsg.Error(player, "Jiz jste prihlasen.");
			}
		}
	}
	
}
