package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class ScoreBoardUtil {
    public static Scoreboard createScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Ranking", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scoreboard;
    }

    public static void updateRanking(Player player, YamlConfiguration yamlConfiguration) {
        Scoreboard scoreboard = createScoreboard();
        Map<String, Integer> TimesMap = new HashMap<>();
        Objective objective = scoreboard.getObjective("Ranking");

        for (String playerName : yamlConfiguration.getKeys(false)) {
            int time = yamlConfiguration.getInt(playerName);
            TimesMap.put(playerName, time);
        }
        int count = 0;
//        List<Map.Entry<String, Integer>>
        for (Map.Entry<String, Integer> entry : getSortedEntries(TimesMap)) {
            count++;
            if (count > 5) break;
            String PlayerName = entry.getKey();
            int time = entry.getValue();
            Team team = scoreboard.getTeam("Rank" + count);
            if (team == null) {
                team = scoreboard.registerNewTeam("Rank" + count);
            }
            team.addEntry(PlayerName);
            objective.getScore(PlayerName).setScore(time);
        }
        player.setScoreboard(scoreboard);



    }
    public static void showScoreboard(Player player) {
        Scoreboard scoreboard = createScoreboard();
        String ObjectiveName = "Ranking";
        Objective objective = scoreboard.getObjective(ObjectiveName);
        if (objective == null) {
            player.sendMessage("オブジェクトが見つかりませんでした");
            return;
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }

    public static void removeScoreboard(Player player) {
        ScoreboardManager manager = player.getServer().getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        player.setScoreboard(scoreboard);
    }

    public static List<Map.Entry<String, Integer>> getSortedEntries(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());
        Collections.reverse(sortedEntries);
        return sortedEntries;
    }

}
