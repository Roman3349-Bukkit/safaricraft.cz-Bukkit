package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.*;

import org.bukkit.entity.Player;

import cz.safaricraft.plugin.chat.PlayerMsg;

public class HelperCommands {

	@CommandHandler(name = "helper", minimumArgs = 0, maximumArgs = -1, description = "Displays all Helper commands.", usage = "Write /helper to display Helper commands.", permissionMessage = "Pouze Helper mohou pouzivat tento prikaz!", permission = "safaricraft.command.helper")
	public void HelperCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.HelperHead(player, "Seznam Helper prikazu");
			PlayerMsg.HelperMenu(player, "/vip", "",
					"- Zobrazi seznam VIP prikazu");
			PlayerMsg.HelperMenu(player, "/tphere", "<player>",
					"- Teleportuje hrace na vas");
			PlayerMsg.HelperMenu(player, "/tppos", "<x> <y> <z>",
					"- Teleportuje Vas na souradnice");
		}
	}

}
