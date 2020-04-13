package tk.halfaheart.core.task;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.Data;
import tk.halfaheart.core.util.BlockUtils;
import tk.halfaheart.core.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PlayerDeath extends BukkitRunnable {

    private final HalfAHeart plugin;
    private final UUID uuid;
    private final Player player;
    private final Data data;
    private final Iterator<Location> locations;
    private final List<Chunk> chunks = new ArrayList<>();

    public PlayerDeath(HalfAHeart plugin, Player player) {
        this.plugin = plugin;
        this.data = plugin.getData();
        this.uuid = player.getUniqueId();
        this.player = player;
        this.locations = data.getLocations(uuid).iterator();
        this.runTaskTimer(plugin, 20, 5);
    }

    @Override
    public void run() {
        int i = 0;

        while (i < 10 && this.locations.hasNext()) {
            Location location = this.locations.next();
            World world = location.getWorld();
            assert world != null;
            Chunk chunk = world.getChunkAt(location);
            if (!this.chunks.contains(chunk)) {
                chunk.addPluginChunkTicket(this.plugin);
                this.chunks.add(chunk);
            }
            breakBlock(location);
            i++;
        }
        if (!this.locations.hasNext()) {
            this.cancel();
            releaseChunks();
            this.data.resetPlayerData(uuid);
            Util.log("&eReset player data for: &b" + player.getName());
        }
    }

    private void breakBlock(Location location) {
        Block block = location.getBlock();
        BlockState state = block.getState();
        if (BlockUtils.hasHolders(state)) {
            List<String> holders = BlockUtils.getHolderList(state);
            if (holders.contains(uuid.toString()) && holders.size() > 1) {
                if (holders.get(0).equalsIgnoreCase(uuid.toString())) {
                    BlockUtils.removeHolder(player, state);
                    return;
                }
            }
        }
        if (state instanceof Container) {
            ((Container) state).getInventory().clear();
        }
        block.setType(Material.AIR);
    }

    private void releaseChunks() {
        for (Chunk chunk : this.chunks) {
            chunk.removePluginChunkTicket(this.plugin);
            chunk.unload(true);
        }
        this.chunks.clear();
    }

}
