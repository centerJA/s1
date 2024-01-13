package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
//                ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
//                ItemStack woodPickaxe = new ItemStack(Material.WOOD_PICKAXE);
//                ItemStack woodAxe = new ItemStack(Material.WOOD_AXE);
//                ItemStack sciccors = new ItemStack(Material.SHEARS);
                ItemStack block = new ItemStack(Material.WOOL, 64, (byte)0);
                ItemStack block1 = new ItemStack(Material.WOOL, 64, (byte)1);
                ItemStack block2 = new ItemStack(Material.WOOL, 64, (byte)2);
                ItemStack block3 = new ItemStack(Material.WOOL, 64, (byte)3);
                ItemStack block4 = new ItemStack(Material.WOOL, 64, (byte)4);
                ItemStack block5 = new ItemStack(Material.WOOL, 64, (byte)5);
                ItemStack block6 = new ItemStack(Material.WOOL, 64, (byte)6);
                ItemStack block7 = new ItemStack(Material.WOOL, 64, (byte)7);
                ItemStack block8 = new ItemStack(Material.WOOL, 64, (byte)8);


                for (String PlayerName: bedwarsPlayerList) {
                    player.sendMessage(String.valueOf(PlayerName));
                    if (PlayerName.equals(bedwarsPlayerList.get(0))) {
                        Player playerABLUE = Bukkit.getPlayer(PlayerName);
                        playerABLUE.teleport(bedwarsPlayerLocation1BLUE);
                        playerABLUE.sendMessage("あなたはBLUEです");
                        playerABLUE.sendMessage("スタート!");
//                        playerABLUE.getInventory().setItem(0, woodSword);
//                        playerABLUE.getInventory().setItem(1, woodPickaxe);
//                        playerABLUE.getInventory().setItem(2, woodAxe);
//                        playerABLUE.getInventory().setItem(3, sciccors);
                        playerABLUE.getInventory().setItem(0, block);
                        playerABLUE.getInventory().setItem(1, block1);
                        playerABLUE.getInventory().setItem(2, block2);
                        playerABLUE.getInventory().setItem(3, block3);
                        playerABLUE.getInventory().setItem(4, block4);
                        playerABLUE.getInventory().setItem(5, block5);
                        playerABLUE.getInventory().setItem(6, block6);
                        playerABLUE.getInventory().setItem(7, block7);
                        playerABLUE.getInventory().setItem(8, block8);



                    }
                    if (PlayerName.equals(bedwarsPlayerList.get(1))) {
                        Player playerBRED = Bukkit.getPlayer(PlayerName);
                        playerBRED.teleport(bedwarsPlayerLocation2RED);
                        playerBRED.sendMessage("あなたはREDです");
                        playerBRED.sendMessage("スタート!");
                        playerBRED.getInventory().setItem(0, block);
                        playerBRED.getInventory().setItem(1, block1);
                        playerBRED.getInventory().setItem(2, block2);
                        playerBRED.getInventory().setItem(3, block3);
                        playerBRED.getInventory().setItem(4, block4);
                        playerBRED.getInventory().setItem(5, block5);
                        playerBRED.getInventory().setItem(6, block6);
                        playerBRED.getInventory().setItem(7, block7);
                        playerBRED.getInventory().setItem(8, block8);
                    }
                }
            }
        }, 410L);
    }

}