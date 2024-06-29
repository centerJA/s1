package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.Config;
import stefano.s1.S1;

import java.util.ArrayList;
import java.util.HashMap;

public class AthleticTimer  {
    ArrayList<String> athleticPlayerList;
    Player player;

    private BukkitRunnable timerTask;

    private boolean isStopped;

    public static int athleticTime;

    private World world;

    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();

    public static HashMap<Player, Integer> playerTimes = new HashMap<>();


    public void startTimer(Player player) {
        if (playerTimes.containsKey(player)) {
            AthleticTimer.stopTimer(player);
        }
        playerTimes.put(player, 0);
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                int elapsedSeconds = playerTimes.get(player) + 1;
                if (elapsedSeconds > 2000) {
                    AthleticTimer.getTaskId(player).cancel();
                    return;
                }
                playerTimes.put(player, elapsedSeconds);
                player.setLevel(elapsedSeconds);
            }
        };
        tasks.put(player, timerTask.runTaskTimer(S1.getPlugin(S1.class), 0, 20));
        athleticTime = 0;
    }
    public static void stopTimer(Player player) {
        if (tasks.get(player) != null) {
            tasks.get(player).cancel();
        }
    }

    public static BukkitTask getTaskId(Player player) {
        return S1.getPlugin(S1.class).getServer().getScheduler().runTaskTimer(S1.getPlugin(S1.class), new Runnable() {
            @Override
            public void run() {
                if (athleticTime > 2000) {
                    player.sendMessage(ChatColor.AQUA + "時間制限!終了!");
                    return;
                }
                player.setLevel(athleticTime);
                athleticTime++;
            }
        }, 0, 10);
    }

    public static void settingsAthleticSimple(Player player) {
        player.teleport(Config.athletic1);
        ScoreBoardUtil.updateRanking(player);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
        player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る(athletic1)", Material.APPLE));
        player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
        player.sendMessage(ChatColor.AQUA + "チェックポイントの設定は、押した時にいた場所がチェックポイントに設定されます。");
        player.sendMessage(ChatColor.AQUA + "チェックポイントの設定方法は、何も持っていない状態でチェックポイントと書いてある看板をクリックします。");
        player.sendMessage(ChatColor.YELLOW + "速さ重視なら、もちろんチェックポイントを設定しなくてもok！");
        player.sendMessage(ChatColor.YELLOW + "石の感圧板を踏んだらスタートするよ！");

    }

}
