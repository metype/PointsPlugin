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

public class SelectMaterialUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        if(path.split("/").length<3) return null;
        instance.state = GUIState.create(GUIScreen.MainMenu, path, null);
        if(previousState != null) instance.state.params = previousState.params;
        instance.state.keepInventoryInstance = true;
        instance.state.currentGUI = instance;

        if(instance.GUI == null)
            instance.GUI = Bukkit.getServer().createInventory(null, 54, MessageParser.parseMessage("title.select_material", null));

        HashMap<Integer, String> links = new HashMap<>();
        for(int i = 0; i < 54; i++){
            instance.GUI.setItem(i, Points.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
        boolean lastPage = false;
        int materialIndex = (Integer.parseInt(path.split("/")[1])*45)+1;
        boolean firstPage = materialIndex == 1;
        for(int i=0; i<45; i++){
            if(Material.values().length <= materialIndex + i) {
                lastPage = true;
                break;
            }
            instance.GUI.setItem(i, new ItemStack(Material.values()[materialIndex + i]));
            lastPage |= !Material.values()[materialIndex + i].isItem();
            links.put(i, "material_selected/"+path.split("/")[2]+"/"+Material.values()[materialIndex + i].name());
        }
        if(!lastPage){
            instance.GUI.setItem(50, Points.createItem(Material.ARROW, MessageParser.parseMessage("title.next", null)));
            links.put(50, "select_material/"+(Integer.parseInt(path.split("/")[1])+1)+"/"+path.split("/")[2]);
        }
        if(!firstPage){
            instance.GUI.setItem(48, Points.createItem(Material.ARROW, MessageParser.parseMessage("title.previous", null)));
            links.put(48, "select_material/"+(Integer.parseInt(path.split("/")[1])-1)+"/"+path.split("/")[2]);
        }

        instance.state.links = links;
        return instance;
    }
}
