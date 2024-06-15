package stefano.s1.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class pvpUtil {
    public static ArrayList<Location> blockLocationList = new ArrayList<>();

    public static Location pvpStartPoint = new Location(Bukkit.getWorld("stefanovarentino"), 25, 70, 24, -90, 0);


    public static void blockLocation(Location location) {
        blockLocationList.add(location);
    }

    public static void blockBreak() {
        for (Location blockBreak: blockLocationList) {
            blockBreak.getBlock().setType(Material.AIR);
        }
    }

    public static void blockLocationAllRemove() {
        if (blockLocationList.size() != 0) {
            blockLocationList.clear();
        }
    }

    public static void playerSettingsBeforeGame(Player player) {
        player.teleport(pvpStartPoint);
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(6);
        player.setSprinting(false);
        player.setNoDamageTicks(600);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1));
        player.sendMessage(ChatColor.YELLOW + "範囲は、x=-125~125、z=-125~125です");
        player.getInventory().clear();
        ItemStack lobby = new ItemStack(Material.RED_MUSHROOM, 1);
        ItemMeta lobbyMeta = lobby.getItemMeta();
        if (lobbyMeta == null) return;
        lobbyMeta.setDisplayName("ロビーに戻る");
        player.getInventory().setItem(9, lobby);
    }

    public static void playerSettingsInGame(Player player) {
        ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET);
        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
        ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack ironChestPlate = new ItemStack(Material.IRON_CHESTPLATE);
        player.setFoodLevel(20);
        player.setSprinting(true);
        ItemStack pvpsword = new ItemStack(Material.IRON_SWORD, 1);
        player.getInventory().setItem(0, pvpsword);
        ItemStack pvpfood = new ItemStack(Material.COOKED_BEEF, 64);
        player.getInventory().setItem(1, pvpfood);
        player.getInventory().setItem(2, ItemUtil.setCustomPotionMeta(PotionEffectType.INVISIBILITY, Material.SPLASH_POTION, "透明化"));
        player.getInventory().setItem(3, ItemUtil.setCustomPotionMeta(PotionEffectType.REGENERATION, Material.SPLASH_POTION, "再生"));
        player.getInventory().setItem(4, ItemUtil.setCustomPotionMeta(PotionEffectType.POISON, Material.LINGERING_POTION, "毒"));
        ItemStack pvpbow = new ItemStack(Material.BOW, 1);
        player.getInventory().setItem(5, pvpbow);
        ItemStack pvparrow = new ItemStack(Material.ARROW, 64);
        player.getInventory().setItem(34, pvparrow);
        player.getInventory().setItem(35, pvparrow);
        player.getInventory().setHelmet(ironHelmet);
        player.getInventory().setChestplate(ironChestPlate);
        player.getInventory().setBoots(ironBoots);
        player.getInventory().setLeggings(ironLeggings);
        ItemStack pvpBlock = new ItemStack(Material.STONE, 64);
        player.getInventory().setItem(6, pvpBlock);
        player.sendTitle(ChatColor.AQUA + "", ChatColor.RED + "最後まで生き残れ！", 20, 40, 20);
    }
}
