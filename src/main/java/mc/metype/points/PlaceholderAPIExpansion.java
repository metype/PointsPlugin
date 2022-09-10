package mc.metype.points;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.Formatter;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Metype";
    }

    @Override
    public String getIdentifier() {
        return "points";
    }

    @Override
    public String getVersion() {
        return "3.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        if(params.equalsIgnoreCase("prefix")) {
            return MessageParser.parseMessage("prefix", null);
        }
        if(params.equalsIgnoreCase("balance")) {
            if(p != null) return (new Formatter().format("%,d", PointsBalanceHandler.GetPoints(p.getUniqueId()))).toString();

            return "0";
        }
        if(params.equalsIgnoreCase("error_args")) {
            return Points.errorArgs;
        }
        if(params.equalsIgnoreCase("user")) {
            if(p == null) return "CONSOLE";
            return p.getName();
        }
        return super.onRequest(p, params);
    }
}
