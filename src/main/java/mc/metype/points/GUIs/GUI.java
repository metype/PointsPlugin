package mc.metype.points.GUIs;

import mc.metype.points.handlers.GUIHandler;
import mc.metype.points.handlers.GUIState;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Objects;
import java.util.UUID;

public class GUI {

    UUID activePlayer;
    public Inventory GUI;
    GUIState state;

    public GUI init(GUI instance) {
        return this;
    }

    public GUIState getState() {
        return state;
    }

    public void show(UUID player) {
        this.activePlayer = player;
        Objects.requireNonNull(Bukkit.getServer().getPlayer(player)).openInventory(GUI);
        GUIHandler.playersInGUI.put(player, state);
    }

    public void close() {
        Objects.requireNonNull(Bukkit.getServer().getPlayer(this.activePlayer)).closeInventory();
        GUIHandler.playersInGUI.remove(this.activePlayer);
    }

}
