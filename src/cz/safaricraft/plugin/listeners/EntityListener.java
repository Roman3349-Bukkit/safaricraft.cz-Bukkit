package cz.safaricraft.plugin.listeners;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import cz.safaricraft.plugin.Main;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		Location loc = e.getLocation();
		World world = loc.getWorld();
		e.setCancelled(true);
		world.playEffect(loc, Effect.SMOKE, 5, 5);
		world.playSound(loc, Sound.EXPLODE, 5, 5);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			String name = player.getName().toLowerCase();
			if (Main.unloged.contains(name) || Main.unregistered.contains(name)
					|| Main.vanished.contains(name) || Main.god.contains(name)) {
				player.setFireTicks(0);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent e) {
		Entity entity = e.getTarget();
		if (entity instanceof Player) {
			String name = ((Player) entity).getName().toLowerCase();
			if (Main.unloged.contains(name)
					|| (Main.unregistered.contains(name) || Main.vanished
							.contains(name))) {
				e.setTarget(null);
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			String name = ((Player) entity).getName().toLowerCase();
			if (Main.unloged.contains(name)
					|| (Main.unregistered.contains(name) || Main.vanished
							.contains(name))) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void EntityRegainHealthEvent(EntityRegainHealthEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			String name = ((Player) entity).getName().toLowerCase();
			if (Main.unloged.contains(name)
					|| (Main.unregistered.contains(name) || Main.vanished
							.contains(name))) {
				event.setCancelled(true);
			}
		}
	}
}
