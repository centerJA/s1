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

        Objective objective = scoreboard.registerNewObjective("1st player", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scoreboard;
    }

    public static void updateRanking(Player player, YamlConfiguration yamlConfiguration) {
        Scoreboard scoreboard = createScoreboard();
        Map<String, Integer> TimesMap = new HashMap<>();
        Objective objective = scoreboard.getObjective("1st player");

        for (String playerName : yamlConfiguration.getKeys(false)) {
            int time = yamlConfiguration.getInt(playerName);
            TimesMap.put(playerName, time);
        }
        int count = 0;
//        player.sendMessage(getSortedEntries(TimesMap).toString());
        for (Map.Entry<String, Integer> entry : getSortedEntries(TimesMap)) {

            if (count == 1) break;
            count++;
            String PlayerName = entry.getKey();
//            player.sendMessage(String.valueOf(count));
//            player.sendMessage(PlayerName);
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
        String ObjectiveName = "1st player";
        Objective objective = scoreboard.getObjective(ObjectiveName);
        if (objective == null) {
            player.sendMessage(ChatColor.DARK_RED + "オブジェクトが見つかりませんでした(REASON)CANNOT FIND OBJECT(0)");
            return;
        }
//        player.sendMessage("ひょうじします");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
//        player.sendMessage("ひょうじしました");
    }

    public static void removeScoreboard(Player player) {
        ScoreboardManager manager = player.getServer().getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        player.setScoreboard(scoreboard);
    }

    public static List<Map.Entry<String, Integer>> getSortedEntries(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());
//        Collections.reverse(sortedEntries);
        return sortedEntries;
    }

}
