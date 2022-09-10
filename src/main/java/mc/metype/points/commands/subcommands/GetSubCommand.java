package mc.metype.points.commands.subcommands;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class GetSubCommand extends SubCommand{

    JavaPlugin plugin;

    public GetSubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        OfflinePlayer[] players = plugin.getServer().getOfflinePlayers();

        for(OfflinePlayer player : players) {
            if (args[1].equalsIgnoreCase(player.getName())) {
                sender.sendMessage(MessageParser.parseMessage("info.other_player_balance", player));
                return true;
            }
        }

        Points.errorArgs = args[1];
        sender.sendMessage(MessageParser.parseMessage("error.invalid_user", null));
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String[] getArgTypes() {
        return new String[]{"name"};
    }
}
