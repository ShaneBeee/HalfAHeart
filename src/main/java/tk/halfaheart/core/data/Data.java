package tk.halfaheart.core.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import tk.halfaheart.core.HalfAHeart;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Data {

    private final HalfAHeart plugin;

    private final Map<UUID, List<Location>> playerLocationMap = new HashMap<>();
    private final Map<UUID, FileConfiguration> playerConfigMap = new HashMap<>();

    public Data(HalfAHeart plugin) {
        this.plugin = plugin;
        loadOnlinePlayerFiles();
    }

    private void loadOnlinePlayerFiles() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayerData(player.getUniqueId());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadPlayerData(UUID uuid) {
        File file = new File(this.plugin.getDataFolder() + "/data", uuid.toString() + ".yml");
        FileConfiguration pConfig = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            saveFile(pConfig, file);
        }
        List<Location> locations;
        if (pConfig.isSet("containers")) {
            locations = (List<Location>) pConfig.getList("containers");
        } else {
            locations = new ArrayList<>();
        }
        this.playerLocationMap.put(uuid, locations);
        this.playerConfigMap.put(uuid, pConfig);
    }

    private void saveFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveForPlayer(UUID uuid) {
        List<Location> locs = playerLocationMap.get(uuid);
        FileConfiguration config = playerConfigMap.get(uuid);
        File file = new File(this.plugin.getDataFolder() + "/data", uuid.toString() + ".yml");
        config.set("containers", locs);
        saveFile(config, file);
    }

    public void resetPlayerData(UUID uuid) {
        this.playerLocationMap.get(uuid).clear();
        FileConfiguration config = playerConfigMap.get(uuid);
        File file = new File(this.plugin.getDataFolder() + "/data", uuid.toString() + ".yml");
        config.set("containers", new ArrayList<>());
        saveFile(config, file);
    }

    public void addLocation(UUID uuid, Location location, boolean save) {
        loadPlayerData(uuid);
        this.playerLocationMap.get(uuid).add(location);
        if (save) saveForPlayer(uuid);
    }

    public void removeLocation(UUID uuid, Location location, boolean save) {
        this.playerLocationMap.get(uuid).remove(location);
        if (save) saveForPlayer(uuid);
    }

    public List<Location> getLocations(UUID uuid) {
        loadPlayerData(uuid);
        return this.playerLocationMap.get(uuid);
    }


}
