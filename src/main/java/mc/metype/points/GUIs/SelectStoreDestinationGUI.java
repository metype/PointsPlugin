package mc.metype.points.GUIs;

import mc.metype.points.MessageParser;
import mc.metype.points.Points;
import mc.metype.points.PointsBalanceHandler;
import mc.metype.points.files.StoreConfig;
import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIScreen;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SelectStoreDestinationGUI extends GUI {
    @Override
    public GUIState getState() {
        return state;
    }

    @Override
    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        if(instance.GUI == null)
            instance.GUI = Bukkit.getServer().createInventory(null, 27, MessageParser.parseMessage("title.store_select_category", Bukkit.getServer().getOfflinePlayer(player)));

        for(int i = 0; i < 27; i++){
            instance.GUI.setItem(i, Points.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
        StoreConfig.reload();
        HashMap<Integer, String> links = new HashMap<>();

        String[] stringArray = Arrays.copyOfRange(path.split("/"), 2, path.split("/").length);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String s : stringArray) {
            sb.append(s).append((i < stringArray.length-1) ? "/" : "");
            i++;
        }
        String str = sb.toString();

        String storeKey = "store."+str.replaceAll("/",".");
        if(StoreConfig.get().getConfigurationSection(storeKey) != null && StoreConfig.get().getConfigurationSection(storeKey).getKeys(false).size() != 0) {
            int index = 0;
            if(path.split("/").length > 1){
                instance.GUI.setItem(index, Points.createItem(Material.ARROW, MessageParser.parseMessage("title.store_back", null), MessageParser.parseMessage("title.store_back_description", null)));

                stringArray = Arrays.copyOf(path.split("/"), path.split("/").length-1);
                sb = new StringBuilder();
                i = 0;
                for (String s : stringArray) {
                    sb.append(s).append((i < stringArray.length-1) ? "/" : "");
                    i++;
                }
                str = sb.toString();
                links.put(index, str);
                index++;
            }
            Set<String> keys = Objects.requireNonNull(StoreConfig.get().getConfigurationSection(storeKey)).getKeys(false);
            for(String key : keys){
                if(StoreConfig.get().getConfigurationSection(storeKey+"."+key) == null) continue;
                boolean category = !StoreConfig.get().getConfigurationSection(storeKey+"."+key).getKeys(false).contains("price");
                if(category) {
                    instance.GUI.setItem(index, Points.createItem(((ItemStack)(StoreConfig.get().get(storeKey+"."+key+".material"))).getType(), StoreConfig.get().get(storeKey+"."+key+".name").toString(), StoreConfig.get().get(storeKey+"."+key+".description").toString()));
                    links.put(index, path + "/" + key);
                }
                index++;
            }
        }

        HashMap<String, String> modeMap = new HashMap<>();

        modeMap.put("create_category","create_category_name");

        instance.GUI.setItem(26, Points.createItem(Material.EMERALD_BLOCK, MessageParser.parseMessage("title.select_category_confirm", Bukkit.getServer().getOfflinePlayer(player))));

        if(modeMap.containsKey(path.split("/")[1]))
            path = path.replace(path.split("/")[1], modeMap.get(path.split("/")[1]));

        links.put(26, path.replace("select_category","category_selected"));

        instance.GUI.setItem(18, Points.createItem(Material.REDSTONE_BLOCK, MessageParser.parseMessage("title.select_category_cancel", Bukkit.getServer().getOfflinePlayer(player))));
        links.put(18, "close");

        instance.state = GUIState.create(GUIScreen.StoreUI, path, links);
        instance.state.keepInventoryInstance = true;
        instance.state.currentGUI = instance;
        return instance;
    }
}
