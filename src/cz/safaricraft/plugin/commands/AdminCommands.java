package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.*;

public class AdminCommands {

	private final Main plugin = Main.getInstance();

	@CommandHandler(name = "admin", minimumArgs = 0, maximumArgs = -1, description = "Displays all Admin commands.", usage = "Write /admin to display Admin commands.", permissionMessage = "Pouze Admini mohou pouzivat tento prikaz!", permission = "safaricraft.command.admin")
	public void AdminCommand(Player player, CommandContext args) {
		Location loc = player.getLocation();
		if (args.getArgs().isEmpty()) {
			PlayerMsg.AdminHead(player, "Seznam Admin prikazu");
			PlayerMsg.AdminMenu(player, "/vip", "",
					"- Zobrazi seznam VIP prikazu");
			PlayerMsg.AdminMenu(player, "/gm", "",
					"- Zobrazi seznam GM prikazu");
			PlayerMsg.AdminMenu(player, "/admin invsee", "<hrac>",
					"- Zobrazi inventar hrace");
			PlayerMsg.AdminMenu(player, "/admin chest", "<hrac>",
					"- Zobrazi EnderChest hrace");
			PlayerMsg.AdminMenu(player, "/admin setspawn", "",
					"- Nastavi spawn");
			PlayerMsg.AdminMenu(player, "/admin kill", "<player>",
					"- Zabijete hrace");
			PlayerMsg.AdminMenu(player, "/admin fly", "<player>",
					"- Zapnete/vypnete fly hraci");
			PlayerMsg.AdminMenu(player, "/say", "<msg>",
					"- Napise zpravu jako server");
		} else if (args.getArgs().get(0).equalsIgnoreCase("invsee")) {
			if (args.getArgs().size() == 2) {
				Player target = Bukkit.getPlayer(args.getArgs().get(1));
				if (target != null) {
					String name = target.getName();
					PlayerMsg.Success(player, "Nyni vidite inventar hrace "
							+ name + ".");
					player.openInventory(target.getInventory());
				} else {
					PlayerMsg.Error(player, "Tento hrac neni online");
				}
			} else {
				PlayerMsg.Error(player, "/admin invsee <player>");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("chest")) {
			if (args.getArgs().size() == 2) {
				Player target = Bukkit.getPlayer(args.getArgs().get(1));
				if (target != null) {
					String name = target.getName();
					PlayerMsg.Success(player,
							"Nyni vidite EnderChest hrace " + name + ".");
					player.openInventory(target.getEnderChest());
				} else {
					PlayerMsg.Error(player, "Tento hrac neni online");
				}
			} else {
				PlayerMsg.Error(player, "/admin chest <player>");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("setspawn")) {
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			int blockx = loc.getBlockX();
			int blocky = loc.getBlockY();
			int blockz = loc.getBlockZ();
			World world = loc.getWorld();
			plugin.getConfig().set("spawn.world", world.getName());
			plugin.getConfig().set("spawn.x", x);
			plugin.getConfig().set("spawn.y", y);
			plugin.getConfig().set("spawn.z", z);
			world.setSpawnLocation(blockx, blocky, blockz);
			plugin.saveConfig();
			PlayerMsg.Success(player, "Spawn byl uspesne nastaven!");
		} else if (args.getArgs().get(0).equalsIgnoreCase("kill")) {
			if (args.getArgs().size() == 2) {
				Player target = Bukkit.getPlayer(args.getArgs().get(1));
				if (target.isOnline()) {
					target.damage(target.getHealth() + 1);
					PlayerMsg.Success(player, "Hrac/ka " + target.getName()
							+ " byl/a zabit/a!");
				}
			} else {
				PlayerMsg.Error(player, "/admin kill <player>");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("fly")) {
			Player target = Bukkit.getPlayer(args.getArgs().get(1));
			if (target == null) {
				PlayerMsg.Error(player, "Hrac neni online!");
			}
			String name = player.getName();
			String tname = target.getName();
			if (target.getAllowFlight()) {
				target.setAllowFlight(false);
				target.setFlying(false);
				PlayerMsg.Success(player, "Vypnuli jste fly hraci " + tname);
				PlayerMsg.Info(target, "Admin " + name + " Vam vypnul fly!");
			} else {
				target.setAllowFlight(true);
				target.setFlying(true);
				PlayerMsg.Success(player, "Zapnuli jste fly hraci " + tname);
				PlayerMsg.Info(target, "Admin " + name + " Vam zapnul fly!");
			}
		}
	}
}
