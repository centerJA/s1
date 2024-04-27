package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Timer extends BukkitRunnable {
    public static int nowTime;
    private ArrayList<String> playerList;
    private World world;
    public Timer(ArrayList<String> playerList) {
        this.nowTime = 20;
        this.world = Bukkit.getWorld("stefanovarentino");
        this.playerList = playerList;
    }
    @Override
    public void run() {
        if (nowTime < 0) {
            cancel();
            return;
        }
        for (String PlayerName: playerList) {
            Player pvpPlayer = Bukkit.getPlayer(PlayerName);
            if (pvpPlayer == null) return;
            pvpPlayer.sendMessage(ChatColor.YELLOW + "<pvp>" + nowTime + "秒");
        }
        nowTime--;
    }

    public static void stopCountDownTimer() {
        nowTime = -100;
    }
}
