package stefano.s1.utils;

import com.sun.org.apache.bcel.internal.generic.IOR;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import stefano.s1.S1;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PlayerScore {

    public static File playerScoreFile, logFile;

    public static YamlConfiguration log;

    public static FileConfiguration playerScoreFileConfig, playerDataFile;
    String playerName;
    int time;

   S1 plugin;


    public PlayerScore(String playerName, int time, S1 plugin) {
        this.playerName = playerName;
        this.plugin = plugin;
        playerDataFile = plugin.getConfig();
        logFile = new File("./playerTime.yml");
        log = YamlConfiguration.loadConfiguration(logFile);

//        playerScoreFile = new File("~/playerTime.yml");
//        playerScoreFileConfig = YamlConfiguration.loadConfiguration(playerScoreFile);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int time() {
        return time;
    }

    public static void setPlayerTime(Player player, int goalTime, S1 plugin) throws IOException {
        FileConfiguration playerDataFile = plugin.getConfig();
        File logFile = new File("./playerTime.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(logFile);
        if (yamlConfiguration == null) {
            player.sendMessage("logはnullです");
            Bukkit.getLogger().info("logはnullです---------");
        }
        Bukkit.getLogger().info(player.getName());
        if (yamlConfiguration.get(player.getName()) == null) {
            yamlConfiguration.set(player.getName(), goalTime);
            yamlConfiguration.save(logFile);
        } else if(goalTime < (int) yamlConfiguration.get(player.getName())) {
            yamlConfiguration.set(player.getName(), goalTime);
            yamlConfiguration.save(logFile);
        }

    }

    public static void removePlayerTime(Player player, S1 plugin) throws IOException {
        File file = new File("./playerTime.yml");
        YamlConfiguration log = YamlConfiguration.loadConfiguration(file);
        String playerName = player.getName();
        if (playerName == null) {
            player.sendMessage("nulldesu");
        }
        if (playerName == null) return;

        if (log.get(playerName) == null) return;
        log.set(playerName, 100000);
        log.save(file);
    }

    public static void removePlayerTimeAll(Player player, S1 plugin) throws IOException {
        if (player.getName().equals("InfInc") || player.getName().equals("markcs11")) {
            player.sendMessage(ChatColor.RED + "権限が必要です");
            File file = new File("./playerTime.yml");
            YamlConfiguration log = YamlConfiguration.loadConfiguration(file);
            String playerName = player.getName();
            for (String playerTimeSS: log.getKeys(false)) {
                if (log.get(playerName) == null) return;
                log.set(playerTimeSS, 100000);
            }
            log.save(file);
            player.sendMessage("完了しました");
        } else {
            player.sendMessage(ChatColor.RED + "権限がありません");
        }
    }
}