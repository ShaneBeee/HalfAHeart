package tk.halfaheart.core.task;

import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.util.Util;

import java.util.Random;

public class RandomWeather extends BukkitRunnable {

    private final int rainChance;
    private final int thunderChance;
    private final Random random;

    public RandomWeather(HalfAHeart plugin) {
        int minutes = 5;
        this.rainChance = 7; // These chances need to be tested
        this.thunderChance = 25;
        this.random = new Random();
        this.runTaskTimer(plugin, minutes * 60 * 20, minutes * 60 * 20);
    }

    @Override
    public void run() {
        if (!Util.WORLD.hasStorm()) {
            int r = this.random.nextInt(100) + 1;
            if (r <= this.rainChance) {
                Util.WORLD.setStorm(true);
                int duration = (this.random.nextInt(3) + 2) * 60 * 20;
                Util.WORLD.setWeatherDuration(duration);
                if (this.random.nextInt(100) + 1 <= this.thunderChance) {
                    Util.WORLD.setThundering(true);
                    Util.WORLD.setThunderDuration(duration);
                }
            }
        }
    }

}
