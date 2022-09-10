package mc.metype.points;

import mc.metype.points.GUIs.MainMenuGUI;
import mc.metype.points.commands.PointsCommand;
import mc.metype.points.commands.subcommands.*;
import mc.metype.points.earning_schemes.IntervalEarningScheme;
import mc.metype.points.files.MessagesConfig;
import mc.metype.points.files.PointsConfig;
import mc.metype.points.handlers.GUIHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public final class Points extends JavaPlugin implements Listener {

    public static String errorArgs = "";

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
        pointsCommand.registerCommand("set", new SetSubCommand(this));
        pointsCommand.registerCommand("get", new GetSubCommand(this));
        pointsCommand.registerCommand("give", new GiveSubCommand(this));
        pointsCommand.registerCommand("force_path", new ForceGUIPath());

        Objects.requireNonNull(getCommand("points")).setExecutor(pointsCommand);
        Objects.requireNonNull(getCommand("points")).setTabCompleter(pointsCommand);

        GUIHandler.RegisterGUI("menu", new MainMenuGUI());

        PointsConfig.setup(this);
        MessagesConfig.setup(this);
        SetupEarningSchemes();
    }

    void SetupEarningSchemes() {
        boolean enabled = (boolean) PointsConfig.get().get("point_earning.interval.enabled", false);
        int secondEarningInterval = (int) PointsConfig.get().get("point_earning.interval.interval", 60);
        int pointsPerInterval = (int) PointsConfig.get().get("point_earning.interval.per_interval", 5);
        IntervalEarningScheme.Init(enabled, secondEarningInterval, pointsPerInterval, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
