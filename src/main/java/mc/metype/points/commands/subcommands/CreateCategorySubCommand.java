package mc.metype.points.commands.subcommands;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CreateCategorySubCommand extends SubCommand{

    JavaPlugin plugin;

    public CreateCategorySubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(MessageParser.parseMessage("warning.only_for_players", null));
            return true;
        }
        Player playerSender = (Player)sender;



        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public String[] getArgTypes() {
        return new String[]{""};
    }
}
