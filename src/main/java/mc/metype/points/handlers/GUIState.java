package mc.metype.points.handlers;

import mc.metype.points.GUIs.GUI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIState {
    public GUIScreen currentScreen;
    public String path;
    public HashMap<Integer, String> links;

    public GUI currentGUI;

    public boolean isClosing = false;

    public HashMap<String, String> params = new HashMap<>();

    public boolean keepInventoryInstance = false;

    public static @NotNull GUIState create(GUIScreen screen, String path, HashMap<Integer, String> links) {
        GUIState returnedState = new GUIState();
        returnedState.currentScreen = screen;
        returnedState.path = path;
        returnedState.links = links;
        return returnedState;
    }

    public static @NotNull GUIState create(GUI screen) {
        return screen.getState();
    }
}
