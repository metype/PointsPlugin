package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import net.wesjd.anvilgui.AnvilGUI;

import java.util.*;

public class CategorySelectedGUIHandler extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        HashMap<Integer, String> links = new HashMap<>();
        instance.state = GUIState.create(GUIScreen.StoreUI, path, null);
        if(previousState != null) instance.state.params = previousState.params;

        if(path.split("/")[1].equalsIgnoreCase("create_category_name")) {
            String[] stringArray = Arrays.copyOfRange(path.split("/"), 2, path.split("/").length);
            StringBuilder sb = new StringBuilder();
            sb.append("store.");
            int i = 0;
            for (String s : stringArray) {
                sb.append(s).append((i < stringArray.length-1) ? "." : "");
                i++;
            }
            String storeCategory = sb.toString();

            instance.state.params.put("store_category", storeCategory);

            new AnvilGUI.Builder()
                    .onComplete((p, text) -> {
                        instance.state.params.put("name", text);
                        GUIHandler.handlePath(path.replace("create_category_name","create_category_description"), player, null);
                        return AnvilGUI.Response.close();
                    })                     //prevents the inventory from being closed
                    .text(MessageParser.parseMessage("title.category_set_name", Bukkit.getServer().getPlayer(player)))                              //sets the text the GUI should start with
                    .itemLeft(new ItemStack(Material.PAPER))                         //use a custom item for the first slot//use a custom item for the second slot
                    .title(MessageParser.parseMessage("title.category_set_name", Bukkit.getServer().getPlayer(player)))                                //set the title of the GUI (only works in 1.14+)
                    .plugin(Bukkit.getPluginManager().getPlugin("Points"))                                                    //set the plugin instance
                    .open(Bukkit.getServer().getPlayer(player));
        }
        if(path.split("/")[1].equalsIgnoreCase("create_category_description")) {
            if(previousState != null) instance.state.params = previousState.params;
            new AnvilGUI.Builder()
                    .onComplete((p, text) -> {
                        instance.state.params.put("description", text);
                        GUIHandler.handlePath("select_material/0/create_category_material", player, null);
                        return AnvilGUI.Response.close();
                    })                     //prevents the inventory from being closed
                    .text(MessageParser.parseMessage("title.category_set_description", Bukkit.getServer().getPlayer(player)))                              //sets the text the GUI should start with
                    .itemLeft(new ItemStack(Material.PAPER))                      //use a custom item for the first slot//use a custom item for the second slot
                    .title(MessageParser.parseMessage("title.category_set_description", Bukkit.getServer().getPlayer(player)))                                //set the title of the GUI (only works in 1.14+)
                    .plugin(Bukkit.getPluginManager().getPlugin("Points"))                                                    //set the plugin instance
                    .open(Bukkit.getServer().getPlayer(player));
        }

        instance.state.links = links;
        instance.state.currentGUI = instance;
        return instance;
    }
}
