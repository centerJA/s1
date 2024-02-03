package stefano.s1;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Map;

public class Config{
    public static int Range = 125;

    public static int CoolDownWitch = 0;
    public static Location lobby = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 223, 25.500, -90, 0)
                          ,athletic1 = new Location(Bukkit.getWorld("stefanovarentino"), 50.500, 239, 25.500, -90, 0)
                          ,svinfo = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 216, 25.500, 90, 0)
                          ,pvpStart = new Location(Bukkit.getWorld("stefanovarentino"), 25.500, 70, 24, -90, 0);

    public static ArrayList<String> playerList = new ArrayList<>()
                 ,checkpointList = new ArrayList<>();

    public static ArrayList<Material> itemList1 = new ArrayList<>();
}


