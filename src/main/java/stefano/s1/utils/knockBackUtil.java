package stefano.s1.utils;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import stefano.s1.S1;

import java.util.ArrayList;

public class knockBackUtil {

    public static Location knockBackPlayerLocation1BLUE, knockBackPlayerLocation2RED;

    public static ArrayList<Location> knockBackBlockLocationList;

    public static void startknockBack(S1 plugin, Player player, ArrayList<String> knockBackPlayerList) {
        World world = Bukkit.getWorld("stefanovarentino");
        knockBackPlayerLocation1BLUE = new Location(world, -12.500, 229, 82.500, 180, 0);
        knockBackPlayerLocation2RED = new Location(world,-10.500, 229, 42.500, 0, 0);
        knockBackBlockLocationList = new ArrayList<>();
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack sciccors = new ItemStack(Material.SHEARS);
                ItemStack blockBlue = new ItemStack(Material.BLUE_WOOL, 64);
                ItemStack blockRed = new ItemStack(Material.RED_WOOL, 64);
                ItemStack lobby = new ItemStack(Material.RED_MUSHROOM);
                ItemStack knockBackStick = new ItemStack(Material.STICK, 1);
                knockBackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);


                for (String PlayerName: knockBackPlayerList) {
                    player.sendMessage(String.valueOf(PlayerName));
                    if (PlayerName.equals(knockBackPlayerList.get(0))) {
                        Player playerABLUE = Bukkit.getPlayer(PlayerName);
                        if (playerABLUE == null) return;
                        playerABLUE.teleport(knockBackPlayerLocation1BLUE);
                        playerABLUE.sendMessage("あなたはBLUEです");
                        playerABLUE.sendMessage("スタート!");
                        playerABLUE.setGameMode(GameMode.SURVIVAL);
                        playerABLUE.getInventory().setItem(0, sciccors);
                        playerABLUE.getInventory().setItem(1, blockBlue);
                        playerABLUE.getInventory().setItem(2, knockBackStick);
                        playerABLUE.getInventory().setItem(9, lobby);
                    }
                    else if (PlayerName.equals(knockBackPlayerList.get(1))) {
                        Player playerBRED = Bukkit.getPlayer(PlayerName);
                        if (playerBRED == null) return;
                        playerBRED.teleport(knockBackPlayerLocation2RED);
                        playerBRED.sendMessage("あなたはREDです");
                        playerBRED.sendMessage("スタート!");
                        playerBRED.setGameMode(GameMode.SURVIVAL);
                        playerBRED.getInventory().setItem(0, sciccors);
                        playerBRED.getInventory().setItem(1, blockRed);
                        playerBRED.getInventory().setItem(2, knockBackStick);
                        playerBRED.getInventory().setItem(9, lobby);
                    }
                }
            }
        }, 410L);
    }

    public static void sendWinMsg(String winner, ArrayList<String> knockBackPlayerList) {
        for (String knockBackWinner: knockBackPlayerList) {
            if (winner.equals(knockBackWinner)) {
                Player trueWinner = Bukkit.getPlayer(winner);
                if (trueWinner == null) return;
                trueWinner.sendTitle(ChatColor.GREEN + "勝利!", ChatColor.AQUA + "ノックバックの制覇者", 20, 40, 20);
                trueWinner.sendMessage("赤いキノコをクリックしてロビーに戻る");
                trueWinner.getInventory().clear();
                trueWinner.getInventory().setItem(0, ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
            } else return;
        }
    }

    public static void knockBackWhoBlockPlaceCheck(ArrayList<String> knockBackPlayerList, Player player2, Location location) {
        for (String PlayerName: knockBackPlayerList) {
            if (player2.getName().equals(PlayerName) && location != null) {
                knockBackBlockLocationList.add(location);
            }
        }
    }

    public static void knockBackBlockCrear() {
        for (Location location: knockBackBlockLocationList) {
            location.getBlock().setType(Material.AIR);
        }
    }
}