package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import mc.metype.points.files.PointsConfig;
import mc.metype.points.files.StoreConfig;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.*;

public class StoreGUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        if(instance.GUI == null)
            instance.GUI = Bukkit.getServer().createInventory(null, 27, MessageParser.parseMessage("title.store", Bukkit.getServer().getOfflinePlayer(player)));
        for(int i = 0; i < 27; i++){
            instance.GUI.setItem(i, Points.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
        StoreConfig.reload();
        HashMap<Integer, String> links = new HashMap<>();
        if(StoreConfig.get().getConfigurationSection(path.replaceAll("/",".")) != null && StoreConfig.get().getConfigurationSection(path.replaceAll("/",".")).getKeys(false).size() != 0) {
            int index = 0;
            if(path.split("/").length > 1){
                instance.GUI.setItem(index, Points.createItem(Material.ARROW, MessageParser.parseMessage("title.store_back", null), MessageParser.parseMessage("title.store_back_description", null)));

                String[] stringArray = Arrays.copyOf(path.split("/"), path.split("/").length-1);
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (String s : stringArray) {
                    sb.append(s).append((i < stringArray.length-1) ? "/" : "");
                    i++;
                }
                String str = sb.toString();
                links.put(index, str);
                index++;
            }
            Set<String> keys = Objects.requireNonNull(StoreConfig.get().getConfigurationSection(path.replaceAll("/", "."))).getKeys(false);
            for(String key : keys){
                if(StoreConfig.get().getConfigurationSection(path.replaceAll("/", ".")+"."+key) == null) continue;
                boolean category = !StoreConfig.get().getConfigurationSection(path.replaceAll("/", ".")+"."+key).getKeys(false).contains("price");
                if(category) {
                    instance.GUI.setItem(index, Points.createItem(((ItemStack)(StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".material"))).getType(), StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".name").toString() + " | " + MessageParser.parseMessage("title.category_descriptor", null), StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".description").toString()));
                    links.put(index, path + "/" + key);
                } else {
                    boolean canAfford = PointsBalanceHandler.GetPoints(player) >= ((Integer)StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".price"));

                    if(canAfford)
                        instance.GUI.setItem(index, Points.createItem(((ItemStack)(StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".material"))).getType(), StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".name").toString(), StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".description").toString(),MessageParser.parseMessage("title.store_price", null) + StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".price").toString()));
                    else
                        instance.GUI.setItem(index, Points.createItem(Material.RED_STAINED_GLASS_PANE, StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".name").toString(), StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".description").toString(),MessageParser.parseMessage("title.store_price", null) + StoreConfig.get().get(path.replaceAll("/", ".")+"."+key+".price").toString(),MessageParser.parseMessage("title.store_cannot_afford", null)));

                    String[] stringArray = Arrays.copyOfRange(path.split("/"), 1, path.split("/").length);
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (String s : stringArray) {
                        sb.append(s).append((i < stringArray.length-1) ? "/" : "");
                        i++;
                    }
                    String str = sb.toString();

                    if(canAfford)
                        links.put(index, "purchase/"+str+"/"+key);
                }
                index++;
            }
        }
        instance.state = GUIState.create(GUIScreen.StoreUI, path, links);
        instance.state.keepInventoryInstance = true;
        instance.state.currentGUI = instance;
        return instance;
    }
}
