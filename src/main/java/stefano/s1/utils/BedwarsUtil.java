package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import stefano.s1.S1;

import java.util.ArrayList;

public class BedwarsUtil {

    public static Location bedwarsPlayerLocation1BLUE, bedwarsPlayerLocation2RED;

    public static void startBedwars(S1 plugin, Player player, ArrayList<String> bedwarsPlayerList) {
        World world = Bukkit.getWorld("stefanovarentino");
        bedwarsPlayerLocation1BLUE = new Location(world, -12.500, 229, 82.500, 180, 0);
        bedwarsPlayerLocation2RED = new Location(world,-11.500, 229, 42.500, 0, 0);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
                ItemStack woodPickaxe = new ItemStack(Material.WOOD_PICKAXE);
                ItemStack woodAxe = new ItemStack(Material.WOOD_AXE);
                ItemStack sciccors = new ItemStack(Material.SHEARS);
                ItemStack blockBlue = new ItemStack(Material.WOOL, 64, (byte)11);
                ItemStack blockRed = new ItemStack(Material.WOOL, 64, (byte)14);


                for (String PlayerName: bedwarsPlayerList) {
                    player.sendMessage(String.valueOf(PlayerName));
                    if (PlayerName.equals(bedwarsPlayerList.get(0))) {
                        Player playerABLUE = Bukkit.getPlayer(PlayerName);
                        playerABLUE.teleport(bedwarsPlayerLocation1BLUE);
                        playerABLUE.sendMessage("あなたはBLUEです");
                        playerABLUE.sendMessage("スタート!");
                        playerABLUE.setGameMode(GameMode.SURVIVAL);
                        playerABLUE.getInventory().setItem(0, woodSword);
                        playerABLUE.getInventory().setItem(1, woodPickaxe);
                        playerABLUE.getInventory().setItem(2, woodAxe);
                        playerABLUE.getInventory().setItem(3, sciccors);
                        playerABLUE.getInventory().setItem(2, blockBlue);
                        playerABLUE.getInventory().setItem(5, blockRed);



                    }
                    if (PlayerName.equals(bedwarsPlayerList.get(1))) {
                        Player playerBRED = Bukkit.getPlayer(PlayerName);
                        playerBRED.teleport(bedwarsPlayerLocation2RED);
                        playerBRED.sendMessage("あなたはREDです");
                        playerBRED.sendMessage("スタート!");
                        playerBRED.setGameMode(GameMode.SURVIVAL);
                        playerBRED.getInventory().setItem(0, woodSword);
                        playerBRED.getInventory().setItem(1, woodPickaxe);
                        playerBRED.getInventory().setItem(2, woodAxe);
                        playerBRED.getInventory().setItem(3, sciccors);
                        playerBRED.getInventory().setItem(2, blockBlue);
                        playerBRED.getInventory().setItem(5, blockRed);
                    }
                }
            }
        }, 410L);
    }

}