package stefano.s1.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import stefano.s1.S1;

import java.io.IOException;

public class AthleticUtil {
    public static void afterAthleticAction(Player player, S1 plugin, Location athleticClear) throws IOException {
        player.sendMessage(ChatColor.YELLOW + "ゴールにつきました！おめでとうございます！");
        if (athleticClear.getWorld() == null) return;
        athleticClear.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
        player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチッククリア！", 20, 40, 20);
        AthleticTimer.stopTimer(player);
        player.sendMessage("あなたの記録は" + ChatColor.AQUA + player.getLevel() + ChatColor.WHITE + "秒でした！");
        if (player.getLevel() < 100) {
            player.sendMessage("プラグインからのメッセージ : 100秒を切るなんて、、ただ者ではないようです。");
        }
        PlayerScore.setPlayerTime(player, player.getLevel(), plugin);
        ScoreBoardUtil.updateRanking(player);
        player.setLevel(0);
    }

    public static void beforeAthleticAction(Player player, Location athleticStart, AthleticTimer athleticTimer) {
        player.sendMessage("アスレチックスタート！");
        player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチックスタート！", 20, 40, 20);
        if (athleticStart.getWorld() == null) return;
        athleticStart.getWorld().playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 0, 10);
        athleticTimer.startTimer(player);
    }

    public static void athletic1UtilityItem(Player player) {
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
        player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る(athletic1)", Material.APPLE));
        player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
    }
}
