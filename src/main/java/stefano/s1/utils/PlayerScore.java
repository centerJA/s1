package stefano.s1.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
    public static void setPlayerTime(YamlConfiguration PlayerTime, Player player, int time) {
        PlayerTime.set(player.getName(), time);

    }
    public static void changePlayerTime(YamlConfiguration PlayerTime, Player player, int time) {
        if (PlayerTime.get(player.getName()) != null) {
            PlayerTime.set(player.getName(), time);
        }
    }
    public static void removePlayerTime(YamlConfiguration PlayerTime, Player player, int time) {
        if (PlayerTime.get(player.getName()) != null) {
            PlayerTime.set(player.getName(), null);
        }
    }
}
