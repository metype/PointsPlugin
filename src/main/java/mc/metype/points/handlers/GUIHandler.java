package mc.metype.points.handlers;

import mc.metype.points.GUIs.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GUIHandler implements Listener {
    public static HashMap<UUID, GUIState> playersInGUI = new HashMap<>();
    static HashMap<String, GUI>  guis = new HashMap<>();

    public static void RegisterGUI(String highLevelPath, GUI handler) {
        guis.put(highLevelPath, handler);
    }

    public static boolean handlePath(String path, UUID player){
        if(guis.containsKey(path.split("/")[0])){
            GUI gui = new GUI();
            guis.get(path.split("/")[0]).init(gui);
            gui.show(player);
            return true;
        } else if (path.split("/")[0].equalsIgnoreCase("command")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), path.split("/")[1]);
        }
        return false;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(playersInGUI.containsKey(p.getUniqueId())) {
            event.setCancelled(true);
            GUIState state = playersInGUI.get(p.getUniqueId());
            if(Objects.equals(event.getClickedInventory(), state.currentGUI.GUI)) {
                if (state.links.containsKey(event.getSlot())) {
                    handlePath(state.links.get(event.getSlot()), p.getUniqueId());
                }
            }
        }
    }
}
