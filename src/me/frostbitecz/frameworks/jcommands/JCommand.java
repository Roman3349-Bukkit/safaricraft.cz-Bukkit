package me.frostbitecz.frameworks.jcommands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JCommand extends Command {
	private CommandExecutor executor = null;

	protected JCommand(String name) {
		super(name);
	}

	protected JCommand(String name, String description, String usageMessage,
			ArrayList<String> activeAliases) {
		super(name, description, usageMessage, activeAliases);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel,
			String[] args) {
		if (executor != null) {
			executor.onCommand(sender, this, commandLabel, args);
		}
		return false;
	}

	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}

}