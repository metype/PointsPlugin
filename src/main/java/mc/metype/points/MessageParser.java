package mc.metype.points;

import mc.metype.points.files.PointsConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.Collections;

public class MessageParser {

    static ArrayList<String> specialCaseKeys = new ArrayList<>(Collections.singleton("prefix"));

    public static String parseMessage(String key, Player player){
        if(specialCaseKeys.contains(key)) {
            return (String)PointsConfig.get().get(key, "");
        }

        if(!PointsConfig.get().contains(key)) {
            return ChatColor.DARK_RED + "Error : Invalid key '" + key + "'.";
        }
        return PlaceholderAPI.setPlaceholders(player, (String)PointsConfig.get().get(key, ""));
    }

}
