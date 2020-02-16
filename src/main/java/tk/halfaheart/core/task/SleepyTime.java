package tk.halfaheart.core.task;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.SleepyBar;

public class SleepyTime extends BukkitRunnable {

    private HalfAHeart plugin;
    private World world;
    private SleepyBar sleepyBar;

    public SleepyTime(HalfAHeart plugin) {
        this.plugin = plugin;
        this.world = plugin.getServer().getWorlds().get(0);
        this.runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        if (world.getTime() > 12542) {
            if (this.sleepyBar == null) {
                this.sleepyBar = new SleepyBar(this.world);
                this.sleepyBar.initSleepyBar();
            } else {
                this.sleepyBar.updateSleepyBar();
            }
        } else {
            if (this.sleepyBar != null) {
                this.sleepyBar.clearSleepyBar();
                this.sleepyBar = null;
            }
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        if (this.sleepyBar != null) {
            this.sleepyBar.clearSleepyBar();
            this.sleepyBar = null;
        }
    }

}
