package tk.halfaheart.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.halfaheart.core.util.PlayerUtils;
import tk.halfaheart.core.util.Util;

public class Chat implements Listener {

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        long days = PlayerUtils.getDaysAlive(player);
        String msg = event.getMessage();
        event.setFormat(Util.getColString("&7[&b" + days + "&7][&a" + player.getName() + "&7] &c>> &r" + msg));
    }

}
