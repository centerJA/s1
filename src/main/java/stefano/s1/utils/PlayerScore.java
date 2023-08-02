package stefano.s1.utils;

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
    public static void removePlayerTime(YamlConfiguration PlayerTime, Player player) {
        if (PlayerTime.get(player.getName()) != null) {
            PlayerTime.set(player.getName(), null);
        }
    }
}
