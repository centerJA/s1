package stefano.s1.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class checkOnGroundTask {
    private static final Map<Player, Boolean> playerOnGroundStatus = new HashMap<>();

    private static final Map<Player, Long> playerOffGroundTime = new HashMap<>();

    public static Boolean checkOnGround(Player player, Boolean isOnGround) {
        if (!isOnGround) {
            if (!playerOnGroundStatus.getOrDefault(player, true)) {
                long currentTime = System.currentTimeMillis();
                long offGroundTime = playerOffGroundTime.getOrDefault(player, currentTime);
                if (currentTime - offGroundTime > 3000) {
                    return false;
                } else {
                    playerOffGroundTime.put(player, System.currentTimeMillis());
                    return true;
                }
            } else {
                playerOnGroundStatus.put(player, true);
                playerOffGroundTime.put(player, System.currentTimeMillis());
                return true;

            }
        } else {
            playerOnGroundStatus.put(player, true);
            playerOffGroundTime.put(player, System.currentTimeMillis());
            return true;
        }
    }
}
