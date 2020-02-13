package tk.halfaheart.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.halfaheart.core.util.PlayerUtils;
import tk.halfaheart.core.util.Util;

public class HardcoreToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);

            if (PlayerUtils.isHardcore(player)) {
                PlayerUtils.setHardcore(player, false);
                Util.sendColMsg(player, "&6Hardcore &cdisabled!", true);
            } else {
                PlayerUtils.setHardcore(player, true);
                Util.sendColMsg(player, "&6Hardcore &aenabled", true);
            }

        } else {
            Util.error("This is a player only command!");
        }
        return true;
    }
}
