package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MainMenuGUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance) {
        instance.GUI = Bukkit.getServer().createInventory(null, 27, MessageParser.parseMessage("title.points_menu", null));
        for(int i = 0; i < 27; i++){
            instance.GUI.setItem(i, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        }
        instance.GUI.setItem(10, new ItemStack(Material.EMERALD_BLOCK));
        instance.GUI.setItem(13, new ItemStack(Material.EMERALD_BLOCK));
        instance.GUI.setItem(16, new ItemStack(Material.EMERALD_BLOCK));
        HashMap<Integer, String> links = new HashMap<>();
        links.put(10, "command/points balance");
        links.put(13, "command/points store");
        links.put(16, "command/help");
        instance.state = GUIState.create(GUIScreen.MainMenu, "menu", links);
        instance.state.currentGUI = instance;
        return instance;
    }
}
