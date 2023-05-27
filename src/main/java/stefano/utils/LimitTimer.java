package stefano.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import stefano.Config;

public class LimitTimer extends BukkitRunnable {

    private int limitTime;

    private World world;

    public LimitTimer() {
        this.limitTime = 1820;
        this.world = Bukkit.getWorld("stefanovarentino");
    }

    @Override
    public void run() {
        if (limitTime < 0) {
            cancel();
            return;
        }
        if (limitTime == 1770) {
            for (Player player: this.world.getPlayers()){
                player.sendMessage("テスト");
            }
        }
        if (limitTime == 1300) {
            Config.Range = 125;
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave1終了まで残り500秒！");
            }
        }
        if (limitTime == 900) {
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave1終了まで残り100秒！");
                player.sendMessage("0秒になったら範囲をx=-100~100、z=-100~100にします。");
            }
        }
        if (limitTime == 800) {
            Config.Range = 100;
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave1終了！");
                player.sendMessage("wave2開始！");
                player.sendMessage("範囲を、x=-100~100、z=-100~100にしました。");
            }
        }
        if(limitTime == 500) {
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave2終了まで残り100秒！");
                player.sendMessage("0秒になったら範囲をx=-60~60、z=-60~60にします。");
            }
        }
        if (limitTime == 400) {
            Config.Range = 60;
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave2終了！");
                player.sendMessage("wave3開始！");
                player.sendMessage("範囲を、x=-60~60、z=-60~60にしました。");
            }
        }
        if (limitTime == 100) {
            for (Player player: this.world.getPlayers()){
                player.sendMessage("wave3終了まで残り100秒！");
            }
        }
        if (limitTime == 0) {
            Config.Range = 125;
            for (Player player:this.world.getPlayers()){
                player.sendMessage("wave3終了！");

            }
        }
        limitTime--;

    }
}

