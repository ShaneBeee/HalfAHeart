package tk.halfaheart.core.listener;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.util.BlockUtils;
import tk.halfaheart.core.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTP implements Listener {

    private Random random;
    private HalfAHeart plugin;
    private List<Player> clicked = new ArrayList<>();

    public RandomTP(HalfAHeart plugin) {
        this.random = new Random();
        this.plugin = plugin;
    }

    @EventHandler
    private void clickSign(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null || !Tag.SIGNS.isTagged(block.getType())) return;

        Sign sign = ((Sign) block.getState());
        if (sign.getLine(0).equalsIgnoreCase(Util.getColString("&7[&bRTP&7]"))) {
            if (!clicked.contains(player)) {
                this.clicked.add(player);
                Util.sendColMsg(player, "&6Looking for a suitable random location", true);
                randomTP(player);
            } else {
                Util.sendColMsg(player, "&cHold on... sheesh!!", true);
            }
        }
    }

    private void randomTP(Player player) {
        int x = this.random.nextInt(100000) + 1000;
        int z = this.random.nextInt(100000) + 1000;
        Block block = Util.WORLD.getHighestBlockAt(x, z);
        Chunk chunk = Util.WORLD.getChunkAt(block.getLocation());
        chunk.addPluginChunkTicket(this.plugin);
        Block up = block.getRelative(BlockFace.UP);
        Block two = up.getRelative(BlockFace.UP);
        Material mat = block.getType();
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (BlockUtils.canSpawnOn(mat)) {
                    if (BlockUtils.isSpawnableAt(up.getType()) && BlockUtils.isSpawnableAt(two.getType())) {
                        Location loc = up.getLocation();
                        loc.add(0.5, 0.5, 0.5);
                        player.teleport(loc);
                        clicked.remove(player);
                        Util.WORLD.setTime(0);
                        Util.WORLD.setStorm(false);
                        Util.sendColMsg(player, "&aGOOD LUCK!!!", true);
                        chunk.removePluginChunkTicket(plugin);
                        return;
                    }
                }
                Util.sendColMsg(player, "&6Still looking...", true);
                chunk.removePluginChunkTicket(plugin);
                chunk.unload();
                randomTP(player);
            }
        };
        runnable.runTaskLater(this.plugin, 20);
    }

}
