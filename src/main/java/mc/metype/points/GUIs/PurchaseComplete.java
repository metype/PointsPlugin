package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import mc.metype.points.files.StoreConfig;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PurchaseComplete extends GUI {

    @Override
    public void show(UUID player) {
        GUIHandler.playersInGUI.remove(player);
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        StoreConfig.reload();
        String storeKey = path.replaceAll("/",".").replace("purchase_complete", "store");
        System.out.println(storeKey);
        PointsBalanceHandler.GivePoints(player, -((Integer)StoreConfig.get().get(storeKey+".price")));
        GUIHandler.handlePath("console_command/"+ PlaceholderAPI.setPlaceholders(Bukkit.getServer().getOfflinePlayer(player), StoreConfig.get().get(storeKey+".command").toString()), player, null);
        return instance;
    }

}
