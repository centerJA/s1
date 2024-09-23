package stefano.s1.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import stefano.s1.S1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class bedwarsUtil {

    public static ArrayList<String> bedwars1sPlayerList = new ArrayList<>();

    public static boolean canPlayBedwars1sStatus = false;


    public static void firstBedwars1sAction(Player player, S1 plugin, Location taikijyo) {
        player.sendMessage("Hello, World!");

        if (!canPlayBedwars1sStatus) {
            player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
            player.sendMessage("後ほどまた試してみてください");
            return;
        }

        if (!bedwars1sPlayerList.isEmpty()) {
            player.sendMessage("問題が発生しました");
            player.sendMessage("現在アクセスできません");
            player.sendMessage("bedwarsPlayerList must be empty");
        }

        player.teleport(taikijyo);
        bedwars1sPlayerList.add(player.getName());
        if (bedwars1sPlayerList.size() >= 2) {
            new bedwarsTimerUtil(bedwars1sPlayerList).runTaskTimer(plugin, 0L, 20L);
            bedwars1sStartSession(bedwars1sPlayerList);
        }
        else {
            for (String PlayerName: bedwars1sPlayerList) {
                Player player2 = Bukkit.getPlayer(PlayerName);
                if (player2 == null) return;
                player2.sendMessage(ChatColor.AQUA + "人数が足りません。2人以上が必要です。");
                player2.sendMessage(ChatColor.AQUA + "現在1人です。");
            }
        }
    }



    public static void bedwars1sPlayerListSizeChecker() {
        if (bedwars1sPlayerList.size() == 1) {
            for (String PlayerName: bedwars1sPlayerList) {
                Player player2 = Bukkit.getPlayer(PlayerName);
                if (player2 == null) return;
                player2.sendMessage("bedwarsがキャンセルされました");
            }
        } else return;
    }





    public static void bedwars1sStartSession(ArrayList<String> bedwars1sPlayerList) {

        int playerListSize = bedwars1sPlayerList.size();
        List<String[]> list = new ArrayList<>();
        list.add(new String[] {"Red", "Blue", "Yellow", "LightGreen", "Grey", "Purple", "White", "LightBlue"});

        for (int i = playerListSize; i == 0; i -= 1) {
            int listSize = bedwars1sPlayerList.size();
            int listMember = 0;
            int randomInt = (int) (Math.random() * listSize);
            String[] gotElement = list.get(randomInt);
            String element = gotElement[randomInt];

//            String PlayerName = Bukkit.getPlayer(bedwars1sPlayerList.get(listMember))

            listSize = listSize - 1;

            i = i - 1;
        }
    }

}
