package stefano.s1.utils;

import com.sun.org.apache.bcel.internal.generic.IOR;
import org.bukkit.Bukkit;
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
        this.time = time;
        this.plugin = plugin;
        playerDataFile = plugin.getConfig();
        logFile = new File("~/playerTime.yml");
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

    public static void setPlayerTime(Player player, int goalTime, File file) throws IOException {
        if (log == null) {
            player.sendMessage("logはnullです");
            Bukkit.getLogger().info("logはnullです---------");
        }
        if (log.get(player.getName()) == null) {
            Bukkit.getLogger().info("message-message");
            log.set(player.getName(), goalTime);
            Bukkit.getLogger().info("message-message");
        } else if(goalTime < (int) log.get(player.getName())) {
            log.set(player.getName(), goalTime);
            log.save(logFile);
            Bukkit.getLogger().info("message-message!?!?!?");
        }

//        if (PlayerTime.get(player.getName()) == null) {
//            PlayerTime.set(player.getName(), goalTime);
//        } else {
//            if (goalTime < (int) PlayerTime.get(player.getName())) {
//                PlayerTime.set(player.getName(), goalTime);
//            }
//        }
//        PlayerTime.save(file);
//
//    }
    }

    public static void removePlayerTime(FileConfiguration PlayerTime, Player player, File file) throws IOException {
        String playerName = player.getName();
        Bukkit.getLogger().info("-----------------------------notification----------------------------------");
        Bukkit.getLogger().info((String) PlayerTime.get("markcs11"));
        Bukkit.getLogger().info(file.toString());
        Bukkit.getLogger().info("---------------------------------------------------------------------------");
        if (playerName == null) return;
        if (PlayerTime.get(playerName) == null) return;
        PlayerTime.set(playerName, 100000);
        player.sendMessage("IF(!PLAYERTIME)の前です");
        player.sendMessage("SAVE-FILE----------SAVE-FILE");
        PlayerTime.save(file);
        player.sendMessage("SAVE-FILE----------SAVE-FILE");
    }

    public static void removePlayerTimeAll(Player player) throws IOException {
        for (String playerTime: playerScoreFileConfig.getKeys(false)) {
            playerScoreFileConfig.set(playerTime, 100000);

        }
        playerScoreFileConfig.save(playerScoreFile);
        player.sendMessage("oke");
    }
}
