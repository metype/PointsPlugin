package mc.metype.points.commands.subcommands;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import mc.metype.points.handlers.GUIHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ForceGUIPath extends SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(MessageParser.parseMessage("warning.only_for_players", null));
            return true;
        }
        if(!GUIHandler.handlePath(args[1], ((Player)sender).getUniqueId())) {
            sender.sendMessage(MessageParser.parseMessage("error.general_failure", null));
        }
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String[] getArgTypes() {
        return new String[]{"string"};
    }
}
