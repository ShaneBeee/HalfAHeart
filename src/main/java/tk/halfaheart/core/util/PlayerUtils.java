package tk.halfaheart.core.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import tk.halfaheart.core.HalfAHeart;

@SuppressWarnings("ConstantConditions")
public class PlayerUtils {

    private static final NamespacedKey DEATHS;
    private static final NamespacedKey MINUTES_ALIVE;
    private static final PersistentDataType<Integer, Integer> INT;

    static {
        DEATHS = new NamespacedKey(HalfAHeart.getInstance(), "deaths");
        MINUTES_ALIVE = new NamespacedKey(HalfAHeart.getInstance(), "days_alive");
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

}
