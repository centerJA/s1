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

    public static File playerScoreFile;

    public static FileConfiguration playerScoreFileConfig;
    String playerName;
    int time;

    static S1 plugin;


    public PlayerScore(String playerName, int time, S1 plugin) {
        this.playerName = playerName;
        this.time = time;
        this.plugin = plugin;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int time() {
        return time;
    }

    public static void setPlayerTime(FileConfiguration PlayerTime, Player player, int goalTime, File file) throws IOException {
        if (PlayerTime.get(player.getName()) == null) {
            PlayerTime.set(player.getName(), goalTime);
        } else {
            if (goalTime < (int) PlayerTime.get(player.getName())) {
                PlayerTime.set(player.getName(), goalTime);
            }
        }
        PlayerTime.save(file);

    }

    public static void removePlayerTime(FileConfiguration PlayerTime, Player player, File file) throws IOException {
        String playerName = player.getName();
        if (playerName == null) return;
        if (PlayerTime.get(playerName) == null) return;
        PlayerTime.set(playerName, 100000);
        PlayerTime.save(file);

    }

    public static void removePlayerTimeAll(Player player) throws IOException {
        //playerScoreFile = new File(plugin.getDataFolder().getParentFile(),"playerTime.yml");
        playerScoreFile = new File("./playerTime.yml");
        playerScoreFileConfig = YamlConfiguration.loadConfiguration(playerScoreFile);
        for (String playerTime: playerScoreFileConfig.getKeys(false)) {
            playerScoreFileConfig.set(playerTime, 100000);

        }
        playerScoreFileConfig.save(playerScoreFile);
        player.sendMessage("oke");
//        InputStreamReader enemy = new InputStreamReader();
//        playerScoreFile = YamlConfiguration.loadConfiguration(enemy);
        //        for (String PlayerAthleticTime: PlayerTime) {
//            if (PlayerTime.get(player.getName()) != null) {
//                PlayerTime.set(player.getName(), 100000);
//                PlayerTime.save(file);
//            }
//        }
    }
}
