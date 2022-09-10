package mc.metype.points;

import mc.metype.points.files.MessagesConfig;
import mc.metype.points.files.PointsConfig;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.Collections;

public class MessageParser {

    static ArrayList<String> specialCaseKeys = new ArrayList<>(Collections.singleton("prefix"));

    public static String parseMessage(String key, OfflinePlayer player){
        if(specialCaseKeys.contains(key)) {
            return (String)MessagesConfig.get().get(key, "");
        }

        if(!MessagesConfig.get().contains(key)) {
            return ChatColor.DARK_RED + "Error : Invalid key '" + key + "'.";
        }
        return PlaceholderAPI.setPlaceholders(player, (String)MessagesConfig.get().get(key, ""));
    }

}
