package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.files.StoreConfig;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class MaterialSelectedGUIHandler extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        if(path.split("/").length<3) return null;
        instance.state = GUIState.create(GUIScreen.MainMenu, path, null);
        if(previousState != null) instance.state.params = previousState.params;
        instance.state.keepInventoryInstance = false;
        instance.state.currentGUI = instance;

        if(path.split("/")[1].equalsIgnoreCase("create_category_material")) {
            String catKey = instance.state.params.get("store_category")+"."+instance.state.params.get("name").replaceAll("[ .,<>;:'\"{}\\[\\]=`~!@#$%^&*()]","_").toLowerCase();
            StoreConfig.get().set(catKey+".name", instance.state.params.get("name"));
            StoreConfig.get().set(catKey+".description", instance.state.params.get("description"));
            StoreConfig.get().set(catKey+".material", new ItemStack(Objects.requireNonNull(Material.getMaterial(path.split("/")[2]))));
            Objects.requireNonNull(Bukkit.getServer().getPlayer(player)).sendMessage(MessageParser.parseMessage("info.category_created",Bukkit.getServer().getPlayer(player)));
            StoreConfig.save();
            Objects.requireNonNull(Bukkit.getServer().getPlayer(player)).closeInventory();
        }

        return instance;
    }
}
