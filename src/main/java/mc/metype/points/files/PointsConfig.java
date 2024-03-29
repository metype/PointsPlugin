package mc.metype.points.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PointsConfig {

    private static File file;
    private static FileConfiguration customFile;

    public static void setup(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "points.yml");
        if (!file.exists()) {
            boolean a = file.getParentFile().mkdirs();
            plugin.saveResource("points.yml", false);
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
