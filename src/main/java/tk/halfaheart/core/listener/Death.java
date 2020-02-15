package tk.halfaheart.core.listener;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.task.PlayerDeath;
import tk.halfaheart.core.util.PlayerUtils;
import tk.halfaheart.core.util.Util;

public class Death implements Listener {

    private HalfAHeart plugin;

    public Death(HalfAHeart plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        String msg = event.getDeathMessage();
        event.setDeathMessage("");

        Player player = event.getEntity();
        PlayerUtils.setDeathTime(player);
        if (PlayerUtils.isHardcore(player)) {
            event.getDrops().clear();
        }

        BukkitRunnable delay = new BukkitRunnable() {
            @Override
            public void run() {
                hardcoreRespawn(player, msg);
            }
        };
        delay.runTaskLater(this.plugin, 1);
    }

    private void hardcoreRespawn(Player player, String msg) {
        player.spigot().respawn();
        PlayerUtils.addDeath(player);
        PlayerUtils.resetMinutesAlive(player);
        PlayerUtils.setTablist(player);
        if (PlayerUtils.isHardcore(player) || player.getBedSpawnLocation() == null) {
            player.teleport(Util.RESPAWN);
        } else {
            player.teleport(player.getBedSpawnLocation());
            killMonsters(player.getLocation());
        }
        Util.broadcast("&6We lost one -> &c" + msg);
        Util.sendTitle(player, "&cGAME OVER", "&e" + msg.replace(player.getName(), "You"));
        Util.deathSound();
        if (PlayerUtils.isHardcore(player)) {
            new PlayerDeath(plugin, player);
        }
    }

    // Kill monsters nearby the player's bed location
    private void killMonsters(Location location) {
        World world = location.getWorld();
        if (world == null) return;

        for (Entity entity : world.getNearbyEntities(location, 10, 10, 10)) {
            if (entity instanceof Monster) {
                entity.remove();
            }
        }
    }

}
