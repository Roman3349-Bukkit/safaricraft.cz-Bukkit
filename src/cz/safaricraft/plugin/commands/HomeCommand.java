package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

public class HomeCommand {

	private final Main plugin = Main.getInstance();

	@CommandHandler(name = "home", minimumArgs = 0, maximumArgs = 0, description = "Teleport to home.", usage = "Write /home to teleport home.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.home")
	public void homeCommand(Player player, CommandContext args) {
		String name = player.getName().toLowerCase();
		if (plugin.getConfig().getConfigurationSection(
				"player." + name + ".home") == null) {
			PlayerMsg.Error(player, "Home neni nastaven!");
		} else {
			double x = plugin.getConfig().getDouble(
					"player." + name + ".home.x");
			double y = plugin.getConfig().getDouble(
					"player." + name + ".home.y");
			double z = plugin.getConfig().getDouble(
					"player." + name + ".home.z");
			String w = plugin.getConfig().getString(
					"player." + name + ".home.world");
			player.teleport(new Location(Bukkit.getWorld(w), x, y, z));
			PlayerMsg
					.Success(player, "Byli jste teleportovani na Vas home!");
		}
	}

	@CommandHandler(name = "sethome", minimumArgs = 0, maximumArgs = 0, description = "Set home.", usage = "Write /sethome to set home.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.home")
	public void sethomeCommand(Player player, CommandContext args) {
		String name = player.getName().toLowerCase();
		Location loc = player.getLocation();
		plugin.getConfig().set("player." + name + ".home.x", loc.getX());
		plugin.getConfig().set("player." + name + ".home.y", loc.getY());
		plugin.getConfig().set("player." + name + ".home.z", loc.getZ());
		plugin.getConfig().set("player." + name + ".home.world",
				loc.getWorld().getName());
		plugin.saveConfig();
		PlayerMsg.Success(player, "Vas home byl uspesne nastaven!");
	}
}
