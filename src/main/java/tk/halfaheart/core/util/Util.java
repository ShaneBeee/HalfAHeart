package tk.halfaheart.core.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class Util {

    private final static String PREFIX = "&7[&3Halfa&bHeart&7] ";

    public static String getColString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + message));
    }

    public static void warn(String warning) {
        log("&e" + warning);
    }

    public static void error(String error) {
        log("&c" + error);
    }

    public static void sendColMsg(CommandSender receiver, String message, boolean prefix) {
        receiver.sendMessage(getColString((prefix ? PREFIX : "") + message));
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(getColString(PREFIX + message));
    }

    public static void sendTitle(Player player, String title, String subtitle, int in, int stay, int out) {
        String t = getColString(title);
        String st = getColString(subtitle);
        player.sendTitle(t, st, in, stay, out);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 10, 100, 10);
    }

    public static void deathSound() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
        }
    }

    // TODO temp values
    public final static Location SPAWN = new Location(Bukkit.getWorlds().get(0), 235.7, 64, 227.5, 90, 10);
    public final static Location RESPAWN = new Location(Bukkit.getWorlds().get(0), 228.6, 64, 227.5, -89, 12);
    public final static World WORLD = Bukkit.getWorlds().get(0);

}
