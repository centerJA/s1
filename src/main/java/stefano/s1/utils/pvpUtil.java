package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class pvpUtil {
    public static ArrayList<Location> blockLocationList = new ArrayList<>();
    public static void blockLocation(Location location) {
        blockLocationList.add(location);
    }

    public static void blockBreak() {
        Bukkit.getLogger().info(String.valueOf(blockLocationList));
        for (Location blockBreak: blockLocationList) {
            blockBreak.getBlock().setType(Material.AIR);
        }
    }

    public static void blockLocationAllRemove() {
        if (blockLocationList.size() != 0) {
            blockLocationList.clear();
        }
    }
}
