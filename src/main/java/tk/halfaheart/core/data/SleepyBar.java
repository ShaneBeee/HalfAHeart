package tk.halfaheart.core.data;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import tk.halfaheart.core.HalfAHeart;

public class SleepyBar {

    private World world;
    private boolean storm;
    private NamespacedKey key;
    private KeyedBossBar bossBar;

    public SleepyBar(World world) {
        this.world = world;
        this.storm = false;
    }

    public void initSleepyBar() {
        this.storm = this.world.isThundering();
        this.key = new NamespacedKey(HalfAHeart.getInstance(), "sleepybar-" + world.getName());
        String title = (this.storm ? "Storm Tracker --" : "Sleepy Time --") + " Sleeping: " + getSleeping() + "/" + getOnline();
        this.bossBar = Bukkit.createBossBar(key, title, BarColor.RED, BarStyle.SEGMENTED_20);
        this.bossBar.setProgress(getSleepingPercent());
        addPlayers();
    }

    public void updateSleepyBar() {
        double sleeping = getSleepingPercent();
        if (sleeping > 0.98) {
            this.bossBar.setColor(BarColor.GREEN);
        } else if (sleeping > 0.25) {
            this.bossBar.setColor(BarColor.YELLOW);
        } else {
            this.bossBar.setColor(BarColor.RED);
        }
        String prefix = this.storm ? "Storm Tracker --" : "Sleepy Time --";
        this.bossBar.setTitle(prefix + " Sleeping: " + getSleeping() + "/" + getOnline());
        this.bossBar.setProgress(sleeping);
        addPlayers();
    }

    public void clearSleepyBar() {
        this.bossBar.removeAll();
        Bukkit.removeBossBar(this.key);
    }

    private double getSleepingPercent() {
        if (getOnline() > 0) {
            return ((double) getSleeping() / (double) getOnline());
        } else {
            return 0.0;
        }
    }

    private int getSleeping() {
        int i = 0;
        for (Player player : this.world.getPlayers()) {
            if (player.isSleeping()) i++;
        }
        return i;
    }

    private int getOnline() {
        return this.world.getPlayers().size();
    }

    private void addPlayers() {
        for (Player player : this.world.getPlayers()) {
            if (!this.bossBar.getPlayers().contains(player)) {
                this.bossBar.addPlayer(player);
            }
        }
    }

}
