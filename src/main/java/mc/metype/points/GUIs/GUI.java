package mc.metype.points.GUIs;

import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class GUI {

    UUID activePlayer;
    public Inventory GUI;
    GUIState state;

    public GUI init(GUI instance, String path, UUID player, ItemStack linkedItem, GUIState previousState) {
        return this;
    }

    public GUIState getState() {
        return state;
    }

    public void show(UUID player) {
        if(this.GUI != null && this.activePlayer == null) {
            Objects.requireNonNull(Bukkit.getServer().getPlayer(player)).openInventory(GUI);
        }
        if(this.activePlayer != null)
            this.activePlayer = player;
        GUIHandler.playersInGUI.put(player, state);
    }

    public void close() {
        if(GUIHandler.playersInGUI.get(this.activePlayer) != null)
            GUIHandler.playersInGUI.get(this.activePlayer).isClosing = true;
        Objects.requireNonNull(Bukkit.getServer().getPlayer(this.activePlayer)).closeInventory();
        GUIHandler.playersInGUI.remove(this.activePlayer);
    }

}
