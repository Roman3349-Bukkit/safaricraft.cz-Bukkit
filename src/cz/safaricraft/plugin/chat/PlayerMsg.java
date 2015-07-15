package cz.safaricraft.plugin.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PlayerMsg {

	public static void Success(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.GREEN + msg);
	}

	public static void Error(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.RED + msg);
	}

	public static void Info(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.YELLOW + msg);
	}

	public static void HelpMenu(CommandSender sender, String cmd, String args,
			String des) {
		sender.sendMessage(ChatColor.YELLOW + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + des);
	}

	public static void HelpHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "******* " + ChatColor.YELLOW
				+ name + ChatColor.GRAY + " *******");
	}

	public static void HelpFoot(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "** " + ChatColor.YELLOW + name
				+ ChatColor.GRAY + " **");
	}

	public static void VIPMenu(CommandSender sender, String cmd, String args,
			String des) {
		sender.sendMessage(ChatColor.GOLD + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + des);
	}

	public static void VIPHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "***** " + ChatColor.GOLD + name
				+ ChatColor.GRAY + " *****");
	}

	public static void HelperMenu(CommandSender sender, String cmd,
			String args, String description) {
		sender.sendMessage(ChatColor.BLUE + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + description);
	}

	public static void HelperHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "***** " + ChatColor.BLUE + name
				+ ChatColor.GRAY + " *****");
	}

	public static void BuilderMenu(CommandSender sender, String cmd,
			String args, String des) {
		sender.sendMessage(ChatColor.RED + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + des);
	}

	public static void BuilderHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "***** " + ChatColor.RED + name
				+ ChatColor.GRAY + " *****");
	}

	public static void ModMenu(CommandSender sender, String cmd, String args,
			String des) {
		sender.sendMessage(ChatColor.GREEN + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + des);
	}

	public static void ModHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "***** " + ChatColor.GREEN + name
				+ ChatColor.GRAY + " *****");
	}

	public static void AdminMenu(CommandSender sender, String cmd, String args,
			String des) {
		sender.sendMessage(ChatColor.AQUA + cmd + ChatColor.GRAY + " " + args
				+ ChatColor.WHITE + " " + des);
	}

	public static void AdminHead(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.GRAY + "***** " + ChatColor.AQUA + name
				+ ChatColor.GRAY + " *****");
	}

	public static void Welcome(CommandSender sender, String name) {
		sender.sendMessage(ChatColor.AQUA + "Vitejte na serveru "
				+ ChatColor.GOLD + name + ChatColor.AQUA + "!");
	}

}
