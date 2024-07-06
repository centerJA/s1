package stefano.s1.utils;

import jdk.tools.jlink.plugin.Plugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import stefano.s1.Config;
import stefano.s1.S1;

import java.io.IOException;
import java.util.Objects;

public class worldSettings {

    public static void firstSettings(Player player, Location lobby) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.sendTitle(player.getName() + ChatColor.AQUA + "さん", ChatColor.AQUA + "こんにちは！", 20, 40, 20);
        player.sendMessage(ChatColor.AQUA + "-----Stefano Varentinoへようこそ!-----");
        player.sendMessage(ChatColor.YELLOW + "アスレチックやPVPなど、いくつかのゲームがあります!");
        player.sendMessage(ChatColor.YELLOW + "前にある金床をクリックしてゲームをプレイすることができます。");
        player.sendMessage(ChatColor.GREEN + "レッドストーンブロック…アスレチックがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "エメラルド…PVPがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "赤いきのこ…ロビーの中心に戻ります。");
        player.sendMessage(ChatColor.GREEN + "/sv…コマンド一覧を表示します。");
        player.sendMessage(ChatColor.AQUA + "------------------------------");
        player.teleport(lobby);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
    }
    public static void signClick(Player player, String[] lines, FileConfiguration checkpointList, Location athleticClear, S1 plugin) throws IOException {
        if (Objects.equals(lines[0], "tyekkupoinnto") || Objects.equals(lines[0], "チェックポイント")) {
            player.sendMessage(ChatColor.DARK_GREEN + "無事にチェックポイントを設定しました!");
            checkpointList.set(String.valueOf(player.getUniqueId()), player.getLocation());
            player.getInventory().setHeldItemSlot(3);
        }
        else if(Objects.equals(lines[0], "今日のお知らせ")) {
            Config.showLatestTips(player);
        }
        else if (Objects.equals(lines[0], "アスレの王者")) {
            player.getWorld().playEffect(player.getLocation(), Effect.WITHER_SHOOT, 0, 1);
        }
        else if (Objects.equals(lines[0], "stefanovarentino")) {
            if (athleticClear.getWorld() == null) return;
            athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 1);
        }
        else if (Objects.equals(lines[0], "この世界の名前は")) {
            player.sendMessage("答えはなんと...." + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "!");
        }
        else if (Objects.equals(lines[0], "この看板をクリックし") || Objects.equals(lines[0], "opathletic")) {
            if (Objects.equals(lines[1], "てタイムをリセット") || Objects.equals(lines[1], "remove")) {
                askToUserYesOrNo(player);
            }
        }
        else if (Objects.equals(lines[0], "この先は暗室です")) {
            player.sendMessage(ChatColor.AQUA + "い、今、、不気味な" + ChatColor.DARK_RED + "声" + ChatColor.AQUA + "、しなかった、？");
            World world = Bukkit.getWorld("stefanovarentino");
            if (world == null) return;
            world.getBlockAt(Config.darkRoomLocationUnder).setType(Material.AIR);
            world.getBlockAt(Config.darkRoomLocationUp).setType(Material.AIR);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(Config.darkRoomPlayerTeleport1);
                }
            }, 20L);

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.teleport(Config.darkRoomPlayerTeleport);
                    world.getBlockAt(Config.darkRoomLocationUnder).setType(Material.STONE);
                    world.getBlockAt(Config.darkRoomLocationUp).setType(Material.STONE);
                }
            }, 10L);

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.AQUA + "開放されたみたいだ...");
                    world.getBlockAt(Config.darkRoomLocationUnder).setType(Material.AIR);
                    world.getBlockAt(Config.darkRoomLocationUp).setType(Material.AIR);
                }
            }, 600L);
        }
    }

    public static void athleticTimeReset(Player player, S1 plugin) throws IOException {
        PlayerScore.removePlayerTime(player, plugin);
        ScoreBoardUtil.updateRanking(player);
    }

    public static void anvilClickAction(Player player, S1 plugin, PlayerInteractEvent e) {
        Inventory gameListInventory = Bukkit.createInventory(null, 9, "ゲーム一覧");
        gameListInventory.setItem(0, ItemUtil.setItemMeta("pvp", Material.EMERALD));
        ItemStack knockBackStick = ItemUtil.setItemMeta("knockback", Material.STICK);
        if (knockBackStick == null) return;
        knockBackStick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
        gameListInventory.setItem(1, knockBackStick);
        gameListInventory.setItem(2, ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (player.getPlayer() == null) return;
                player.getPlayer().openInventory(gameListInventory);
                player.addScoreboardTag("game");
            }
        }, 3L);
        e.setCancelled(true);
    }

    public static void askToUserYesOrNo(Player player) {
        player.sendMessage("本当に操作を続けますか?");
        TextComponent userCommentYes = new TextComponent(ChatColor.GREEN + "[YES]");
        userCommentYes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv click perform userCommentYes"));
        TextComponent userCommentNo = new TextComponent(ChatColor.RED + "[NO]");
        userCommentNo.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv click perform userCommentNo"));
        player.spigot().sendMessage(userCommentYes);
        player.spigot().sendMessage(userCommentNo);
    }
}
