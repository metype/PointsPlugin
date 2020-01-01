package mc.points.metype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import mc.points.metype.commands.GetpointsCommand;
import mc.points.metype.commands.GivepointsCommand;
import mc.points.metype.commands.PointsCommand;
import mc.points.metype.commands.SetpointsCommand;
import mc.points.metype.files.MenuConfig;
import mc.points.metype.files.PointsConfig;

public class Main extends JavaPlugin{
	private Connection connection;
	public String host, database, user, pass, table;
	public int port,defaultPoints,ppm;
	public long millis=0;
	@Override
	public void onEnable() {
		
		new PointsCommand(this);
		new SetpointsCommand(this);
		new GetpointsCommand(this);
		new GivepointsCommand(this);
	
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		PointsConfig.setup();
		PointsConfig.get().addDefault("points.per_minute",5);
		PointsConfig.get().addDefault("points.default",0);
		PointsConfig.get().addDefault("sql.host","127.0.0.1");
		PointsConfig.get().addDefault("sql.port",3306);
		PointsConfig.get().addDefault("sql.database","points");
		PointsConfig.get().addDefault("sql.password","pass");
		PointsConfig.get().addDefault("sql.username","user");
		PointsConfig.get().addDefault("sql.table","points");
		PointsConfig.get().options().copyDefaults(true);
		PointsConfig.save();
		
		MenuConfig.setup();
		MenuConfig.get().addDefault("example.menu.categories.test.name","Test Category");
		MenuConfig.get().addDefault("example.menu.categories.test.description","Just a test");
		MenuConfig.get().addDefault("example.menu.categories.test.items.item1.cost",250);
		MenuConfig.get().addDefault("example.menu.categories.test.items.item1.name","Item 1");
		MenuConfig.get().addDefault("example.menu.categories.test.items.item1.command","give %user% cooked_porkchop 64");
		MenuConfig.get().options().copyDefaults(true);
		MenuConfig.save();
		
		mySQLSetup();
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"MYSQL CONNECTED");
		try {
			PreparedStatement statement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "+this.table+" (uuid varchar(200), name varchar(16), points int)");
//			statement.setString(1,(String)PointsConfig.get().get("sql.table"));
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		defaultPoints=(int)PointsConfig.get().get("points.default");
		ppm=(int)PointsConfig.get().get("points.per_minute");
		
		this.getServer().getPluginManager().registerEvents(new mysqlint(), this);
		Main thus = this;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	if(System.currentTimeMillis() > millis+59000) {
            		try {
            			if(getConnection().isClosed()) {
            				mySQLSetup();
            			}
            		} catch (SQLException e) {
            			// TODO Auto-generated catch block
            			//nothing
            		}
             	   millis=System.currentTimeMillis();
             	   for(Player p : thus.getServer().getOnlinePlayers()) {
       					(new mysqlint()).updatePoints(p.getUniqueId(), p);
             	   }
                }
            }
        }, 0, 100);
	}
	
	public void mySQLSetup() {
		host=(String)PointsConfig.get().get("sql.host");
		port=(int)PointsConfig.get().get("sql.port");
		database=(String)PointsConfig.get().get("sql.database");
		pass=(String)PointsConfig.get().get("sql.password");
		user=(String)PointsConfig.get().get("sql.username");
		table = (String)PointsConfig.get().get("sql.table");
		
		try {			
			synchronized (this) {
				if(getConnection()!=null && !getConnection().isClosed()) {
					return;
				}
				
				Class.forName("com.mysql.jdbc.Driver");
				setConnection(DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database,this.user,this.pass));
			}
		}catch(Exception e) {
			e.printStackTrace();	
		}
	}
	
	public Connection getConnection() {
		return connection;	
	}
	
	public void setConnection(Connection connection) {
		this.connection=connection;
	}
}

