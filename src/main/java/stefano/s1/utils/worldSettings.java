package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import stefano.s1.Config;
import stefano.s1.S1;

import java.io.IOException;
import java.util.Objects;

public class worldSettings {

    public static void firstSettings(Player player, Location lobby) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.sendTitle(player.getName() + ChatColor.AQUA + "さん", ChatColor.AQUA + "こんにちは！", 20, 40, 20);
        player.sendMessage(ChatColor.AQUA + "-----Stefano Varentinoへようこそ!-----");
        player.sendMessage(ChatColor.YELLOW + "アスレチックやPVPなど、いくつかのゲームがあります!");
        player.sendMessage(ChatColor.YELLOW + "前にある金床をクリックしてゲームをプレイすることができます。");
        player.sendMessage(ChatColor.GREEN + "レッドストーンブロック…アスレチックがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "エメラルド…PVPがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "赤いきのこ…ロビーの中心に戻ります。");
        player.sendMessage(ChatColor.GREEN + "/sv…コマンド一覧を表示します。");
        player.sendMessage(ChatColor.AQUA + "------------------------------");
        player.teleport(lobby);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
    }
    public static void signClick(Player player, String[] lines, FileConfiguration checkpointList, Location athleticClear, S1 plugin) throws IOException {
        if (Objects.equals(lines[0], "tyekkupoinnto") || Objects.equals(lines[0], "チェックポイント")) {
            player.sendMessage(ChatColor.DARK_GREEN + "無事にチェックポイントを設定しました!");
            checkpointList.set(String.valueOf(player.getUniqueId()), player.getLocation());
            player.getInventory().setHeldItemSlot(3);
        }
        else if(Objects.equals(lines[0], "今日のお知らせ")) {
            Config.showLatestTips(player);
        }
        else if (Objects.equals(lines[0], "アスレの王者")) {
            player.getWorld().playEffect(player.getLocation(), Effect.WITHER_SHOOT, 0, 1);
        }
        else if (Objects.equals(lines[0], "stefanovarentino")) {
            if (athleticClear.getWorld() == null) return;
            athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 1);
        }
        else if (Objects.equals(lines[0], "この世界の名前は")) {
            player.sendMessage("答えはなんと...." + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "!");
        }
        else if (Objects.equals(lines[0], "この看板をクリックし") || Objects.equals(lines[0], "opathletic")) {
            if (Objects.equals(lines[1], "てタイムをリセット") || Objects.equals(lines[1], "remove")) {
                player.sendMessage(ChatColor.AQUA + "自分のタイムをリセットします。");
                PlayerScore.removePlayerTime(player, plugin);
                ScoreBoardUtil.updateRanking(player);
            }
        }
    }
}
