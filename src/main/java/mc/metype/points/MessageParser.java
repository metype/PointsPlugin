package mc.metype.points;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

public class MessageParser {

    public static String parseMessage(String key, Player player){
        if(keyDoesNotExist /*TODO : Actually make this condition*/) {
            return ChatColor.DARK_RED + "Error : Invalid key '" + key + "'.";
        }
        return PlaceholderAPI.setPlaceholders(player, actualMessage /*TODO: Get a message by key*/);
    }

}
