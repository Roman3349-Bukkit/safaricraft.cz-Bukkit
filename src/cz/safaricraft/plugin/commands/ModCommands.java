package cz.safaricraft.plugin.commands;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

public class ModCommands {

	@CommandHandler(name = "mod", minimumArgs = 0, maximumArgs = -1, description = "Displays all Mod commands.", usage = "Write /mod to display Mod commands.", permissionMessage = "Pouze Mod mohou pouzivat tento prikaz!", permission = "safaricraft.command.mod")
	public void ModCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.ModHead(player, "Seznam Mod prikazu");
			PlayerMsg.ModMenu(player, "/vip ", "",
					"- Zobrazi seznam VIP prikazu");
			PlayerMsg.ModMenu(player, "/gm ", "", "- Zobrazi gamemode menu");
			PlayerMsg.ModMenu(player, "/tp", "<player>",
					"- Teleportuje Vas na hrace");
			PlayerMsg.ModMenu(player, "/tphere", "<player>",
					"Teleportuje hrace na vas");
			PlayerMsg.ModMenu(player, "/tppos", "<x> <y> <z>",
					"- Teleportuje Vas na souradnice");
			PlayerMsg.ModMenu(player, "/mod god", "<player>",
					"- Zapnete god hraci");
			PlayerMsg.ModMenu(player, "/mod kick", "<player>",
					"- Vyhodite daneho hrace");
			PlayerMsg.ModMenu(player, "/mod head", "<player>",
					"- Dostanete hlavu hrace");
			PlayerMsg.ModMenu(player, "/time set", "<0-24000>", "- Nastavi cas");
		} else if (args.getArgs().get(0).equalsIgnoreCase("god")) {
			String name = player.getName();
			Player target = Bukkit.getPlayer(args.getArgs().get(1));
			String tname = target.getName();
			if (args.getArgs().size() == 2) {
				if (!Main.god.contains(tname.toLowerCase())) {
					Main.god.add(tname.toLowerCase());
					PlayerMsg.Success(player,
							"Uspene jste zapnuli god hraci " + tname);
					PlayerMsg.Info(target, "Hrac " + name
							+ " Vam zapnul god");
				} else {
					Main.god.remove(tname.toLowerCase());
					PlayerMsg.Success(player,
							"Uspene jste vypnuli god hraci " + tname);
					PlayerMsg.Info(target, "Hrac " + name
							+ " Vam vypnul god!");
				}
			} else {
				PlayerMsg.Error(player, "/gm god <player>");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("kick")) {
			if (args.getArgs().size() == 2) {
				Player target = Bukkit.getPlayer(args.getArgs().get(1));
				if (target == null) {
					PlayerMsg.Error(player, "Hrac neni online!");
				}
				target.kickPlayer(ChatColor.RED + "Byl jsi vyhozen GM/Adminem.");
			} else {
				PlayerMsg.Error(player, "/gm kick <player>");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("head")) {
			if (args.getArgs().size() == 2) {
				ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1,
						(short) SkullType.PLAYER.ordinal());
				String name = Bukkit.getOfflinePlayer(args.getArgs().get(1))
						.getName();
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(name);
				skull.setItemMeta(meta);
				player.getInventory().addItem(skull);
				PlayerMsg.Success(player, "Dostali jste hlavu hrace " + name
						+ "!");
			} else {
				PlayerMsg.Error(player, "/gm head <player>");
			}
		}
	}

}
