package tk.halfaheart.core.command;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.halfaheart.core.HalfAHeart;
import tk.halfaheart.core.task.PlayerDeath;
import tk.halfaheart.core.util.BlockUtils;

import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class Test implements CommandExecutor {

    private final HalfAHeart plugin;

    public Test(HalfAHeart plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            Block block = player.getTargetBlockExact(10);

            if (args.length == 1) {
                switch (args[0]) {
                    case "fake":
                        UUID uuid = UUID.randomUUID();
                        BlockState state = block.getState();
                        BlockUtils.addHolder(uuid, state);
                        player.sendMessage("Added fake player to block as holder!");
                        break;
                    case "death":
                        new PlayerDeath(this.plugin, player);
                }
            }
        }
        return true;
    }

}
