package stefano.s1.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import stefano.s1.Config;
import sun.jvm.hotspot.debugger.win32.coff.COFFLineNumber;

public class textDisplayUtil {
    public static void mainText(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showPvpIsStopping(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showPvpIsWaiting(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showPvpIsPlaying(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showPvpMainText(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showKnockBackIsStopping(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showKnockBackIsWaiting(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showKnockBackIsPlaying(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void showKnockBackMainText(Location location, String text, boolean visible) {
        if (location.getWorld() == null) return;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStandSettings(armorStand, text, visible);
    }

    public static void removeMainText(World world) {
        for (Entity entity: world.getEntities()) {
            if (entity instanceof ArmorStand) {
                Location loc = entity.getLocation();
                if (loc.equals(Config.textLocationAnvil)) {
                    entity.remove();
                }
                if (loc.equals(Config.textLocationPvpMainText)) {
                    entity.remove();
                }
                if (loc.equals(Config.textLocationKnockBackMainText)) {
                    entity.remove();
                }
            }
        }
    }

    public static void removePvpColumnText(World world) {
        for (Entity entity: world.getEntities()) {
            if (entity instanceof ArmorStand) {
                Location loc = entity.getLocation();
                if (loc.equals(Config.textLocationPvpColumn)) {
                    entity.remove();
                }
            }
        }
    }

    public static void removeKnockBackColumnText(World world) {
        for (Entity entity: world.getEntities()) {
            if (entity instanceof ArmorStand) {
                Location loc = entity.getLocation();
                if (loc.equals(Config.textLocationKnockBackColumn)) {
                    entity.remove();
                }
            }
        }
    }

    public static void armorStandSettings(ArmorStand as, String text, boolean visible) {
        as.setBasePlate(false);
        as.setCustomNameVisible(true);
        as.setCustomName(text);
        as.setArms(false);
        as.setVisible(visible);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
    }

    public static void showMainArmorStand() {
        boolean visible = false;
        showPvpMainText(Config.textLocationPvpMainText, Config.pvpMainText, visible);
        showKnockBackMainText(Config.textLocationKnockBackMainText, Config.knockBackMainText, visible);
        mainText(Config.textLocationAnvil, Config.mainText, visible);
    }

}
