package tk.halfaheart.core.util;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import tk.halfaheart.core.HalfAHeart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class BlockUtils {

    private final static NamespacedKey HOLDERS;
    public final static ImmutableSet<Material> CONTAINERS;

    static {
        CONTAINERS = ImmutableSet.<Material>builder()
                .add(Material.CHEST)
                .add(Material.TRAPPED_CHEST)
                .add(Material.BARREL)
                .add(Material.FURNACE)
                .add(Material.SMOKER)
                .add(Material.BLAST_FURNACE)
                .add(Material.DISPENSER)
                .add(Material.DROPPER)
                .add(Material.HOPPER)
                .build();
        HOLDERS = new NamespacedKey(HalfAHeart.getInstance(), "holders");
    }

    public static boolean isContainer(Material material) {
        if (Tag.SHULKER_BOXES.isTagged(material)) {
            return true;
        }
        return CONTAINERS.contains(material);
    }

    public static boolean hasHolders(BlockState state) {
        return state instanceof PersistentDataHolder &&
                ((PersistentDataHolder) state).getPersistentDataContainer().has(HOLDERS, PersistentDataType.STRING);
    }

    public static boolean hasHolder(UUID uuid, BlockState state) {
        if (hasHolders(state)) {
            return getHolderList(state).contains(uuid.toString());
        }
        return false;
    }

    public static void addHolder(UUID uuid, BlockState state) {
        //if (hasHolders(state)) {
            List<String> holders = new ArrayList<>(getHolderList(state));
            if (!holders.contains(uuid.toString())) {
                holders.add(uuid.toString());
                ((PersistentDataHolder) state).getPersistentDataContainer().set(HOLDERS, PersistentDataType.STRING, listToString(holders));
                state.update(true);
            }
        //}
    }

    public static void removeHolder(Player player, BlockState state) {
        String uuid = player.getUniqueId().toString();
        if (hasHolders(state)) {
            List<String> holders = Arrays.asList(((PersistentDataHolder) state).getPersistentDataContainer().get(HOLDERS,
                    PersistentDataType.STRING).split(";"));
            holders.removeIf(holder -> holder.equalsIgnoreCase(uuid));

            StringBuilder newHolders = new StringBuilder();
            for (String holder : holders) {
                newHolders.append(holder).append(";");
            }
            newHolders.deleteCharAt(newHolders.lastIndexOf(";"));
            String holds = newHolders.toString();
            ((PersistentDataHolder) state).getPersistentDataContainer().set(HOLDERS, PersistentDataType.STRING, holds);
            state.update(true);
        }
    }

    public static void removeHolders(BlockState state) {
        if (hasHolders(state)) {
            ((PersistentDataHolder) state).getPersistentDataContainer().remove(HOLDERS);
            state.update(true);
        }
    }

    public static List<String> getHolderList(BlockState state) {
        if (hasHolders(state)) {
            return Arrays.asList(((PersistentDataHolder) state).getPersistentDataContainer().get(HOLDERS,
                    PersistentDataType.STRING).split(";"));
        }
        return new ArrayList<>();
    }

    private static String listToString(List<String> list) {
        StringBuilder newHolders = new StringBuilder();
        for (String holder : list) {
            newHolders.append(holder).append(";");
        }
        newHolders.deleteCharAt(newHolders.lastIndexOf(";"));
        return newHolders.toString();
    }

}
