package me.frostbitecz.frameworks.jcommands;

import java.util.ArrayList;

public class CommandContext {
	
	private ArrayList<String> args = new ArrayList<String>();
	private ArrayList<Character> flags = null;

	public CommandContext(String[] commandArguments) {
		for (String s : commandArguments) {
			if ((s.startsWith("-") || s.startsWith("--"))
					&& !s.startsWith("---")) {
				s = s.replaceAll("-", "");
				char flag = s.charAt(0);
				if (flags == null) {
					flags = new ArrayList<Character>();
					flags.add(flag);
				} else {
					flags.add(flag);
				}
			} else {
				args.add(s);
			}
		}
	}

	public boolean hasFlag(char flag) {
		if (this.flags == null)
			return false;
		return this.flags.contains(flag);
	}

	public ArrayList<String> getArgs() {
		return this.args;
	}
	
	public String[] getFrom(int index) {
		String[] temp = new String[args.size() - index];
		temp = args.toArray(temp);
		String[] from = new String[temp.length - index];
		System.arraycopy(temp, index, from, 0, temp.length - index);
		return from;
	}
}