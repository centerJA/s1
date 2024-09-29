package stefano.s1.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import stefano.s1.S1;

import java.util.*;

public class bedwarsUtil {

    public static ArrayList<String> bedwars1sPlayerListInaa = new ArrayList<>();

    public static ArrayList<String> bedwars1sPlayerListInbb = new ArrayList<>();

    public static ArrayList<String> bedwars4vPlayerListInaa = new ArrayList<>();

    public static ArrayList<String> bedwars4vPlayerListInbb = new ArrayList<>();

    public static boolean canPlayBedwars1sStatusInaa = true;

    public static boolean canPlayBedwars1sStatusInbb = true;

    public static boolean canPlayBedwars4vStatusInaa = true;

    public static boolean canPlayBedwars4vStatusInbb = true;


    public static Location bedwars1sREDInA, bedwars1sREDInB;

    public static Location bedwars1sBLUEInA, bedwars1sBLUEInB;

    public static Location bedwars1sYellowInA, bedwars1sYellowInB;

    public static Location bedwars1sGreenInA, bedwars1sGreenInB;

    public static Location bedwars1sBlackInA, bedwars1sBlackInB;

    public static Location bedwars1sPurpleInA, bedwars1sPurpleInB;

    public static Location bedwars1sWhiteInA, bedwars1sWhiteInB;

    public static Location bedwars1sLimeInA, bedwars1sLimeInB;


    public static ItemStack helmet = null;

    public static ItemStack chestplate = null;

    public static ItemStack leggings = null;

    public static ItemStack boots = null;

    public static ItemStack colorBlockBeforeGame = null;

    public static void firstBedwarsAction(Player player, S1 plugin, Location taikijyo, Material material, ArrayList<String> cannotDamageList) {
        player.sendMessage("Hello, World!");

        if (material.equals(Material.BLUE_BED)) {
            if (!canPlayBedwars1sStatusInaa) {
                player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
                player.sendMessage("後ほどまた試してみてください");
                return;
            }
            programOfBedwars1sInaa(plugin, player, taikijyo, cannotDamageList);
        }

        else if (material.equals(Material.YELLOW_BED)) {
            if (!canPlayBedwars1sStatusInbb) {
                player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
                player.sendMessage("後ほどまた試してみてください");
                return;
            }
            player.teleport(taikijyo);
            bedwars1sPlayerListInbb.add(player.getName());
        }

        else if (material.equals(Material.GREEN_BED)) {
            if (!canPlayBedwars4vStatusInaa) {
                player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
                player.sendMessage("後ほどまた試してみてください");
                return;
            }
            player.teleport(taikijyo);
            bedwars4vPlayerListInaa.add(player.getName());
        }

        else if (material.equals(Material.PURPLE_BED)) {
            if (!canPlayBedwars4vStatusInbb) {
                player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
                player.sendMessage("後ほどまた試してみてください");
                return;
            }
            player.teleport(taikijyo);
            bedwars4vPlayerListInbb.add(player.getName());
        }


    }


    public static void programOfBedwars1sInaa(S1 plugin, Player player, Location taikijyo, ArrayList<String> cannotDamageList) {
        if (bedwars1sPlayerListInaa.size() == 8) {
            player.sendMessage("現在誰かがbedwarsをプレイ中です!!");
            player.sendMessage("後ほどまた試してみてください");
            return;
        }
        player.teleport(taikijyo);
        player.getInventory().clear();
        player.getInventory().setItem(0, ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
        bedwars1sPlayerListInaa.add(player.getName());
        player.sendMessage(ChatColor.YELLOW + String.valueOf(bedwars1sPlayerListInaa));
        if (bedwars1sPlayerListInaa.size() >= 2) {
            new bedwarsTimerUtil(bedwars1sPlayerListInaa).runTaskTimer(plugin, 0L, 20L);
            bedwars1sStartSession(bedwars1sPlayerListInaa, plugin, cannotDamageList);
            if (bedwars1sPlayerListInaa.size() == 8) {
                canPlayBedwars1sStatusInaa = false;
            }
        } else {
            for (String PlayerName: bedwars1sPlayerListInaa) {
                Player player2 = Bukkit.getPlayer(PlayerName);
                if (player2 == null) return;
                player2.sendMessage(ChatColor.AQUA + "人数が足りません。2人以上が必要です。");
                player2.sendMessage(ChatColor.AQUA + "現在1人です。");
            }
        }
    }



    public static void removePlayerName(Player player, ArrayList<String> cannotDamageList) {
        String playerName = player.getName();
        if (bedwars1sPlayerListInaa.contains(playerName)) {
            bedwars1sPlayerListInaa.remove(playerName);
        }

        else if (bedwars1sPlayerListInbb.contains(playerName)) {
            bedwars1sPlayerListInbb.remove(playerName);
        }

        else if (bedwars4vPlayerListInaa.contains(playerName)) {
            bedwars4vPlayerListInaa.remove(playerName);
        }

        else if (bedwars4vPlayerListInbb.contains(playerName)) {
            bedwars4vPlayerListInbb.remove(playerName);
        }

        if (!cannotDamageList.contains(playerName)) {
            cannotDamageList.add(playerName);
        }
    }








//    public static void bedwars1sPlayerListSizeChecker() {
//        if (bedwars1sPlayerList.size() == 1) {
//            for (String PlayerName: bedwars1sPlayerList) {
//                Player player2 = Bukkit.getPlayer(PlayerName);
//                if (player2 == null) return;
//                player2.sendMessage("bedwarsがキャンセルされました");
//            }
//            bedwarsTimerUtil.stopBedwarsCountDownTimer();
//        } else return;
//    }





    public static void bedwars1sStartSession(ArrayList<String> bedwars1sPlayerList, S1 plugin, ArrayList<String> cannotDamageList) {

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                for (String PlayerName: bedwars1sPlayerList) {
                    cannotDamageList.remove(PlayerName);
                }
            }
        }, 410L);

        int playerListSize = bedwars1sPlayerList.size();
        List<String[]> colorList = new ArrayList<>();
