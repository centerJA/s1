package stefano.s1.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class bedwarsUtil {

    public static ArrayList<String> bedwarsPlayerList = new ArrayList<>();

    public static boolean canPlayBedwarsStatus = false;


    public static void firstBedwarsAction(Player player) {
        player.sendMessage("Hello, World!");

        if (!canPlayBedwarsStatus) {
            player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
            player.sendMessage("後ほどまた試してみてください");
            return;
        }

        if (!bedwarsPlayerList.isEmpty()) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("bedwarsPlayerList must be empty");
        }

    }



}
