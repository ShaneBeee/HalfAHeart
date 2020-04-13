package tk.halfaheart.core.task;

import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.data.SleepyBar;
import tk.halfaheart.core.util.Util;

public class SleepyTime extends BukkitRunnable {

    private SleepyBar sleepyBar;

    public SleepyTime(HalfAHeart plugin) {
        this.runTaskTimer(plugin, 20, 20);
    }

    @Override
    public void run() {
        if (Util.WORLD.getTime() > 12542 || Util.WORLD.isThundering()) {
            if (this.sleepyBar == null) {
                this.sleepyBar = new SleepyBar(Util.WORLD);
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
