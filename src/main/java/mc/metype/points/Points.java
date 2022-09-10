package mc.metype.points;

import mc.metype.points.commands.PointsCommand;
import mc.metype.points.commands.subcommands.BalanceSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Points extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIExpansion().register();
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().log(Level.SEVERE, "Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        PointsCommand pointsCommand = new PointsCommand();
        pointsCommand.registerCommand("balance", new BalanceSubCommand());

        Objects.requireNonNull(getCommand("points")).setExecutor(pointsCommand);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
