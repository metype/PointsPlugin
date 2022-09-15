package mc.metype.points.commands.subcommands;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import mc.metype.points.files.PointsConfig;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SetSubCommand extends SubCommand{

    JavaPlugin plugin;

    public SetSubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        OfflinePlayer[] players = plugin.getServer().getOfflinePlayers();

        for(OfflinePlayer player : players) {
            try {
                if (args[1].equalsIgnoreCase(player.getName())) {
                    PointsBalanceHandler.SetPoints(player.getUniqueId(), Integer.parseInt(args[2]));
                    sender.sendMessage(MessageParser.parseMessage("info.set_player_balance", player));
                    return true;
                }
            } catch (NumberFormatException e) {
                Points.errorArgs = args[2];
                sender.sendMessage(MessageParser.parseMessage("error.invalid_arguments.NAN", player));
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
        return new String[]{"name", "integer"};
    }
}
