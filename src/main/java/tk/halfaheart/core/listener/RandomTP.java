package tk.halfaheart.core.listener;

import org.bukkit.Chunk;
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
import tk.halfaheart.core.HalfAHeart;
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
            //randomTP(player);
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
        int x = this.random.nextInt(30000) + 500;
        int z = this.random.nextInt(30000) + 500;
        Block block = Util.WORLD.getHighestBlockAt(x, z);
        Chunk chunk = Util.WORLD.getChunkAt(block.getLocation());
        chunk.addPluginChunkTicket(this.plugin);
        Block up = block.getRelative(BlockFace.UP);
        Block two = up.getRelative(BlockFace.UP);
        Material mat = block.getType();
        switch (mat) {
            case GRASS_BLOCK:
            case SAND:
            case PODZOL:
            case GRAVEL:
            case DIRT:
                if (up.getType() == Material.AIR && two.getType() == Material.AIR) {
                    player.teleport(up.getLocation());
                    this.clicked.remove(player);
                    Util.WORLD.setTime(0);
                    Util.WORLD.setStorm(false);
                    return;
                }
        }
        Util.sendColMsg(player, "&6Still looking...", true);
        randomTP(player);
    }

}
