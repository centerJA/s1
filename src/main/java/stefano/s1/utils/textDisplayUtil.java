package stefano.s1.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class textDisplayUtil {
    public static void showText(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setBasePlate(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(text);
        armorStand.setArms(false);
        armorStand.setVisible(visible);
        armorStand.setInvulnerable(true);
        armorStand.setCanPickupItems(false);
        armorStand.setGravity(false);
    }

    public static void removeText(World world) {
        for (Entity entity: world.getEntities()) {
            if (entity instanceof ArmorStand) {
                entity.remove();
            }
        }
    }
}
