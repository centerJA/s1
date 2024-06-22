package stefano.s1.utils;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import stefano.s1.S1;

import java.io.IOException;

public class AthleticUtil {
    public static void afterAthleticAction(Player player, S1 plugin, Location athleticClear) throws IOException {
        player.sendMessage(ChatColor.YELLOW + "ゴールにつきました！おめでとうございます！");
        athleticClear.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
        player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチッククリア！", 20, 40, 20);
        AthleticTimer.stopTimer(player);
        player.sendMessage("あなたの記録は" + player.getLevel() + "でした！");
        PlayerScore.setPlayerTime(player, player.getLevel(), plugin);
        ScoreBoardUtil.updateRanking(player);
        player.setLevel(0);
    }

    public static void beforeAthleticAction(Player player, Location athleticStart, AthleticTimer athleticTimer) {
        player.sendMessage("アスレチックスタート！");
        player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチックスタート！", 20, 40, 20);
        athleticStart.getWorld().playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 0, 10);
        athleticTimer.startTimer(player);
    }
}
