package stefano.s1.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import stefano.s1.Config;

import java.util.ArrayList;
import java.util.Random;

public class LimitTimer extends BukkitRunnable {

    private int limitTime;

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
                Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.AQUA + "チェストがスポーンしました！");
                Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.AQUA + "通常よりも強い装備をもらうことができます！");
            }
        }
        if (limitTime == randomTime1) {
            Config.itemList1.add(Material.DIAMOND_AXE);
            this.randomX = random.nextInt(10) + 30;
            this.randomZ = random.nextInt(10) + 55;
            this.randomLocation = new Location(world, randomX, 70, randomZ);
            ChestUtil.setChest(randomLocation, Config.itemList1);
            for(String PlayerName: playerList) {
                Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.AQUA + "チェストがスポーンしました！");
                Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.AQUA + "通常よりも強い装備をもらうことができます！");
            }
        }
        if (limitTime == 1330) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave1終了まで残り500秒！");
            }
        }
        if (limitTime == 930) {
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave1終了まで残り100秒！");
                Bukkit.getPlayer(PlayerName).sendMessage("0秒になったら範囲をx=-100~100、z=-100~100にします。");
            }
        }
        if (limitTime == 830) {
            Config.Range = 100;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave1終了！");
                Bukkit.getPlayer(PlayerName).sendMessage("wave2開始！");
                Bukkit.getPlayer(PlayerName).sendMessage("範囲を、x=-100~100、z=-100~100にしました。");
            }
        }
        if(limitTime == 530) {
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave2終了まで残り100秒！");
                Bukkit.getPlayer(PlayerName).sendMessage("0秒になったら範囲をx=-60~60、z=-60~60にします。");
            }
        }
        if (limitTime == 430) {
            Config.Range = 60;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave2終了！");
                Bukkit.getPlayer(PlayerName).sendMessage("wave3開始！");
                Bukkit.getPlayer(PlayerName).sendMessage("範囲を、x=-60~60、z=-60~60にしました。");
            }
        }
        if (limitTime == 130) {
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave3終了まで残り100秒！");
            }
        }
        if (limitTime == 33) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("3");
            }
        }
        if (limitTime == 32) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("2");

            }
        }
        if (limitTime == 31) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("1");

            }
        }

        if (limitTime == 30) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("wave3終了！");
                Bukkit.getPlayer(PlayerName).teleport(pvpFinal);
            }
        }

        if (limitTime == 15) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("引き分けまで残り15秒!");
            }
        }

        if (limitTime == 5) {
            Config.Range = 125;
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("引き分けまで残り5秒!");
            }
        }
        if (limitTime == 0) {
            for (String PlayerName: playerList){
                Bukkit.getPlayer(PlayerName).sendMessage("引き分け!");
                Bukkit.getPlayer(PlayerName).sendTitle("引き分け", "", 20, 400, 20);
                Bukkit.getPlayer(PlayerName).setHealth(20);
                Bukkit.getPlayer(PlayerName).setFoodLevel(20);
                for (String playerName: playerList) {
                    playerList.remove(playerName);
                }
                Bukkit.getPlayer(PlayerName).teleport(Config.lobby);
            }
            pvpUtil.blockBreak();
        }
        limitTime--;

    }
}

