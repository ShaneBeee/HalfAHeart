package tk.halfaheart.core.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.Data;
import tk.halfaheart.core.util.BlockUtils;

import java.util.UUID;

public class Container implements Listener {

    private Data data;

    public Container(HalfAHeart plugin) {
        this.data = plugin.getData();
    }

    @EventHandler
    private void onPlaceContainer(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (BlockUtils.isContainer(block.getType())) {
            this.data.addLocation(player.getUniqueId(), block.getLocation(), true);
            BlockUtils.addHolder(uuid, block.getState());
        }
    }

    @EventHandler
    private void onBreakContainer(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (BlockUtils.isContainer(block.getType())) {
            this.data.removeLocation(player.getUniqueId(), block.getLocation(), true);
            BlockUtils.removeHolders(block.getState());
        }
    }

    @EventHandler
    private void onOpenContainer(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Block block = event.getClickedBlock();
        if (block == null) return;

        if (BlockUtils.isContainer(block.getType())) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                BlockUtils.addHolder(uuid, block.getState());
                this.data.addLocation(uuid, block.getLocation(), true);
            }
        }
    }

}
