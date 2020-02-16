package tk.halfaheart.core;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tk.halfaheart.core.command.HardcoreToggle;
import tk.halfaheart.core.command.Sign;
import tk.halfaheart.core.command.Test;
import tk.halfaheart.core.data.Data;
import tk.halfaheart.core.listener.Break;
import tk.halfaheart.core.listener.Chat;
import tk.halfaheart.core.listener.Container;
import tk.halfaheart.core.listener.Death;
import tk.halfaheart.core.listener.Join;
import tk.halfaheart.core.listener.RandomTP;
import tk.halfaheart.core.task.PlayerAlive;
import tk.halfaheart.core.task.RandomWeather;
import tk.halfaheart.core.task.SleepyTime;
import tk.halfaheart.core.util.Util;

@SuppressWarnings("ConstantConditions")
public class HalfAHeart extends JavaPlugin {

    private static HalfAHeart instance;
    private Data data;
    private PlayerAlive player_timer;
    private SleepyTime sleepy_time;
    private RandomWeather random_weather;

    @Override
    public void onEnable() {
        instance = this;

        this.data = new Data(this);
        registerCommands();
        registerListeners();
        registerTasks();

        Util.log("&aSuccessfully loaded!");
    }

    @Override
    public void onDisable() {
        cancelTasks();
    }

    private void registerCommands() {
        getCommand("sign").setExecutor(new Sign());
        getCommand("test").setExecutor(new Test(this));
        getCommand("hardcore").setExecutor(new HardcoreToggle());
    }

    private void registerListeners() {
        register(new Join(this));
        register(new Death(this));
        register(new RandomTP(this));
        register(new Break(this));
        register(new Container(this));
        register(new Chat());
    }

    private void registerTasks() {
        this.player_timer = new PlayerAlive(this);
        this.sleepy_time = new SleepyTime(this);
        this.random_weather = new RandomWeather(this);
    }

    private void cancelTasks() {
        this.player_timer.cancel();
        this.player_timer = null;
        this.sleepy_time.cancel();
        this.sleepy_time = null;
        this.random_weather.cancel();
        this.random_weather = null;
    }

    private void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static HalfAHeart getInstance() {
        return instance;
    }

    public Data getData() {
        return data;
    }

}
