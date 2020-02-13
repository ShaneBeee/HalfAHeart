package tk.halfaheart.core.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import tk.halfaheart.core.HalfAHeart;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class PlayerUtils {

    private static final NamespacedKey DEATHS;
    private static final NamespacedKey MINUTES_ALIVE;
    private static final NamespacedKey HARDCORE;
    private static final PersistentDataType<Integer, Integer> INT;
    private static final Map<UUID, Long> DEATH_TIME;

    static {
        DEATHS = new NamespacedKey(HalfAHeart.getInstance(), "deaths");
        MINUTES_ALIVE = new NamespacedKey(HalfAHeart.getInstance(), "days_alive");
        HARDCORE = new NamespacedKey(HalfAHeart.getInstance(), "hardcore");
        DEATH_TIME = new HashMap<>();
        INT = PersistentDataType.INTEGER;
    }

    public static void addDeath(Player player) {
        if (player.getPersistentDataContainer().has(DEATHS, INT)) {
            int deaths = player.getPersistentDataContainer().get(DEATHS, INT);
            player.getPersistentDataContainer().set(DEATHS, INT, deaths + 1);
        } else {
            player.getPersistentDataContainer().set(DEATHS, INT, 1);
        }
    }

    public static int getDeaths(Player player) {
        if (player.getPersistentDataContainer().has(DEATHS, INT)) {
            return player.getPersistentDataContainer().get(DEATHS, INT);
        } else {
            return 0;
        }
    }

    public static void addMinuteAlive(Player player) {
        if (player.getPersistentDataContainer().has(MINUTES_ALIVE, INT)) {
            int minutes_alive = player.getPersistentDataContainer().get(MINUTES_ALIVE, INT);
            player.getPersistentDataContainer().set(MINUTES_ALIVE, INT, minutes_alive + 1);
        } else {
            player.getPersistentDataContainer().set(MINUTES_ALIVE, INT, 1);
        }
    }

    public static void resetMinutesAlive(Player player) {
        player.getPersistentDataContainer().set(MINUTES_ALIVE, INT, 1);
    }

    public static int getMinutesAlive(Player player) {
        if (player.getPersistentDataContainer().has(MINUTES_ALIVE, INT)) {
            return player.getPersistentDataContainer().get(MINUTES_ALIVE, INT);
        } else {
            return 1;
        }
    }

    public static int getDaysAlive(Player player) {
        return (int) Math.ceil((double) getMinutesAlive(player) / 20);
    }

    public static void setTablist(Player player) {
        String separator = "&3===============";
        player.setPlayerListHeader(Util.getColString("&3Halfa&bHeart\n" + separator));

        String footer = "&6Deaths: &c" + PlayerUtils.getDeaths(player) + " &6Days Alive: &b" + PlayerUtils.getDaysAlive(player);
        player.setPlayerListFooter(Util.getColString(separator + "\n" + footer));
    }

    public static void setHardcore(Player player, boolean hardcore) {
        player.getPersistentDataContainer().set(HARDCORE, INT, hardcore ? 1 : 0);
    }

    public static boolean isHardcore(Player player) {
        if (player.getPersistentDataContainer().has(HARDCORE, INT)) {
            return player.getPersistentDataContainer().get(HARDCORE, INT) == 1;
        }
        player.getPersistentDataContainer().set(HARDCORE, INT, 1);
        return true;
    }

    public static void setDeathTime(Player player) {
        DEATH_TIME.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public static boolean hasDiedRecently(Player player) {
        long now = System.currentTimeMillis();
        UUID uuid = player.getUniqueId();
        return DEATH_TIME.containsKey(uuid) && now - DEATH_TIME.get(uuid) <= 10000;
    }

}
