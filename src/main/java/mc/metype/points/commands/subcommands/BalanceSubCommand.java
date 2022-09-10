package mc.metype.points.commands.subcommands;

import mc.metype.points.MessageParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BalanceSubCommand extends SubCommand{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(MessageParser.parseMessage("warning.only_for_players", null));
            return true;
        }
        sender.sendMessage(MessageParser.parseMessage("info.player_balance", (Player)sender));
        return true;
    }

    @Override
    public String getPermission() {
        return null;
    }
}
