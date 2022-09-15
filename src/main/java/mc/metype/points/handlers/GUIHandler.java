package mc.metype.points.handlers;

import mc.metype.points.GUIs.GUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftResultInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GUIHandler implements Listener {
    public static HashMap<UUID, GUIState> playersInGUI = new HashMap<>();
    static HashMap<String, GUI>  guis = new HashMap<>();

    public static void RegisterGUI(String highLevelPath, GUI handler) {
        guis.put(highLevelPath, handler);
    }

    public static boolean handlePath(String path, UUID player, ItemStack linkedItem) {
        if(guis.containsKey(path.split("/")[0])){
            GUI gui = new GUI();
            if(playersInGUI.get(player) != null)
                if(playersInGUI.get(player).keepInventoryInstance && playersInGUI.get(player).path.startsWith(path.split("/")[0]))
                    gui = playersInGUI.get(player).currentGUI;
            GUIState preState = null;
            if(playersInGUI.containsKey(player))
                preState = playersInGUI.get(player);
            guis.get(path.split("/")[0]).init(gui, path, player, linkedItem, preState);
            gui.show(player);
            return true;
        } else if (path.split("/")[0].equalsIgnoreCase("command")) {
            if(playersInGUI.containsKey(player)) playersInGUI.get(player).currentGUI.close();
            Objects.requireNonNull(Bukkit.getServer().getPlayer(player)).chat("/"+path.split("/")[1]);
        } else if (path.split("/")[0].equalsIgnoreCase("console_command")) {
            if(playersInGUI.containsKey(player)) playersInGUI.get(player).currentGUI.close();
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(Bukkit.getServer().getOfflinePlayer(player), path.split("/")[1]));
        }else if (path.split("/")[0].equalsIgnoreCase("close")) {
            if(playersInGUI.containsKey(player)) playersInGUI.get(player).currentGUI.close();
        }
        return false;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        Player p = Bukkit.getServer().getPlayer(event.getWhoClicked().getUniqueId());
        ItemStack currentItem = event.getCurrentItem();
        if(playersInGUI.containsKey(p.getUniqueId())) {
            event.setCancelled(true);
            GUIState state = playersInGUI.get(p.getUniqueId());
            if(Objects.equals(event.getClickedInventory(), state.currentGUI.GUI)) {
                if (state.links.containsKey(event.getSlot())) {
                    handlePath(state.links.get(event.getSlot()), p.getUniqueId(), currentItem);
                }
            }
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();
        if(playersInGUI.containsKey(p.getUniqueId())) {
//            if(!playersInGUI.get(p.getUniqueId()).isClosing)
//                playersInGUI.get(p.getUniqueId()).currentGUI.close();
            playersInGUI.remove(p.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if(playersInGUI.containsKey(p.getUniqueId())) {
            playersInGUI.get(p.getUniqueId()).currentGUI.close();
            playersInGUI.remove(p.getUniqueId());
        }
    }
}
