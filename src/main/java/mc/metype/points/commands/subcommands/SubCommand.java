package mc.metype.points.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand{
    public abstract boolean onCommand(CommandSender sender, Command command, String[] args);
    public abstract String getPermission();
}