//        List<String[]> mapList = new ArrayList<>();
        colorList.add(new String[] {"Red", "Blue", "Yellow", "Green", "Black", "Purple", "White", "Lime"});
//        mapList.add(new String[] {"aa", "aa"});


        for (int i = playerListSize; i == 0; i -= 1) {
            int listSize = bedwars1sPlayerList.size();
            int randomInt = (int) (Math.random() * listSize);
            String[] gotElement = colorList.get(randomInt);
            String element = gotElement[randomInt];


            String selectedPlayer = bedwars1sPlayerList.get(randomInt);
            Player selectedPlayerPL = Bukkit.getPlayer(selectedPlayer);
            if (selectedPlayerPL == null) return;

            bedwars1sPlayerWearArmor(selectedPlayerPL, element);

//            int listSize2 = mapList.size();
//            int ramdomInt2 = (int) (Math.random() * listSize);
//            String[] gotElement2 = mapList.get(ramdomInt2);
//            String element2 = gotElement[ramdomInt2];
//            bedwars1sPlayerTeleport(selectedPlayerPL, element2);

            listSize = listSize - 1;

            i = i - 1;
        }




    }


    public static void bedwars1sPlayerWearArmor(Player player, String colorName) {


        if (colorName.equals("Red")) {
            Color color = Color.RED;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.RED_WOOL);
        }
        else if (colorName.equals("Blue")) {
            Color color = Color.BLUE;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.BLUE_WOOL);
        }
        else if (colorName.equals("Yellow")) {
            Color color = Color.YELLOW;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.YELLOW_WOOL);
        }
        else if (colorName.equals("Green")) {
            Color color = Color.GREEN;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.GREEN_WOOL);
        }
        else if (colorName.equals("Black")) {
            Color color = Color.BLACK;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.BLACK_WOOL);
        }
        else if (colorName.equals("Purple")) {
            Color color = Color.PURPLE;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.PURPLE_WOOL);
        }
        else if (colorName.equals("White")) {
            Color color = Color.WHITE;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.WHITE_WOOL);
        }
        else if (colorName.equals("Lime")) {
            Color color = Color.LIME;
            helmet = ItemUtil.setLeatherColor(Material.LEATHER_HELMET, color);
            chestplate = ItemUtil.setLeatherColor(Material.LEATHER_CHESTPLATE, color);
            leggings = ItemUtil.setLeatherColor(Material.LEATHER_LEGGINGS, color);
            boots = ItemUtil.setLeatherColor(Material.LEATHER_BOOTS, color);
            colorBlockBeforeGame = ItemUtil.setColorWool(Material.LIME_WOOL);
        }

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, colorBlockBeforeGame);
    }



    public static void bedwars1sPlayerTeleport(Player player, String element) {
        ItemStack item = player.getInventory().getItem(0);
        if (item == null) return;
        Material material = item.getType();
        if (element == "a") {
            if (material.equals(Material.RED_WOOL)) {
                player.teleport(bedwars1sREDInA);
            }
            else if (material.equals(Material.BLUE_WOOL)) {
                player.teleport(bedwars1sBLUEInA);
            }
            else if (material.equals(Material.YELLOW_WOOL)) {
                player.teleport(bedwars1sYellowInA);
            }
            else if (material.equals(Material.GREEN_WOOL)) {
                player.teleport(bedwars1sGreenInA);
            }
            else if (material.equals(Material.BLACK_WOOL)) {
                player.teleport(bedwars1sBlackInA);
            }
            else if (material.equals(Material.PURPLE_WOOL)) {
                player.teleport(bedwars1sPurpleInA);
            }
            else if (material.equals(Material.WHITE_WOOL)) {
                player.teleport(bedwars1sWhiteInA);
            }
            else if (material.equals(Material.LIME_WOOL)) {
                player.teleport(bedwars1sLimeInA);
            }
            player.getInventory().clear();
        }

        else if (element == "b") {
            if (material.equals(Material.RED_WOOL)) {
                player.teleport(bedwars1sREDInB);
            }
            else if (material.equals(Material.BLUE_WOOL)) {
                player.teleport(bedwars1sBLUEInB);
            }
            else if (material.equals(Material.YELLOW_WOOL)) {
                player.teleport(bedwars1sYellowInB);
            }
            else if (material.equals(Material.GREEN_WOOL)) {
                player.teleport(bedwars1sGreenInB);
           }
            else if (material.equals(Material.BLACK_WOOL)) {
                player.teleport(bedwars1sBlackInB);
            }
            else if (material.equals(Material.PURPLE_WOOL)) {
                player.teleport(bedwars1sPurpleInB);
            }
            else if (material.equals(Material.WHITE_WOOL)) {
                player.teleport(bedwars1sWhiteInB);
            }
            else if (material.equals(Material.LIME_WOOL)) {
                player.teleport(bedwars1sLimeInB);
            }
            player.getInventory().clear();
        }









    }
    //各アーマーに色をつける
}
