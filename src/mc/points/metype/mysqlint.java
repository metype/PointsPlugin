package mc.points.metype;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import mc.points.metype.files.MenuConfig;

public class mysqlint implements Listener {

	Main plugin = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		Player player = event.getPlayer();
		createPlayer(player.getUniqueId(),player);
		player.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.WHITE+" You have " + ChatColor.DARK_GREEN + "" +getPoints(player.getUniqueId().toString(),player) +ChatColor.WHITE + " points.");
	}
	
	@EventHandler
	public void InvenClick(InventoryClickEvent event) {
		FileConfiguration config = MenuConfig.get();
		Player p = (Player) event.getWhoClicked();
		//ClickType click = event.getClick();
		Inventory open = event.getClickedInventory();
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		if(open!=null) {
		if(open.getName().startsWith(ChatColor.DARK_GREEN+"Points Store : ")) {
			//Bukkit.getConsoleSender().sendMessage("Slot : "+event.getSlot());
			int j = event.getSlot();
			int i = 0;
			if(open.getItem(event.getSlot())!=null) {
				p.closeInventory();
			Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN+"Points Store ("+open.getItem(event.getSlot()).getItemMeta().getDisplayName()+") : " + (new mysqlint()).getPoints(p.getUniqueId().toString(),p));
			for(String key : config.getConfigurationSection("menu.categories").getKeys(false)){	
		    	if(i==j) {
		    		int k=0;
		    		for(String key2 : config.getConfigurationSection("menu.categories."+key+".items").getKeys(false)){
		    			String[] lore = {(String)config.get("menu.categories."+key+".items."+key2+".description"),"Cost : "+ config.get("menu.categories."+key+".items."+key2+".cost")};
		    			inv.setItem(k,createItem(new ItemStack(Material.getMaterial(((String)config.get("menu.categories."+key+".items."+key2+".item")).toUpperCase())),(String)config.get("menu.categories."+key+".items."+key2+".name"),lore));
		    			k++;
		    		}
		    	}
		    	i++;
		}
			p.openInventory(inv);
		}
		}else {
			int j=0;
			if(open.getItem(event.getSlot())!=null) {
			for(String key : config.getConfigurationSection("menu.categories").getKeys(false)){	
				if(open.getName().startsWith(ChatColor.DARK_GREEN+"Points Store ("+(String)config.get("menu.categories."+key+".name")+") : ")) {
					int i=0;
					for(String key2 : config.getConfigurationSection("menu.categories").getKeys(false)){	
				    	if(i==j) {
				    		for(String key3 : config.getConfigurationSection("menu.categories."+key2+".items").getKeys(false)){
				    			if(open.getItem(event.getSlot()).getItemMeta().getDisplayName()==(String)config.get("menu.categories."+key2+".items."+key3+".name")) {
									p.closeInventory();
				    				if((new mysqlint()).getPoints(p.getUniqueId().toString(),p)>=(int)config.get("menu.categories."+key2+".items."+key3+".cost")) {
				    					Inventory inv = Bukkit.createInventory(null, 27,ChatColor.DARK_GREEN+"Are you sure? ("+(String)config.get("menu.categories."+key2+".items."+key3+".name")+")");
				    					for( int l =0;l<9;l++) {
				    						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					}
				    					for( int l =9;l<12;l++) {
				    						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					}
				    					inv.setItem(14, createItem(new ItemStack(Material.REDSTONE_BLOCK),ChatColor.RED+"Deny Purchase"));
				    					inv.setItem(13, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					inv.setItem(12, createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.GREEN+"Accept Purchase"));
				    					inv.setItem(15, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					inv.setItem(16, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					inv.setItem(17, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					for( int l =18;l<27;l++) {
				    						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
				    					}
				    					p.openInventory(inv);
				    						}else {
				    		    				Inventory inv = Bukkit.createInventory(null, 27);
				    		    				p.openInventory(inv);
				    							p.closeInventory();
				    					p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" You cannot afford that item!");
				    				}
				    			}
				    		}
				    	}
				    	i++;
				}
				}
				j++;
			}
			}
			if(open.getName().startsWith(ChatColor.DARK_GREEN+"Are you sure? (")) {
				if(event.getSlot()!=12&&event.getSlot()!=14) {
					Inventory inv = Bukkit.createInventory(null, 27,open.getName());
					for( int l =0;l<9;l++) {
						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					}
					for( int l =9;l<12;l++) {
						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					}
					inv.setItem(14, createItem(new ItemStack(Material.REDSTONE_BLOCK),ChatColor.RED+"Deny Purchase"));
					inv.setItem(13, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					inv.setItem(12, createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.GREEN+"Accept Purchase"));
					inv.setItem(15, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					inv.setItem(16, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					inv.setItem(17, createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					for( int l =18;l<27;l++) {
						inv.setItem(l,createItem(new ItemStack(Material.STAINED_GLASS_PANE)," "));
					}
					p.closeInventory();
					p.openInventory(inv);
				}else {
					if(event.getSlot()==12) {
						for(String key2 : config.getConfigurationSection("menu.categories").getKeys(false)){	
					    		for(String key3 : config.getConfigurationSection("menu.categories."+key2+".items").getKeys(false)){
					    			if(open.getName().startsWith(ChatColor.DARK_GREEN+"Are you sure? ("+config.get("menu.categories."+key2+".items."+key3+".name")+")")){
					    				ConsoleCommandSender console = Bukkit.getConsoleSender();
					    				String command = ((String)config.get("menu.categories."+key2+".items."+key3+".command")).replaceAll("%user%",p.getDisplayName());
					    				Bukkit.dispatchCommand(console, command);
					    				setPoints(p.getUniqueId().toString(),(new mysqlint()).getPoints(p.getUniqueId().toString(),p)-(int)config.get("menu.categories."+key2+".items."+key3+".cost"));
					    				Inventory inv = Bukkit.createInventory(null, 27);
					    				p.openInventory(inv);
					    				p.closeInventory();
					    				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.WHITE+" Succesfully purchased : " +ChatColor.GREEN+config.get("menu.categories."+key2+".items."+key3+".name"));
					    			}
					    		 }
					    		}
					}else {
	    				Inventory inv = Bukkit.createInventory(null, 27);
	    				p.openInventory(inv);
						p.closeInventory();
						p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Purchase cancelled");
					}
				}
			}
			if(open.getName().equals(ChatColor.DARK_GREEN+"Points Menu")) {
				if(event.getSlot()==14) {
				File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Points_By_Metype").getDataFolder(), "menu.yml");
				if(!file.exists()) {
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" No menu.yml file was found, contact the owner, or an administrator to sort this out.");
				}else {
					MenuConfig.reload();
					FileConfiguration conf = MenuConfig.get();
					if(config.getString("menu.categories") != null) {
				    	Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN+"Points Store : " + (new mysqlint()).getPoints(p.getUniqueId().toString(),p));
				    	int i=0;
						for(String key : conf.getConfigurationSection("menu.categories").getKeys(false)){	
							String[] lore = {(String)conf.get("menu.categories."+key+".description")};
							if(Material.getMaterial(((String)conf.get("menu.categories."+key+".item")).toUpperCase())!=null){
						    	inv.setItem(i,createItem(new ItemStack(Material.getMaterial(((String)conf.get("menu.categories."+key+".item")).toUpperCase())),(String)conf.get("menu.categories."+key+".name"),lore));
							}else {
								inv.setItem(i,createItem(new ItemStack(Material.GRASS),(String)conf.get("menu.categories."+key+".name"),lore));
							}
							i++;
						}
				    	p.openInventory(inv);
					}else {
						p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" Malformed menu.yml");
					}
				}
			}else if(event.getSlot()==12){
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.WHITE+" You have " + ChatColor.DARK_GREEN + "" +getPoints(p.getUniqueId().toString(),p) +ChatColor.WHITE + " points.");
				p.openInventory(Bukkit.createInventory(null,27,"null"));
				p.closeInventory();
			}else {
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
			}
		}
		}
	}
	
	public boolean playerExists(UUID uuid) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		try {
			PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE uuid=?");
			statement.setString(1,uuid.toString());
			ResultSet results = statement.executeQuery();
			if(results.next()) {
//				Bukkit.getConsoleSender().sendMessage("Player found");
				return true;
			}
//			Bukkit.getConsoleSender().sendMessage("Player not found");
			return false;
		} catch (SQLException e) {
			//nothing
		}
		return false;
	}
	
	public String getPlayer(String name) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		String uuid="";
		try {
		PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT uuid FROM " + plugin.table + " WHERE name=?");
		statement.setString(1,name);
		ResultSet results = statement.executeQuery();
		while(results.next()) {
			uuid=results.getString("uuid");
		}
		return uuid;
		}catch(Exception e) {
		//nothing	
		}
		return "null";
	}
	
	public void createPlayer(final UUID uuid, Player player) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		try {
			PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM " + plugin.table + " WHERE uuid=?");
			statement.setString(1,uuid.toString());
			ResultSet results = statement.executeQuery();
			results.next();
			if(!playerExists(uuid)) {
				PreparedStatement insert = plugin.getConnection().prepareStatement("INSERT INTO " + plugin.table + " (uuid,name,points) VALUE (?,?,?)");
//				Bukkit.getConsoleSender().sendMessage("Inserted player.");
				insert.setString(1, uuid.toString());
				insert.setString(2, player.getName());
				insert.setInt(3, plugin.defaultPoints);
				insert.executeUpdate();
			}
		}catch(Exception e) {
			//nothing
		}
	}
	
	public void updatePoints(final UUID uuid, Player player) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		try {
				Connection c = plugin.getConnection();
				PreparedStatement insert = c.prepareStatement("UPDATE "+plugin.table+" SET points = points+? WHERE uuid=?");
				insert.setInt(1, plugin.ppm);
				insert.setString(2, uuid.toString());
				insert.executeUpdate();
		}catch(Exception e) {
			//nothing
		}
	}
	
	public void setPoints(final String uuid, int points) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//nothing
		}
		try {
				PreparedStatement insert = plugin.getConnection().prepareStatement("UPDATE "+plugin.table+" SET points = ? WHERE uuid=?");
				insert.setInt(1, points);
				insert.setString(2, uuid.toString());
				insert.executeUpdate();
		}catch(Exception e) {
			//nothing
		}
	}
	
	public int getPoints(final String uuid, Player p) {
		try {
			if(plugin.getConnection().isClosed()) {
				p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" There was an issue accessing the database's information, try again an a minute, if the problem persists, contact an administrator.");
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		int score=0;
		try {
			PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT points FROM " + plugin.table + " WHERE uuid=?");
			statement.setString(1,uuid.toString());
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				score=results.getInt("points");
			}
			return score;
		}catch(Exception e) {
			//nothing
		}
		return 0;
	}
	
	public int getPoints(final String uuid, ConsoleCommandSender p) {
		try {
			if(plugin.getConnection().isClosed()) {
				plugin.mySQLSetup();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			p.sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GOLD+"POINTS"+ChatColor.DARK_GRAY+"]"+ChatColor.RED+" There was an issue accessing the database's information, try again an a minute, if the problem persists, contact an administrator.");
			
		}
		int score=0;
		try {
			PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT points FROM " + plugin.table + " WHERE uuid=?");
			statement.setString(1,uuid.toString());
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				score=results.getInt("points");
			}
			return score;
		}catch(Exception e) {
			//nothing
		}
		return 0;
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
