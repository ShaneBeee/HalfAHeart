package tk.halfaheart.core.listener;

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
        event.getDrops().clear();

        BukkitRunnable delay = new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.teleport(Util.RESPAWN);
                PlayerUtils.addDeath(player);
                PlayerUtils.resetMinutesAlive(player);
                PlayerUtils.setTablist(player);
                Util.broadcast("&6We lost one -> &c" + msg);
                assert msg != null;
                Util.sendTitle(player, "&cGAME OVER", "&e" + msg.replace(player.getName(), "You"));
                Util.deathSound();
                new PlayerDeath(plugin, player);
            }
        };
        delay.runTaskLater(this.plugin, 1);

    }

}
