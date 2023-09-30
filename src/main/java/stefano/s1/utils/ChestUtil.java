package stefano.s1.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ChestUtil {

    private int chestLimitTime;
    public static void setChest(Location location, ArrayList<Material> materials) {
        location.getBlock().setType(Material.CHEST);
        Chest chest = (Chest) location.getBlock().getState();
        Inventory chestInventory = chest.getInventory();
        for (Material i: materials) {
            ItemStack itemStack = new ItemStack(i, 1);
            chestInventory.addItem(itemStack);
        }
    }
}
