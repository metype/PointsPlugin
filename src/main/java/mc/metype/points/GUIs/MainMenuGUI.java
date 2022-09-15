package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class MainMenuGUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        instance.GUI = Bukkit.getServer().createInventory(null, 27, MessageParser.parseMessage("title.points_menu", null));
        for(int i = 0; i < 27; i++){
            instance.GUI.setItem(i, Points.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
        instance.GUI.setItem(11, Points.createItem(Material.EMERALD_BLOCK, MessageParser.parseMessage("title.menu_get_bal", null), "Get Your Points Balance"));
        instance.GUI.setItem(13, Points.createItem(Material.EMERALD_BLOCK, MessageParser.parseMessage("title.menu_show_help", null), "Open the Help Menu"));
        instance.GUI.setItem(15, Points.createItem(Material.EMERALD_BLOCK, MessageParser.parseMessage("title.menu_open_store", null), "Open the Points Store"));
        HashMap<Integer, String> links = new HashMap<>();
        links.put(10, "command/points balance");
        links.put(13, "command/help");
        links.put(16, "store");
        instance.state = GUIState.create(GUIScreen.MainMenu, path, links);
        instance.state.currentGUI = instance;
        return instance;
    }
}
