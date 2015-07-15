package cz.safaricraft.plugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cz.safaricraft.plugin.chat.ServerMsg;

public class VotifierListener implements Listener {

	@EventHandler
	public void onPlayerVote(VotifierEvent e) {
		Vote vote = e.getVote();
		Player player = Bukkit.getPlayer(vote.getUsername());
		String name = vote.getUsername();
		String s = vote.getServiceName();
		PlayerInventory pi = player.getInventory();
		ServerMsg.Info("Hrac/ka " + name + " hlasoval/a na " + s + "!");
		pi.addItem(new ItemStack(Material.GOLD_INGOT, 8));
		pi.addItem(new ItemStack(Material.IRON_INGOT, 8));
		pi.addItem(new ItemStack(Material.DIAMOND, 4));
		pi.addItem(new ItemStack(Material.EMERALD, 2));
		pi.addItem(new ItemStack(Material.PUMPKIN_PIE, 8));
		player.giveExpLevels(15);
	}

}
