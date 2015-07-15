package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.chat.PlayerMsg;

public class BuilderCommand {

	@CommandHandler(name = "builder", minimumArgs = 0, maximumArgs = -1, description = "Displays all Builder commands.", usage = "Write /builder to display Builder commands.", permissionMessage = "Pouze Builderi mohou pouzivat tento prikaz!", permission = "safaricraft.command.builder")
	public void GMCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.BuilderHead(player, "Seznam Builder prikazu");
			PlayerMsg.BuilderMenu(player, "/vip ", "",
					"- Zobrazi seznam VIP prikazu");
			PlayerMsg.BuilderMenu(player, "/builder gm ", "",
					"- Zobrazi gamemode menu");
			PlayerMsg.BuilderMenu(player, "/tphere", "<player>",
					"Teleportuje hrace na vas");
		} else if (args.getArgs().get(0).equalsIgnoreCase("gm")) {
			if (args.getArgs().size() == 1) {
				PlayerMsg.BuilderHead(player, "Seznam gamemode prikazu");
				PlayerMsg.BuilderMenu(player, "/builder gm 0", "",
						"- Zmeni gamemode na survival");
				PlayerMsg.BuilderMenu(player, "/builder gm 1", "",
						"- Zmeni gamemode na creative");
				PlayerMsg.BuilderMenu(player, "/builder gm 2", "",
						"- Zmeni gamemode na adventure");
			} else if (args.getArgs().get(1).equalsIgnoreCase("0")) {
				player.setGameMode(GameMode.SURVIVAL);
				PlayerMsg.Info(player,
						"Vas gamemode byl zmenen na survival!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("1")) {
				player.setGameMode(GameMode.CREATIVE);
				PlayerMsg.Info(player,
						"Vas gamemode byl zmenen na creative!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("2")) {
				player.setGameMode(GameMode.ADVENTURE);
				PlayerMsg.Info(player,
						"Vas gamemode byl zmenen na adventure!");
			}
		}
	}

}
