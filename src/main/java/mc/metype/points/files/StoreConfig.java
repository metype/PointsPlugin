package mc.metype.points.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class StoreConfig {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "store.yml");
        if (!file.exists()) {
            boolean a = file.getParentFile().mkdirs();
            plugin.saveResource("store.yml", false);
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
