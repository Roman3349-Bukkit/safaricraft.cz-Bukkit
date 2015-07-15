package me.frostbitecz.frameworks.jcommands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

public class CommandManager implements CommandExecutor {
	private final static HashMap<Command, Object> handlers = new HashMap<Command, Object>();
	private final static HashMap<Command, Method> methods = new HashMap<Command, Method>();
	private static CommandMap commandMap = null;

	public static void registerCommands(JavaPlugin plugin, Object handler) {
		for (Method method : handler.getClass().getMethods()) {
			Class<?>[] params = method.getParameterTypes();
			if (params.length == 2
					&& CommandSender.class.isAssignableFrom(params[0])
					&& CommandContext.class.equals(params[1])) {
				if (isCommandHandler(method)) {
					CommandHandler annotation = method
							.getAnnotation(CommandHandler.class);
					if (plugin.getCommand(annotation.name()) == null) {
						if (commandMap == null) {
							try {
								final Field field = Bukkit.getServer()
										.getClass()
										.getDeclaredField("commandMap");
								field.setAccessible(true);
								commandMap = (CommandMap) field.get(Bukkit
										.getServer());
							} catch (NoSuchFieldException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
						String name = annotation.name();
						String description = annotation.description();
						String usageMessage = annotation.usage();
						ArrayList<String> activeAliases;
						if (!(annotation.aliases().equals(new String[] { "" })))
							activeAliases = Lists.newArrayList(annotation
									.aliases());
						else
							activeAliases = new ArrayList<String>();
						JCommand jCommand = new JCommand(name, description,
								usageMessage, activeAliases);
						jCommand.setPermission(annotation.permission());
						jCommand.setPermissionMessage(annotation
								.permissionMessage());
						jCommand.setExecutor(new CommandManager());
						commandMap.register(annotation.name(), jCommand);
						handlers.put(jCommand, handler);
						methods.put(jCommand, method);
					}
				}
			}
		}
	}

	private static boolean isCommandHandler(Method method) {
		return method.getAnnotation(CommandHandler.class) != null;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Object handler = handlers.get(command);
		Method method = methods.get(command);
		CommandContext arguments = new CommandContext(args);
		String permission = method.getAnnotation(CommandHandler.class)
				.permission();
		int minimumReqArgs = method.getAnnotation(CommandHandler.class)
				.minimumArgs();
		int maximumReqArgs = method.getAnnotation(CommandHandler.class)
				.maximumArgs();
		if (handler != null && method != null) {
			if (!sender.hasPermission(permission)) {
				sender.sendMessage(ChatColor.RED
						+ method.getAnnotation(CommandHandler.class)
								.permissionMessage());
				return true;
			}
			if (method.getParameterTypes()[0].equals(Player.class)
					&& !(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ "This command requires a player sender");
				return true;
			}
			if (method.getParameterTypes()[0]
					.equals(ConsoleCommandSender.class)
					&& !(sender instanceof ConsoleCommandSender)) {
				sender.sendMessage(ChatColor.RED
						+ "This command requires a console sender");
				return true;
			}
			if (arguments.getArgs().size() < minimumReqArgs) {
				sender.sendMessage(ChatColor.RED + "Too few arguments!");
				sender.sendMessage(ChatColor.RED + command.getUsage());
				return true;
			}
			if (arguments.getArgs().size() > maximumReqArgs
					&& maximumReqArgs != -1) {
				sender.sendMessage(ChatColor.RED + "Too much arguments!");
				sender.sendMessage(ChatColor.RED + command.getUsage());
				return true;
			}
			try {
				method.invoke(handler, sender, arguments);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED
						+ "An error occurred while trying to process the command");
				e.printStackTrace();
			}
		} else
			sender.sendMessage("Unknown command. Type \"help\" for help.");
		return true;
	}
}