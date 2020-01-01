package mc.points.metype.commands;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mc.points.metype.Main;

public class PointsCommand implements CommandExecutor{
	
public PointsCommand(Main plugin) {
	plugin.getCommand("points").setExecutor(this);
}

@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(sender instanceof Player) {
	Player p = (Player) sender;
	if(cmd.getName().equalsIgnoreCase("points")) {
		Inventory inv = Bukkit.createInventory(null, 27,ChatColor.DARK_GREEN+"Points Menu");
		for( int l =0;l<9;l++) {
			inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		}
		for( int l =9;l<12;l++) {
			inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		}
		inv.setItem(12, createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.GREEN+"Get Points Balance"));
		inv.setItem(13, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		inv.setItem(14, createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.GREEN+"Enter Points Store"));
		inv.setItem(15, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		inv.setItem(16, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		inv.setItem(17, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		for( int l =18;l<27;l++) {
			inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
		}
		p.closeInventory();
		p.openInventory(inv);
	}
	}else {
		ConsoleCommandSender p = (ConsoleCommandSender) sender;
		p.sendMessage("You can't open the points store from the console!");
	}
	return false;
}

public ItemStack createItem(ItemStack item, String name, String[] lore) {
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(name);
    im.setLore(Arrays.asList(lore));
    item.setItemMeta(im);
    return item;
}


public ItemStack createItem(ItemStack item, String name) {
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(name);
    item.setItemMeta(im);
    item.setDurability((short)7);
    return item;
}
}
