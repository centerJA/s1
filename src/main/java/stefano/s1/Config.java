package stefano.s1;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class Config{
    public static int Range = 125, knockBackRange = 180;

    public static int CoolDownWitch = 0;
    public static Location lobby = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 223, 25.500, -90, 0)
                          ,athletic1 = new Location(Bukkit.getWorld("stefanovarentino"), 50.500, 239, 25.500, -90, 0)
                          ,svinfo = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 216, 25.500, 90, 0)
                          ,pvpStart = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 70, 24, -90, 0)
                          ,textLocationAnvil = new Location(Bukkit.getWorld("stefanovarentino"), 42.500, 223.500, 25.500)
                          ,textLocationPvpMainText = new Location(Bukkit.getWorld("stefanovarentino"), 23.500, 225, 11.500)
                          ,textLocationPvpColumn = new Location(Bukkit.getWorld("stefanovarentino"), 23.500, 224, 11.500)
                          ,textLocationKnockBackMainText = new Location(Bukkit.getWorld("stefanovarentino"), 23.500, 225, 39.500)
                          ,textLocationKnockBackColumn = new Location(Bukkit.getWorld("stefanovarentino"), 23.500, 224, 39.500)
                          ,darkRoomLocationUnder = new Location(Bukkit.getWorld("stefanovarentino"), -6, 228, 25)
                          ,darkRoomLocationUp = new Location(Bukkit.getWorld("stefanovarentino"), -6, 229, 25)
                          ,darkRoomPlayerTeleport = new Location(Bukkit.getWorld("stefanovarentino"), -5.500, 228, 25.500)
                          ,darkRoomPlayerTeleport1 = new Location(Bukkit.getWorld("stefanovarentino"), -6.500, 228, 25.500);



    public static ArrayList<String> playerList = new ArrayList<>()
                 ,checkpointList = new ArrayList<>();

    public static ArrayList<Material> itemList1 = new ArrayList<>();


    public static String mainText = ChatColor.AQUA + "金床をクリックしてゲームをしよう",
                         pvpIsStopping = "現在pvpで待機している人はいません",
                         pvpIsWaiting = "現在誰かがpvpで待機しています",
                         pvpIsPlaying = "現在誰かがpvpをプレイしています",
                         pvpMainText = "PVP GAME",
                         knockBackIsStopping = "現在knockbackで待機している人はいません",
                         knockBackIsWaiting = "現在誰かがknockbackで待機しています",
                         knockBackIsPlaying = "現在誰かがknockbackをプレイしています",
                         knockBackMainText = "KNOCKBACK GAME";

    public static void showLatestTips(Player player) {
        String date = "2024.07.22";
        String main = "freePVPスペースが解放されました!";
        String description = "ロビーの奥の方に参加できるところがあるので、いってみてください!";
        player.sendMessage(ChatColor.AQUA + "最近のお知らせ");
        player.sendMessage(date + " : " + main);
        player.sendMessage(description);

    }

    public static void showAllTips(Player player) {
        player.sendMessage("2024.07.22 - freePVPスペースの公開");
        player.sendMessage("2024.07.01 - knockBackゲームの敗北のタイミングの設定の変更");
        player.sendMessage("2024.06.15 - コマンド一覧表示の大幅変更");
        player.sendMessage("2024.05.25 - お知らせの表示機能の追加");
    }
}


