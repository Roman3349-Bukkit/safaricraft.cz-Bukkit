package cz.safaricraft.plugin.commands;

import java.util.HashMap;

import me.frostbitecz.frameworks.jcommands.CommandContext;
import me.frostbitecz.frameworks.jcommands.CommandHandler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cz.safaricraft.plugin.Main;
import cz.safaricraft.plugin.chat.PlayerMsg;

public class VIPCommands {

	private HashMap<String, String> vipTeleports = new HashMap<String, String>();

	@CommandHandler(name = "y", minimumArgs = 0, maximumArgs = 0, description = "Accept tp.", usage = "Write /y to accept tp.", permissionMessage = "You do not have permissions to use this command!", permission = "safaricraft.command.y")
	public void tpacceptCommand(Player player, CommandContext args) {
		String name = player.getName();
		Player target = Bukkit.getPlayer(vipTeleports.get(name));
		if (target != null) {
			target.teleport(player);
			vipTeleports.remove(name);
			PlayerMsg.Info(player, "Teleport prijat!");
		} else {
			PlayerMsg.Error(player, "Nemate zadny pozadavek na teleport!");
		}
	}

	@CommandHandler(name = "vip", minimumArgs = 0, maximumArgs = -1, description = "Displays all VIP commands.", usage = "Write/vip to display VIP commands.", permissionMessage = "Pouze VIP mohou pouzivat tento prikaz!", permission = "safaricraft.command.vip")
	public void VIPCommand(Player player, CommandContext args) {
		if (args.getArgs().isEmpty()) {
			PlayerMsg.VIPHead(player, "Seznam VIP prikazu");
			PlayerMsg.VIPMenu(player, "/vip fly", "", "- Zapne/Vypne Vam fly");
			PlayerMsg.VIPMenu(player, "/vip heal", "", "- Vyleci Vas");
			PlayerMsg.VIPMenu(player, "/vip feed", "",
					"- Doplni Vam hunger bar");
			PlayerMsg.VIPMenu(player, "/vip time", "",
					"- Zobrazi menu na zmenu casu");
			PlayerMsg.VIPMenu(player, "/vip weather", "",
					"- Zobrazi menu na zmenu pocasi");
			PlayerMsg.VIPMenu(player, "/vip tp", "",
					"- Zobrazi menu pro teleportaci");
			PlayerMsg.VIPMenu(player, "/vip crafting", "",
					"- Otevre CraftingTable");
			PlayerMsg.VIPMenu(player, "/vip chest", "", "- Otevre EnderChest");
			PlayerMsg.VIPMenu(player, "/vip repair", "", "- Opravi item");
			PlayerMsg.VIPMenu(player, "/vip kit", "", "- Zobrazi kity");
			PlayerMsg.VIPMenu(player, "/vip hat", "",
					"- Date si na hlavu block");
			PlayerMsg.VIPMenu(player, "/vip clear", "",
					"- Vycisti Vam inventar");
		} else if (args.getArgs().get(0).equalsIgnoreCase("time")) {
			if (args.getArgs().size() == 1) {
				PlayerMsg.VIPHead(player, "Seznam prikazu na zmenu casu");
				PlayerMsg.VIPMenu(player, "/vip time set", "<0-24000>",
						"- Nastavi cas");
				PlayerMsg.VIPMenu(player, "/vip time reset", "",
						"- Zmeni cas zpet na serverovy");
			} else if (args.getArgs().size() == 3 && args.getArgs().get(1).equalsIgnoreCase("set")) {
				int time = Integer.parseInt(args.getArgs().get(2));
				if ((time >= 0) && (time <= 24000)) {
					player.setPlayerTime(time, false);
					PlayerMsg.Success(player, "Cas byl uspesne zmenen");
				} else {
					PlayerMsg.Error(player, "Tato hodnota nelze nastavit!");
				}
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("reset")) {
				if (!player.isPlayerTimeRelative()) {
					player.resetPlayerTime();
					PlayerMsg.Success(player, "Cas byl restartovan");
				} else {
					PlayerMsg.Success(player,
							"Vas cas nepotrebuje restartovat!");
				}
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("weather")) {
			if (args.getArgs().size() == 1) {
				PlayerMsg.VIPHead(player, "Seznam prikazu na zmenu pocasi");
				PlayerMsg.VIPMenu(player, "/vip weather clean", "",
						"- Nastavi slunecno");
				PlayerMsg.VIPMenu(player, "/vip weather rain", "",
						"- Nastavi dest");
				PlayerMsg.VIPMenu(player, "/vip weather reset", "",
						"- Zmeni pocasi na serverove");
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("clean")) {
				player.setPlayerWeather(WeatherType.CLEAR);
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("rain")) {
				player.setPlayerWeather(WeatherType.DOWNFALL);
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("reset")) {
				if (!player.isPlayerTimeRelative()) {
					player.resetPlayerWeather();
					PlayerMsg.Success(player, "Pocasi bylo restartovano");
				} else {
					PlayerMsg.Success(player,
							"Pocasi nepotrebuje restartovat!");
				}
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("heal")) {
			player.setHealth(20);
			PlayerMsg.Success(player, "Uspesne jste se vylecili!");
		} else if (args.getArgs().get(0).equalsIgnoreCase("feed")) {
			player.setFoodLevel(20);
			player.setSaturation(20);
			PlayerMsg.Success(player, "Uspesne jste se najedli!");
		} else if (args.getArgs().get(0).equalsIgnoreCase("fly")) {
			if (player.getAllowFlight()) {
				player.setAllowFlight(false);
				player.setFlying(false);
				PlayerMsg.Success(player, "Vas fly byl vypnut!");
			} else {
				player.setAllowFlight(true);
				player.setFlying(true);
				PlayerMsg.Success(player, "Vas fly byl zapnut!");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("crafting")
				|| args.getArgs().get(0).equalsIgnoreCase("workbench")) {
			player.openWorkbench(null, true);
		} else if (args.getArgs().get(0).equalsIgnoreCase("chest")
				|| args.getArgs().get(0).equalsIgnoreCase("enderchest")) {
			player.openInventory(player.getEnderChest());
		} else if (args.getArgs().get(0).equalsIgnoreCase("repair")) {
			double durability = (double) player.getItemInHand().getDurability();
			if (durability == -1 || durability == 0) {
				PlayerMsg.Error(player, "Tento item nelze opravit!");
			} else {
				player.getItemInHand().setDurability((short) 0);
				PlayerMsg.Success(player, "Tento item byl opraven!");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("tp")) {
			if (args.getArgs().size() == 1) {
				PlayerMsg.VIPHead(player, "Seznam prikazu pro teleportaci");
				PlayerMsg.VIPMenu(player, "/vip tp player", "<player>",
						"- Teleportuje Vas na misto posledni smrti");
				PlayerMsg.VIPMenu(player, "/vip tp bed", "",
						"- Teleportuje Vas k posteli");
				PlayerMsg.VIPMenu(player, "/vip tp death", "",
						"- Teleportuje Vas na misto posledni smrti");
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("bed")) {
				Location loc = player.getBedSpawnLocation();
				player.teleport(loc);
				PlayerMsg.Success(player,
						"Uspesne jste byli teleportovani k posteli.");
			} else if (args.getArgs().size() == 2 && args.getArgs().get(1).equalsIgnoreCase("death")) {
				String name = player.getName().toLowerCase();
				if (Main.deaths.containsKey(name)) {
					player.teleport(Main.deaths.get(name));
					PlayerMsg.Success(player,
							"Byl jsi teleportovan na misto posledni smrti!");
				} else {
					PlayerMsg.Error(player, "Misto smrti zatim neexistuje!");
				}
			} else if (args.getArgs().size() == 3 && args.getArgs().get(1).equalsIgnoreCase("player")) {
				Player p = Bukkit.getPlayer(args.getArgs().get(2));
				String name = player.getName();
				if (p == null) {
					PlayerMsg.Error(player, "Tento hrac neni online!");
				}
				String pname = p.getName();
				PlayerMsg
						.Info(
								p,
								"Hrac "
										+ name
										+ " se na vas chce teleportovat, pro prijeti napiste /y");
				vipTeleports.put(pname, name);
				PlayerMsg.Success(player, "Pozadavek byl odeslan!");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("kit")) {
			PlayerInventory pi = player.getInventory();
			if (args.getArgs().size() == 1) {
				PlayerMsg.VIPHead(player, "Seznam kitu");
				PlayerMsg.VIPMenu(player, "/vip kit dirt", "",
						"- Dostanete stack hliny");
				PlayerMsg.VIPMenu(player, "/vip kit sand", "",
						"- Dostanete stack pisku");
				PlayerMsg.VIPMenu(player, "/vip kit sandstone", "",
						"- Dostanete stack piskovce");
				PlayerMsg.VIPMenu(player, "/vip kit cobblestone", "",
						"- Dostanete stack cobblu");
				PlayerMsg.VIPMenu(player, "/vip kit stone", "",
						"- Dostanete stack kameni");
				PlayerMsg.VIPMenu(player, "/vip kit wood", "",
						"- Dostanete stack dreva");
			} else if (args.getArgs().get(1).equalsIgnoreCase("dirt")) {
				ItemStack dirt = new ItemStack(Material.DIRT, 64);
				pi.addItem(dirt);
				PlayerMsg.Success(player, "Dostali jste stack hliny!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("sand")) {
				ItemStack sand = new ItemStack(Material.SAND, 64);
				pi.addItem(sand);
				PlayerMsg.Success(player, "Dostali jste stack pisku!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("sandstone")) {
				ItemStack sandstone = new ItemStack(Material.SANDSTONE, 64);
				pi.addItem(sandstone);
				PlayerMsg.Success(player, "Dostali jste stack piskovce!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("cobblestone")) {
				ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 64);
				pi.addItem(cobblestone);
				PlayerMsg.Success(player, "Dostali jste stack cobblestonu!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("stone")) {
				ItemStack stone = new ItemStack(Material.STONE, 64);
				pi.addItem(stone);
				PlayerMsg.Success(player, "Dostali jste stack kameni!");
			} else if (args.getArgs().get(1).equalsIgnoreCase("wood")) {
				ItemStack wood = new ItemStack(Material.WOOD, 64);
				pi.addItem(wood);
				PlayerMsg.Success(player, "Dostali jste stack dreva!");
			}
		} else if (args.getArgs().get(0).equalsIgnoreCase("clear")) {
			player.getInventory().clear();
			PlayerMsg.Success(player, "Vas inventar byl uspesne vycisten!");
		} else if (args.getArgs().get(0).equalsIgnoreCase("hat")) {
			ItemStack i = player.getItemInHand();
			if (i.getType().isSolid()) {
				player.getInventory().setHelmet(new ItemStack(i.getType(), 1));
				i.setAmount(i.getAmount() - 1);
				PlayerMsg.Success(player,
						"Uspesne jste si na hlavu nasadili blok.");
			} else {
				PlayerMsg.Error(player,
						"Tento item si na hlavu nasadit nemuzete!");
			}
		}
	}

}
