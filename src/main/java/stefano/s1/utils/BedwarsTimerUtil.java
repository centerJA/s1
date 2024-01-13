package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BedwarsTimerUtil extends BukkitRunnable {

    public static int nowBedwarsTime;

    private ArrayList<String> bedwarsPlayerList;

    public BedwarsTimerUtil(ArrayList<String> bedwarsPlayerList) {
        this.nowBedwarsTime = 20;
        this.bedwarsPlayerList = bedwarsPlayerList;
    }
    @Override
    public void run() {
        if (nowBedwarsTime < 0) {
            cancel();
            return;
        }
        for (String PlayerName: bedwarsPlayerList) {
            Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.YELLOW + "<bedwars>" + nowBedwarsTime + "秒");
        }
        nowBedwarsTime--;
    }

    public static void stopBedwarsCountDownTimer() {
        nowBedwarsTime = -100;
    }
}
