package tk.halfaheart.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.Data;
import tk.halfaheart.core.util.Util;

public class Break implements Listener {

    private Data data;

    public Break(HalfAHeart plugin) {
        this.data = plugin.getData();
    }

    @EventHandler
    private void breakSpawn(BlockBreakEvent event) {
        int x = event.getBlock().getX();
        int z = event.getBlock().getZ();
        if (x < 300 && x > 200 && z < 300 && z > 200) {
            Player player = event.getPlayer();
            if (!player.hasPermission("halfaheart.break")) {
                event.setCancelled(true);
                Util.sendColMsg(player, "&cStop trying to break the spawn!", true);
            }
        }
    }

}
