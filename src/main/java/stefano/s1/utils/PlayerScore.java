package stefano.s1.utils;

import com.sun.org.apache.bcel.internal.generic.IOR;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerScore {
    String playerName;
    int time;


    public PlayerScore(String playerName, int time) {
        this.playerName = playerName;
        this.time = time;
    }
    public String getPlayerName() {
        return playerName;
    }

    public int time() {
        return time;
    }
    public static void setPlayerTime(YamlConfiguration PlayerTime, Player player, int goalTime, File file) throws IOException {
        if (PlayerTime.get(player.getName()) == null) {
            PlayerTime.set(player.getName(), goalTime);
        } else {
            if (goalTime < (int)PlayerTime.get(player.getName())) {
                PlayerTime.set(player.getName(), goalTime);
            }
        }
        PlayerTime.save(file);

    }
    public static void removePlayerTime(YamlConfiguration PlayerTime, Player player, File file) throws IOException {
        if (PlayerTime.get(player.getName()) != null) {
            PlayerTime.set(player.getName(), 100000);
            PlayerTime.save(file);
        }
    }
    public static void removePlayerTimeAll(YamlConfiguration PlayerTime, Player player, File file) throws IOException {
        for (String PlayerAthleticTime: PlayerTime) {
//            if (PlayerTime.get(player.getName()) != null) {
//                PlayerTime.set(player.getName(), 100000);
//                PlayerTime.save(file);
//            }
//        }

    }
}
