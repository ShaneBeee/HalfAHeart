package tk.halfaheart.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.Data;
import tk.halfaheart.core.util.PlayerUtils;
import tk.halfaheart.core.util.Util;

@SuppressWarnings("ConstantConditions")
public class Join implements Listener {

    private final Data data;

    public Join(HalfAHeart plugin) {
        this.data = plugin.getData();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        String name = player.getName();
        int total_players = Bukkit.getOfflinePlayers().length;

        // First Join
        if (!player.hasPlayedBefore()) {
            // Set half a heart
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
            player.setHealth(1);
            player.teleport(Util.SPAWN);
            Util.broadcast("&7[&a+&7] &6Welcome new player &b" + name + " &7[&a#" + total_players + "&7]");
        } else {
            Util.broadcast("&7[&a+&7] &6Welcome back &b" + name);
        }
        PlayerUtils.setTablist(player);
        this.data.loadPlayerData(player.getUniqueId());
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Player player = event.getPlayer();
        String name = player.getName();
        if (PlayerUtils.hasDiedRecently(player)) {
            Util.broadcast("&7[&c-&7] &b" + name + " &6rage quit!");
        } else {
            Util.broadcast("&7[&c-&7] &6See ya later &b" + name);
        }
    }

}
