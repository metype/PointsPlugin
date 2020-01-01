package mc.points.metype.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mc.points.metype.Main;
import mc.points.metype.mysqlint;

public class GetpointsCommand implements CommandExecutor{
	
public GetpointsCommand(Main plugin) {
	plugin.getCommand("getpoints").setExecutor(this);
}

@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(sender instanceof Player) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("getpoints")) {
			if(p.hasPermission("points.getpoints")) {
				Bukkit.getConsoleSender().sendMessage(args[0]);
				if((new mysqlint()).getPlayer(args[0]).equals("null")) {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Failed to find user! This command is case sensitive!");
					return false;
				}else {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" "+args[0]+" has "+(new mysqlint()).getPoints((new mysqlint()).getPlayer(args[0]),p)+" points");
				}		
			}else {
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" You can't do that!");
			}
		}
	}else {
		ConsoleCommandSender p = (ConsoleCommandSender) sender;
		if(cmd.getName().equalsIgnoreCase("getpoints")) {
			if(p.hasPermission("points.getpoints")) {
				Bukkit.getConsoleSender().sendMessage(args[0]);
				if((new mysqlint()).getPlayer(args[0]).equals("null")) {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Failed to find user! This command is case sensitive!");
					return false;
				}else {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" "+args[0]+" has "+(new mysqlint()).getPoints((new mysqlint()).getPlayer(args[0]),p)+" points");
				}		
			}else {
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" You can't do that!");
			}
		}
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
}
