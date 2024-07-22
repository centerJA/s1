package stefano.s1.SvCommand;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.Config;
import stefano.s1.S1;
import stefano.s1.utils.*;

import java.io.IOException;
import java.util.Arrays;

import static stefano.s1.utils.AthleticTimer.tasks;


public class SvCommand implements CommandExecutor {
    private static final S1 plugin = S1.getPlugin(S1.class);
    private AthleticTimer athleticTimer;

    World world;

    public SvCommand(AthleticTimer athleticTimer) {
        this.athleticTimer = athleticTimer;
        this.world = Bukkit.getWorld("stefanovarentino");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        World world = player.getWorld();
        if (command.getName().equalsIgnoreCase("sv")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.AQUA + "----------Stefano Varentino command list----------");
                player.sendMessage(ChatColor.RED + "赤色のコマンド" + ChatColor.WHITE + "は開発中のコマンドです");
                player.sendMessage(ChatColor.YELLOW + "開発中のコマンドは使えない場合があります");
                player.sendMessage(ChatColor.YELLOW + "クリックしてコマンドを表示します");


                TextComponent kindsOfTeleport = new TextComponent(ChatColor.UNDERLINE + "テレポート関係のコマンド");
                TextComponent kindsOfTell = new TextComponent(ChatColor.UNDERLINE + "テキスト関係のコマンド");
                TextComponent kindsOfOthers = new TextComponent(ChatColor.UNDERLINE + "他のコマンド");
                kindsOfTeleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv click perform teleport"));
                kindsOfTell.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv click perform tell"));
                kindsOfOthers.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv click perform other"));



                player.spigot().sendMessage(kindsOfTeleport);
                player.spigot().sendMessage(kindsOfTell);
                player.spigot().sendMessage(kindsOfOthers);
                player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
            }
            else {
                if (args[0].equals("click") && args[1].equals("perform")) {
                    TextComponent back = new TextComponent(ChatColor.UNDERLINE + "前のページに戻る");
                    back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sv"));
                    if (args[2].equals("userCommentYes")) {
                        try {
                            worldSettings.athleticTimeReset(player, plugin);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        player.sendMessage("操作が完了しました。");
                    }

                    else if (args[2].equals("userCommentNo")) {
                        player.sendMessage("操作をキャンセルしました。");
                    }

                    else if (args[2].equals("teleport")) {
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                        player.sendMessage("/sv tp lobby       ロビーにテレポートします");
                        player.sendMessage("/sv tp athletic       アスレチックにテレポートします");
                        player.sendMessage("/sv tp pvpMap       pvpの時に使用するマップにテレポートします");
                        player.sendMessage("/sv tp svinfo       情報センターにテレポートします");
                        player.spigot().sendMessage(back);
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                    }
                    else if (args[2].equals("tell")) {
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                        player.sendMessage("/sv tell origin       名前の由来を表示します");
                        player.sendMessage("/sv tell cooperative       このワールドの共同製作者を表示します");
                        player.sendMessage("/sv tell homepageurl       ホームページのURLを表示します");
                        player.spigot().sendMessage(back);
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                    }
                    else if (args[2].equals("other")) {
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                        player.sendMessage("/sv help       ヘルプを表示します");
                        player.sendMessage("/sv effects       エフェクトを出します");
                        player.sendMessage("/sv report       このコマンドを打って表示されるリンクをクリックしてバグや誤字を報告してください");
                        player.sendMessage("/sv title       タイトルを表示します");
                        player.sendMessage("/sv hello       ワールド紹介文を表示します");
                        player.sendMessage("/sv notice       最近のお知らせを表示します");
                        player.sendMessage("/sv history       お知らせの歴史を表示します");
                        player.spigot().sendMessage(back);
                        player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                    }
                }
                else if (args[0].equals("op")) {
                    if (player.getName().equals("markcs11") || player.getName().equals("InfInc")) {
                        if (args[1].equals("c")) {
                            player.sendMessage(ChatColor.DARK_RED + "(OP ACTION)type:set gamemode gamemode:creative user:" + ChatColor.DARK_RED + player.getName());
                            player.setGameMode(GameMode.CREATIVE);
                        }
                        else if (args[1].equals("s")) {
                            player.sendMessage(ChatColor.DARK_RED + "(OP ACTION)type:set gamemode gamemode:survival user:" + ChatColor.DARK_RED + player.getName());
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        else if (args[1].equals("m")) {
                            player.sendMessage(ChatColor.DARK_RED + "(OP ACTION)type:give item item:material.RED_MUSHROOM user:"+ ChatColor.DARK_RED + player.getName());
                            ItemStack material = new ItemStack(Material.RED_MUSHROOM);
                            player.getInventory().addItem(material);
                        }
                        else {
                            return false;
                        }
                    } else {
                        player.sendMessage("OP権限が必要です");
                        return false;
                    }
                }
                else if (args[0].equals("report")) {
                    ComponentBuilder report = new ComponentBuilder("バグや誤字を見つけた際には" + ChatColor.AQUA + "ここをクリック" + ChatColor.WHITE + "して報告をお願いします!");
                    BaseComponent[] reportURL = report.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAANAAXcoW2dUQkEzSTlHUkZCNVVWSVdXUzRXOEtXSjFSTy4u")).create();
                    player.spigot().sendMessage(reportURL);
                    player.sendMessage("必要のない報告やスパムはしないでください!");
                }
                else if (args[0].equals("hello")) {
                    player.sendMessage("どうもこんにちは！StefanoVarentinoを制作しているInfIncです！");
                    player.sendMessage("このワールドにはいろいろなギミックがあります。");
                    player.sendMessage("'/sv'という独自のコマンドが存在します。");
                    player.sendMessage("'/sv'と打って、コマンドの一覧を出します。");
                    player.sendMessage("又機能が多いところも特徴です。");
                    player.sendMessage("看板をクリックすると何か出てきたり、コマンドを打つと何か出てきたりします。");
                    player.sendMessage("svinfoでは、StefanoVarentinoの情報を見ることができます。");
                    player.sendMessage("これらのことを頭にいれながらStefanoVarentinoをプレイしてみてください！");
                    player.sendMessage(ChatColor.AQUA + "きっと何か見つかるはずです...");
                }
                else if (args[0].equals("effects")) {
                    player.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                    player.sendMessage("Stefano Varentino");
                }
                else if (args[0].equals("help")) {
                    ComponentBuilder msg1 = new ComponentBuilder("・コマンドの一覧を表示するには、ここをクリックします");
                    ComponentBuilder msg2 = new ComponentBuilder("・SVホームページにアクセスするには、ここをクリックします");
                    ComponentBuilder msg3 = new ComponentBuilder("・tofuホームページにアクセスするには、ここをクリックします");
                    BaseComponent[] message1 = msg1.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sv")).create();
                    BaseComponent[] message2 = msg2.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/283")).create();
                    BaseComponent[] message3 = msg3.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://tofu-66fa7.web.app/user/worlds/43")).create();
                    player.sendMessage("ヘルプが必要ですか?");
                    player.spigot().sendMessage(message1);
                    player.spigot().sendMessage(message2);
                    player.spigot().sendMessage(message3);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_DEATH, 2, 10);
                }
                else if (args[0].equals("tell")) {
                    if (args[1].equals("origin")) {
                        player.sendMessage(ChatColor.WHITE + "Stefano Varentinoの由来は" + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "です。");
                        player.sendMessage("ハンカチの名前から来てるとは思いませんね…");
                    }
                    else if (args[1].equals("cooperative")) {
                        player.sendMessage(ChatColor.AQUA + "ワールド作成協力者紹介");
                        player.sendMessage("1.ushikujin");
                        player.sendMessage("2.SleetyNote17020");
                        player.sendMessage("3.maintya3");
                        player.sendMessage("4. m1n_tya3");
                    }
                    else if (args[1].equals("homePageURL") || args[1].equals("homepageurl") || args[1].equals("homepageURL")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                        player.sendMessage(ChatColor.GREEN + "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/283");
                    }
                }
                else if (args[0].equals("notice")) {
                    Config.showLatestTips(player);
                }
                else if (args[0].equals("title")) {
                    player.sendTitle("SV", "This is Stefano Varentino", 5, 50, 35);
                }
                else if (args[0].equals("history")) {
                    Config.showAllTips(player);
                }
                else if (args[0].equals("tp")) {
                    if (args[1].equals("lobby")) {
                        player.sendMessage("lobbyにテレポートしました");
                        ScoreBoardUtil.removeScoreboard(player);
                        player.teleport(Config.lobby);
                        athleticTimer.stopTimer(player);
                        player.setLevel(0);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        Config.playerList.remove(player.getName());
                    }
                    else if (args[1].equals("athletic")) {
                        Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                        athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                        player.openInventory(athleticInventory);
                        player.addScoreboardTag("athletic");
                    }
                    else if (args[1].equals("pvpMap") || args[1].equals("pvpmap")) {
                        player.sendMessage("pvpMapにテレポートしました");
                        player.teleport(Config.pvpStart);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    }
                    else if (args[1].equals("svinfo")) {
                        player.sendMessage("svInfoにテレポートしました");
                        player.teleport(Config.svinfo);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    }
                }

            }

        }
        return false;
    }
}
