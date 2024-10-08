package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.util.*;

public class ScoreBoardUtil {
    public static Scoreboard createScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return null;
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective2 = scoreboard.registerNewObjective("1st player", "dummy", "ranking");
        objective2.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scoreboard;
    }

    public static void updateRanking(Player player) {
        File logFile = new File("./playerTime.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(logFile);
        Scoreboard scoreboard = createScoreboard();
        Map<String, Integer> TimesMap = new HashMap<>();
        if (scoreboard == null) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("Cannot get scoreboard");
        }
        if (scoreboard == null) return;
        Objective objective = scoreboard.getObjective("1st player");
        if (yamlConfiguration == null) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("Cannot get yaml");
            return;
        }

        for (String playerName : yamlConfiguration.getKeys(false)) {
            int time = yamlConfiguration.getInt(playerName);
            TimesMap.put(playerName, time);
        }
        int count = 0;
        for (Map.Entry<String, Integer> entry : getSortedEntries(TimesMap)) {

            if (count == 1) break;
            count++;
            String PlayerName = entry.getKey();
            int time = entry.getValue();
            Team team = scoreboard.getTeam("Rank" + count);
            if (team == null) {
                team = scoreboard.registerNewTeam("Rank" + count);
            }
            team.addEntry(PlayerName);
            if (objective == null) return;
            objective.getScore(PlayerName).setScore(time);
        }
        player.setScoreboard(scoreboard);



    }
    public static void showScoreboard(Player player) {
        Scoreboard scoreboard = createScoreboard();
        String ObjectiveName = "1st player";
        if (scoreboard == null) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("Cannot get scoreboard");
        }
        if (scoreboard == null) return;
        Objective objective = scoreboard.getObjective(ObjectiveName);
        if (objective == null) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("Cannot get objective");
            return;
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    public static void removeScoreboard(Player player) {
        ScoreboardManager manager = player.getServer().getScoreboardManager();
        if (manager == null) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("Cannot get manager");
            return;
        }
        Scoreboard scoreboard = manager.getMainScoreboard();
        player.setScoreboard(scoreboard);
    }

    public static List<Map.Entry<String, Integer>> getSortedEntries(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());
        return sortedEntries;
    }

}
