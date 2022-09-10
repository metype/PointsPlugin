package mc.metype.points.commands;

import mc.metype.points.MessageParser;
import mc.metype.points.commands.subcommands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointsCommand implements CommandExecutor, TabCompleter {

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

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabCompletions = new ArrayList<String>();
        if(args.length < 2) {
            for(String commandName : commands.keySet()) {
                if(args.length == 0) {
                    tabCompletions.add(commandName);
                } else if (commandName.contains(args[0])) {
                    tabCompletions.add(commandName);
                }
            }
        } else {
            for(String commandName : commands.keySet()) {
                if(commandName.equalsIgnoreCase(args[0])) {
                    String[] argTypes = commands.get(args[0]).getArgTypes();
                    if(argTypes.length > 0 && args.length < argTypes.length + 2) {
                        if(argTypes[args.length - 2].equalsIgnoreCase("name")) {
                            for(Player p : sender.getServer().getOnlinePlayers()){
                                tabCompletions.add(p.getName());
                            }
                        }
                        if(argTypes[args.length - 2].equalsIgnoreCase("integer")) {
                            tabCompletions.add("0");
                        }
                    }
                }
            }
        }
        return tabCompletions;
    }
}
