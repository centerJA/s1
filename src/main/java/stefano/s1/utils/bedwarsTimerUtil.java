package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class bedwarsTimerUtil extends BukkitRunnable {

    public static int nowBedwarsTime;

    public static ArrayList<String> bedwarsPlayerList2;

    public bedwarsTimerUtil(ArrayList<String> bedwarsPlayerList) {
        nowBedwarsTime = 20;
        bedwarsPlayerList2 = bedwarsPlayerList;
    }
    @Override
    public void run() {
        if (nowBedwarsTime < 0) {
            cancel();
            return;
        }
        for (String PlayerName: bedwarsPlayerList2) {
            Player gamePlayer = Bukkit.getPlayer(PlayerName);
            if (gamePlayer == null) return;
            gamePlayer.sendMessage(ChatColor.YELLOW + "<bedwars>" + nowBedwarsTime + "秒");
        }
        nowBedwarsTime--;
    }

    public static void stopBedwarsCountDownTimer() {
        nowBedwarsTime = -100;
    }
}
