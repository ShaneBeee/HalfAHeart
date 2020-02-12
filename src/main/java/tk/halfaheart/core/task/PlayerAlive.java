package tk.halfaheart.core.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.util.PlayerUtils;

public class PlayerAlive extends BukkitRunnable {

    public PlayerAlive(HalfAHeart plugin) {
        this.runTaskTimer(plugin, 0, 60 * 20);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtils.addMinuteAlive(player);
            PlayerUtils.setTablist(player);
        }
    }



}
