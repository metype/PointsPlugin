package mc.metype.points.commands;

import mc.metype.points.MessageParser;
import mc.metype.points.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PointsCommand implements CommandExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(!commands.containsKey(args[0].toLowerCase())){
            if(sender instanceof Player)
                sender.sendMessage(MessageParser.parseMessage("warning.subcommand_not_found", (Player)sender));
            else
                sender.sendMessage(MessageParser.parseMessage("warning.subcommand_not_found", null));
            return true;
        }

        return commands.get(args[0]).onCommand(sender, command, args);
    }

    public void registerCommand(String cmd, SubCommand subCommand) {
        commands.put(cmd, subCommand);
    }
}
