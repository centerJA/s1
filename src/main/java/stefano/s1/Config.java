package stefano.s1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
                          ,textLocationKnockBackColumn = new Location(Bukkit.getWorld("stefanovarentino"), 23.500, 224, 39.500);



    public static ArrayList<String> playerList = new ArrayList<>()
                 ,checkpointList = new ArrayList<>();

    public static ArrayList<Material> itemList1 = new ArrayList<>();


    public static String mainText = "金床をクリックしてゲームをしよう",
                         pvpIsStopping = "現在pvpで待機している人はいません",
                         pvpIsWaiting = "現在誰かがpvpで待機しています",
                         pvpIsPlaying = "現在誰かがpvpをプレイしています",
                         pvpMainText = "PVP GAME",
                         knockBackIsStopping = "現在knockbackで待機している人はいません",
                         knockBackIsWaiting = "現在誰かがknockbackで待機しています",
                         knockBackIsPlaying = "現在誰かがpvpをプレイしています",
                         knockBackMainText = "KNOCKBACK GAME";
}


