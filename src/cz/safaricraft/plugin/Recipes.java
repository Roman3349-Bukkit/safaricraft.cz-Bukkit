package cz.safaricraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

	public static void saddle() {
		ShapedRecipe r = new ShapedRecipe(new ItemStack(Material.SADDLE, 1));
		r.shape(new String[] { "AAA", "ABA" });
		r.setIngredient('A', Material.LEATHER);
		r.setIngredient('B', Material.IRON_INGOT);
		Bukkit.addRecipe(r);
	}

	public static void nametag() {
		ShapedRecipe r = new ShapedRecipe(new ItemStack(Material.NAME_TAG, 2));
		r.shape(new String[] { "  A", " BA", "B  " });
		r.setIngredient('A', Material.STRING);
		r.setIngredient('B', Material.PAPER);
		Bukkit.addRecipe(r);
	}

	public static void diamondhorse() {
		ShapedRecipe r = new ShapedRecipe(new ItemStack(
				Material.DIAMOND_BARDING, 1));
		r.shape(new String[] { "  A", "ABA", "A A" });
		r.setIngredient('A', Material.DIAMOND);
		r.setIngredient('B', Material.WOOL);
		Bukkit.addRecipe(r);
	}

	public static void goldhorse() {
		ShapedRecipe r = new ShapedRecipe(new ItemStack(Material.GOLD_BARDING,
				1));
		r.shape(new String[] { "  A", "ABA", "A A" });
		r.setIngredient('A', Material.GOLD_INGOT);
		r.setIngredient('B', Material.WOOL);
		Bukkit.addRecipe(r);
	}

	public static void ironhorse() {
		ShapedRecipe r = new ShapedRecipe(new ItemStack(Material.IRON_BARDING,
				1));
		r.shape(new String[] { "  A", "ABA", "A A" });
		r.setIngredient('A', Material.IRON_INGOT);
		r.setIngredient('B', Material.WOOL);
		Bukkit.addRecipe(r);
	}

}
