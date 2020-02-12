package tk.halfaheart.core.command;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.halfaheart.core.util.Util;

public class Sign implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            Block block = player.getTargetBlockExact(10);
            if (block != null && Tag.SIGNS.isTagged(block.getType())) {
                org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) block.getState());
                sign.setLine(0, Util.getColString("&7[&bRTP&7]"));
                sign.setLine(1, "Click to randomly");
                sign.setLine(2, "teleport in the");
                sign.setLine(3, "world");
                sign.update(true);
            }
        } else {
            Util.sendColMsg(sender, "&cThis command is for players only!", true);
        }
        return true;
    }

}
