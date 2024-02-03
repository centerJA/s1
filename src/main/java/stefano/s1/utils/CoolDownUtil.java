package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import stefano.s1.Config;
import stefano.s1.S1;

import java.util.HashMap;
import java.util.UUID;

public class CoolDownUtil{
    private static HashMap<UUID, Long> coolDowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 5000;

    public static int cooldown(Player player, S1 plugin) {
        if (!hasCoolDown(player)) {
            setCoolDown(player);
            setCoolDownWitch(plugin);
            Config.CoolDownWitch = 1;
        } else {
            player.sendMessage("少しお待ちください!");
        }
        return Config.CoolDownWitch;
    }

    public static boolean hasCoolDown(Player player) {
        return coolDowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - coolDowns.get(player.getUniqueId()) < COOLDOWN_TIME;
    }

    public static void setCoolDown(Player player) {
        coolDowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public static void setCoolDownWitch(S1 plugin) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                Config.CoolDownWitch = 0;
            }
        }, 50L);
    }
}
