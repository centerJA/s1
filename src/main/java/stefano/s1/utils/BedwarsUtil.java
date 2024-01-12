package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import stefano.s1.S1;

public class BedwarsUtil {
    public static void startBedwars(S1 plugin, Player player) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.sendMessage("testtest");
            }
        }, 410L);
    }
}