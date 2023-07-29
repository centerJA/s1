package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardUtil {
    public static Scoreboard createScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Ranking", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scoreboard;
    }

    public static void updateRanking(Player player) {
        Scoreboard scoreboard = createScoreboard();
        int time = player.getLevel();
        Score score = scoreboard.getObjective("Ranking").getScore(player.getName());
        score.setScore(time);
        player.setScoreboard(scoreboard);

    }
    public static void showScoreboard(Player player) {
        player.sendMessage("test");
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

}
