package tk.halfaheart.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tk.halfaheart.core.util.PlayerUtils;
import tk.halfaheart.core.util.Util;

@SuppressWarnings("ConstantConditions")
public class Join implements Listener {

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
            Util.broadcast("&6Welcome new player &b" + name + " &7[&a#" + total_players + "&7]");
        } else {
            Util.broadcast("&6Welcome back &b" + name);
        }
        PlayerUtils.setTablist(player);
    }

}
