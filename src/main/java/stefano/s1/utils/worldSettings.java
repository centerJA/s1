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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;
import stefano.s1.Config;
import stefano.s1.S1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class worldSettings {

    public static void firstSettings(Player player, Location lobby, ArrayList<String> cannotDamageList) {
        cannotDamageList.add(player.getName());
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
    public static void signClick(Player player, String[] lines, FileConfiguration checkpointList, Location athleticClear, S1 plugin, ArrayList<String> knockBackPlayerList, ArrayList<String> playerList, ArrayList<String> playerCanPlayEffectInPvpList, int ramdomInt, ArrayList<PotionEffectType> effectList) throws IOException {
        if (Objects.equals(lines[0], "tyekkupoinnto") || Objects.equals(lines[0], "チェックポイント")) {
            player.sendMessage(ChatColor.DARK_GREEN + "無事にチェックポイントを設定しました!");
            checkpointList.set(String.valueOf(player.getUniqueId()), player.getLocation());
            player.getInventory().setHeldItemSlot(3);
        } else if (Objects.equals(lines[0], "今日のお知らせ")) {
            Config.showLatestTips(player);
        } else if (Objects.equals(lines[0], "アスレの王者")) {
            player.getWorld().playEffect(player.getLocation(), Effect.WITHER_SHOOT, 0, 1);
        } else if (Objects.equals(lines[0], "stefanovarentino")) {
            if (athleticClear.getWorld() == null) return;
            athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 1);
        } else if (Objects.equals(lines[0], "この世界の名前は")) {
            player.sendMessage("答えはなんと...." + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "!");
        } else if (Objects.equals(lines[0], "この看板をクリックし") || Objects.equals(lines[0], "opathletic")) {
            if (Objects.equals(lines[1], "てタイムをリセット") || Objects.equals(lines[1], "remove")) {
                askToUserYesOrNo(player);
            }
        } else if (Objects.equals(lines[0], "この先は暗室です")) {
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
        } else if (Objects.equals(lines[0], "ここをクリックして") && Objects.equals(lines[1], "ルールを確認する")) {
            String PlayerStringName = player.getName();
            for (String PlayerName : knockBackPlayerList) {
                if (PlayerName.equals(PlayerStringName)) {
                    returnKnockBackRules(player);
                }
            }

            for (String PlayerName2 : playerList) {
                if (PlayerName2.equals(PlayerStringName)) {
                    returnPvpRules(player);
                }
            }
        } else if (Objects.equals(lines[0], "effects")) {
            String StringPlayerName = player.getName();
            if (playerCanPlayEffectInPvpList.contains(StringPlayerName)) {
                PotionEffectType effect = effectList.get((ramdomInt));
                player.getInventory().setItem(11, ItemUtil.setCustomPotionMeta(effect, Material.SPLASH_POTION, "ポーション"));
                playerCanPlayEffectInPvpList.remove(player.getName());
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        playerCanPlayEffectInPvpList.add(player.getName());
                    }
                }, 600L);
            } else {
                    player.sendMessage("クールダウン中です!");
                    return;
            }
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

    public static void returnKnockBackRules(Player player) {
        player.sendMessage("-----Knockackのルール説明-----");
        player.sendMessage("20秒のタイマーが終わると、自動的にマップにテレポートします");
        player.sendMessage("テレポートすると、ノックバック棒と羊毛1スタック、はさみが渡されます");
        player.sendMessage("マップは敵と向かい合わせになるような状態になっています");
        player.sendMessage(ChatColor.GREEN + "羊毛を使って相手の陣地まで行き、ノックバック棒で落とすと勝ちになります");
        player.sendMessage("「落ちる」の判断基準は、y座標が200以下になったときです");
        player.sendMessage(ChatColor.YELLOW + "外に行き過ぎると見えない壁があり、ブロックを置けません");
        player.sendMessage("すぐに相手の陣地に行き、相手を落としましょう!");
        player.sendMessage("------------------------------");
    }

    public static void returnPvpRules(Player player) {
        player.sendMessage("-----PVPのルール説明-----");
        player.sendMessage("20秒のタイマーが終わると、自動的にマップにテレポートします");
        player.sendMessage("テレポートすると、防具や剣、ブロック、弓矢、ポーションなどが渡されます");
        player.sendMessage("30秒間のクールダウンの時間の後、pvpが可能になります");
        player.sendMessage("スポーン地点の右側にはチェストがスポーンするところがあります");
        player.sendMessage(ChatColor.GREEN + "チェストにはダイヤ剣や金リンゴなどの強いアイテムが入手できます");
        player.sendMessage(ChatColor.YELLOW + "外に行き過ぎると見えない壁があり、出ることができません");
        player.sendMessage("時間制限の前に全ての敵を倒しましょう!");
        player.sendMessage("------------------------------");
    }

    public static void sendErrorMessageToPlayer(Player player, int s, String reason, String className) {
        player.sendMessage("問題が発生しました");
        player.sendMessage("Error: " + className + ".java:" + s + "****" + reason + "****" + "ErrorCode(" + s + ") ");
        player.sendMessage("/sv report でエラーコードを報告してください");
    }
}
