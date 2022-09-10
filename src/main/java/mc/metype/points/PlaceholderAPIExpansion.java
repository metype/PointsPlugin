package mc.metype.points;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

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
            if(p != null) return System.out.format("%,d", PointsBalanceHandler.GetPointsBalance(p.getUniqueId())).toString();

            return "0";
        }
        return super.onRequest(p, params);
    }
}
