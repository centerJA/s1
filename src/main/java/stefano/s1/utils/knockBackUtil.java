package stefano.s1.utils;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import stefano.s1.S1;

import java.util.ArrayList;

public class knockBackUtil {

    public static Location knockBackPlayerLocation1BLUE, knockBackPlayerLocation2RED;

    public static void startknockBack(S1 plugin, Player player, ArrayList<String> knockBackPlayerList) {
        World world = Bukkit.getWorld("stefanovarentino");
        knockBackPlayerLocation1BLUE = new Location(world, -12.500, 229, 82.500, 180, 0);
        knockBackPlayerLocation2RED = new Location(world,-10.500, 229, 42.500, 0, 0);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                ItemStack sciccors = new ItemStack(Material.SHEARS);
                ItemStack blockBlue = new ItemStack(Material.BLUE_WOOL, 64);
                ItemStack blockRed = new ItemStack(Material.RED_WOOL, 64);
                ItemStack lobby = new ItemStack(Material.RED_MUSHROOM);
                ItemStack knockBackStick = new ItemStack(Material.STICK, 1);
                knockBackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);


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
                    if (PlayerName.equals(knockBackPlayerList.get(1))) {
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

}