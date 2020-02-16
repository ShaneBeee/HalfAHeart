package tk.halfaheart.core.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import tk.halfaheart.core.HalfAHeart;

import java.util.Random;

public class RandomWeather extends BukkitRunnable {

    private int rainChance;
    private int thunderChance;
    private World world;
    private Random random;

    public RandomWeather(HalfAHeart plugin) {
        int minutes = 5;
        this.rainChance = 10; // These chances need to be tested
        this.thunderChance = 25;
        this.world = Bukkit.getWorlds().get(0);
        this.random = new Random();
        this.runTaskTimer(plugin, minutes * 60 * 20, minutes * 60 * 20);
    }

    @Override
    public void run() {
        if (!this.world.hasStorm()) {
            int r = this.random.nextInt(100) + 1;
            if (r <= this.rainChance) {
                this.world.setStorm(true);
                int duration = (this.random.nextInt(3) + 2) * 60 * 20;
                this.world.setWeatherDuration(duration);
                if (this.random.nextInt(100) + 1 <= this.thunderChance) {
                    this.world.setThundering(true);
                    this.world.setThunderDuration(duration);
                }
            }
        }
    }

}
