package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class knockBackTimerUtil extends BukkitRunnable {

    public static int nowknockBackTime;

    private ArrayList<String> knockBackPlayerList;

    public knockBackTimerUtil(ArrayList<String> knockBackPlayerList2) {
        this.nowknockBackTime = 20;
        this.knockBackPlayerList = knockBackPlayerList2;
    }
    @Override
    public void run() {
        if (nowknockBackTime < 0) {
            cancel();
            return;
        }
        for (String PlayerName: knockBackPlayerList) {
            Player gamePlayer = Bukkit.getPlayer(PlayerName);
            if (gamePlayer == null) return;
            gamePlayer.sendMessage(ChatColor.YELLOW + "<knockBack>" + nowknockBackTime + "秒");
        }
        nowknockBackTime--;
    }

    public static void stopBedwarsCountDownTimer() {
        nowknockBackTime = -100;
    }
}
