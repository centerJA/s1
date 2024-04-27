package stefano.s1.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import stefano.s1.Config;

import java.util.ArrayList;
import java.util.Random;

public class LimitTimer extends BukkitRunnable {

    public static int limitTime;

    private World world;

    ArrayList<String> playerList;

    Location pvpFinal, randomLocation;

    Random random;

    int randomTime, randomTime1;

    double randomX, randomZ;

    public LimitTimer(ArrayList<String> playerList, Location pvpFinal) {
        this.limitTime = 1850;
        this.world = Bukkit.getWorld("stefanovarentino");
        this.playerList = playerList;
        this.pvpFinal = pvpFinal;
        this.random = new Random();
        this.randomTime = random.nextInt(1000) + 100;
        this.randomTime1 = random.nextInt(1000) + 100;
    }

    @Override
    public void run() {
        if (limitTime < 0) {
            cancel();
            return;
        }
        if (limitTime == randomTime) {
            Config.itemList1.add(Material.GOLDEN_APPLE);
            Config.itemList1.add(Material.DIAMOND_SWORD);
            this.randomX = random.nextInt(10) + 30;
            this.randomZ = random.nextInt(10) + 55;
            this.randomLocation = new Location(world, randomX, 70, randomZ);
            ChestUtil.setChest(randomLocation, Config.itemList1);
            for(String PlayerName: playerList) {
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage(ChatColor.AQUA + "チェストがスポーンしました！");
                sendPlayer.sendMessage(ChatColor.AQUA + "通常よりも強い装備をもらうことができます！");
            }
        }
        if (limitTime == randomTime1) {
            Config.itemList1.add(Material.DIAMOND_AXE);
            this.randomX = random.nextInt(10) + 30;
            this.randomZ = random.nextInt(10) + 55;
            this.randomLocation = new Location(world, randomX, 70, randomZ);
            ChestUtil.setChest(randomLocation, Config.itemList1);
            for(String PlayerName: playerList) {
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage(ChatColor.AQUA + "チェストがスポーンしました！");
                sendPlayer.sendMessage(ChatColor.AQUA + "通常よりも強い装備をもらうことができます！");
            }
        }
        if (limitTime == 1330) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave1終了まで残り500秒！");
            }
        }
        if (limitTime == 930) {
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave1終了まで残り100秒！");
                sendPlayer.sendMessage("0秒になったら範囲をx=-100~100、z=-100~100にします。");
            }
        }
        if (limitTime == 830) {
            Config.Range = 100;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave1終了！");
                sendPlayer.sendMessage("wave2開始！");
                sendPlayer.sendMessage("範囲を、x=-100~100、z=-100~100にしました。");
            }
        }
        if(limitTime == 530) {
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave2終了まで残り100秒！");
                sendPlayer.sendMessage("0秒になったら範囲をx=-60~60、z=-60~60にします。");
            }
        }
        if (limitTime == 430) {
            Config.Range = 60;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave2終了！");
                sendPlayer.sendMessage("wave3開始！");
                sendPlayer.sendMessage("範囲を、x=-60~60、z=-60~60にしました。");
            }
        }
        if (limitTime == 130) {
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave3終了まで残り100秒！");
            }
        }
        if (limitTime == 33) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("3");
            }
        }
        if (limitTime == 32) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("2");

            }
        }
        if (limitTime == 31) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("1");

            }
        }

        if (limitTime == 30) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("wave3終了！");
                sendPlayer.teleport(pvpFinal);
            }
        }

        if (limitTime == 15) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("引き分けまで残り15秒!");
            }
        }

        if (limitTime == 5) {
            Config.Range = 125;
            for (String PlayerName: playerList) {
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("引き分けまで残り5秒!");
            }
        }
        if (limitTime == 0) {
            for (String PlayerName: playerList){
                Player sendPlayer = Bukkit.getPlayer(PlayerName);
                if (sendPlayer == null) return;
                sendPlayer.sendMessage("引き分け!");
                sendPlayer.sendTitle("引き分け", "", 20, 400, 20);
                sendPlayer.setHealth(20);
                sendPlayer.setFoodLevel(20);
                for (String playerName: playerList) {
                    playerList.remove(playerName);
                }
                sendPlayer.teleport(Config.lobby);
            }
            pvpUtil.blockBreak();
        }
        limitTime--;

    }

    public static void stopTimer() {
        limitTime = -100;
    }
}

