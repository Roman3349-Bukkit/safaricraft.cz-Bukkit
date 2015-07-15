package me.frostbitecz.frameworks.jcommands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {
	String name();

	String[] aliases() default { "" };

	int minimumArgs() default 0;

	int maximumArgs() default 0;

	String description() default "";

	String usage() default "";

	String permission() default "";

	String permissionMessage() default "You do not have permission to use that command!";
}