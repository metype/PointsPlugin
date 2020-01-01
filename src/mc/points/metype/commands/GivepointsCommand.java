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

public class GivepointsCommand implements CommandExecutor{
	
public GivepointsCommand(Main plugin) {
	plugin.getCommand("givepoints").setExecutor(this);
}

@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(sender instanceof Player) {
	Player p = (Player) sender;
	if(cmd.getName().equalsIgnoreCase("givepoints")) {
		if(p.hasPermission("points.givepoints")) {
			if((new mysqlint()).getPlayer(args[0]).equals("")) {
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Failed to find user! This command is case sensitive!");
				return false;
			}else {
				try {
					(new mysqlint()).setPoints((new mysqlint()).getPlayer(args[0]), (new mysqlint()).getPoints((new mysqlint()).getPlayer(args[0]),p)+Integer.parseInt(args[1]));
				}catch (Exception e) {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" "+args[1]+" is not a valid number!");
					return false;
				}
			}
			Player player = Bukkit.getServer().getPlayer(args[0]);
			if(player!=null) {
				player.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" You have been given " + args[1] + " points by " + p.getName());
			}
		p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" Successfully gave " + args[1] + " points to " + args[0]);
		
		}else {
			p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" You can't do that!");
		}
	}
	}else {
		ConsoleCommandSender p = (ConsoleCommandSender) sender;
		if(cmd.getName().equalsIgnoreCase("givepoints")) {
			if(p.hasPermission("points.givepoints")) {
				if((new mysqlint()).getPlayer(args[0]).equals("")) {
					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Failed to find user! This command is case sensitive!");
					return false;
				}else {
					try {
						(new mysqlint()).setPoints((new mysqlint()).getPlayer(args[0]), (new mysqlint()).getPoints((new mysqlint()).getPlayer(args[0]),p)+Integer.parseInt(args[1]));
					}catch (Exception e) {
						p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" "+args[1]+" is not a valid number!");
						return false;
					}
				}
				Player player = Bukkit.getServer().getPlayer(args[0]);
				if(player!=null) {
					player.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" You have been given " + args[1] + " points by CONSOLE");
				}
			p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.GREEN+" Successfully gave " + args[1] + " points to " + args[0]);
			
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
