package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.files.StoreConfig;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PurchaseGUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        instance.GUI = Bukkit.getServer().createInventory(null, 27, MessageParser.parseMessage("title.store", Bukkit.getServer().getOfflinePlayer(player)));
        for(int i = 0; i < 27; i++){
            instance.GUI.setItem(i, Points.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
        StoreConfig.reload();
        HashMap<Integer, String> links = new HashMap<>();
        String storeKey = path.replaceAll("/",".").replace("purchase", "store");
        if(StoreConfig.get().getConfigurationSection(storeKey) != null) {
            instance.GUI.setItem(4, Points.createItem(((ItemStack)(StoreConfig.get().get(storeKey+".material"))).getType(), StoreConfig.get().get(storeKey+".name").toString(), StoreConfig.get().get(storeKey+".description").toString(), MessageParser.parseMessage("title.store_price", Bukkit.getServer().getOfflinePlayer(player)) + StoreConfig.get().get(storeKey+".price").toString()));
            instance.GUI.setItem(11, Points.createItem(Material.EMERALD_BLOCK, MessageParser.parseMessage("title.store_accept_purchase", Bukkit.getServer().getOfflinePlayer(player)),MessageParser.parseMessage("title.store_price", Bukkit.getServer().getOfflinePlayer(player)) + StoreConfig.get().get(storeKey+".price").toString()));
            instance.GUI.setItem(15, Points.createItem(Material.REDSTONE_BLOCK, MessageParser.parseMessage("title.store_cancel_purchase", Bukkit.getServer().getOfflinePlayer(player))));
            links.put(11, path.replace("purchase", "purchase_complete"));
            links.put(15, "close");
        }
        instance.state = GUIState.create(GUIScreen.StoreUI, path, links);
        instance.state.currentGUI = instance;
        return instance;
    }
}

