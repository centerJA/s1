package stefano.s1.SvCommand;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
import stefano.s1.utils.AthleticTimer;
import stefano.s1.utils.ItemUtil;
import stefano.s1.utils.PlayerScore;
import stefano.s1.utils.ScoreBoardUtil;

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
                player.sendMessage("開発中のコマンドは使えない場合があります");
                player.sendMessage("/sv       コマンドの一覧を表示します");
                player.sendMessage("/sv tell origin       名前の由来を表示します");
                player.sendMessage("/sv tell cooperative       このワールドの共同製作者を表示します");
                player.sendMessage("/sv tell homepageurl       ホームページのURLを表示します");
                player.sendMessage("/sv tp lobby       ロビーにテレポートします");
                player.sendMessage("/sv tp athletic       アスレチックにテレポートします");
                player.sendMessage("/sv tp pvpMap       pvpの時に使用するマップにテレポートします");
                player.sendMessage("/sv tp svinfo       情報センターにテレポートします");
                player.sendMessage("/sv help       ヘルプを表示します");
                player.sendMessage("/sv effects       エフェクトを出します");
                player.sendMessage(ChatColor.RED + "/sv report       このコマンドを打って表示されるリンクをクリックしてバグや誤字を報告してください");
                player.sendMessage("/sv title       タイトルを表示します");
                player.sendMessage("/sv hello       ワールド紹介文を表示します");
                player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
            }
            else {
                if (args[0].equals("op")) {
                    if (player.getName().equals("markcs11") || player.getName().equals("InfInc")) {
                        if (args[1].equals("c")) {
                            player.sendMessage(ChatColor.DARK_RED + "(OP ACTION)type:set gamemode gamemode:creative user:" + ChatColor.DARK_RED + player.getName());
                            player.setGameMode(GameMode.CREATIVE);
                        }
                        if (args[1].equals("s")) {
                            player.sendMessage(ChatColor.DARK_RED + "(OP ACTION)type:set gamemode gamemode:survival user:" + ChatColor.DARK_RED + player.getName());
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        if (args[1].equals("m")) {
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
                if (args[0].equals("report")) {
                    ComponentBuilder report = new ComponentBuilder("バグや誤字を見つけた際には" + ChatColor.AQUA + "ここをクリック" + ChatColor.WHITE + "して報告をお願いします!");
                    BaseComponent[] reportURL = report.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://google.com")).create();
                    player.spigot().sendMessage(reportURL);
                }
                if (args[0].equals("hello")) {
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
                if (args[0].equals("effects")) {
                    player.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                    player.sendMessage("Stefano Varentino");
                }
                if (args[0].equals("help")) {
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
                if (args[0].equals("tell")) {
                    if (args[1].equals("origin")) {
                        player.sendMessage(ChatColor.WHITE + "Stefano Varentinoの由来は" + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "です。");
                        player.sendMessage("ハンカチの名前から来てるとは思いませんね…");
                    }
                    if (args[1].equals("cooperative")) {
                        player.sendMessage("ワールド作成協力者紹介");
                        player.sendMessage("1.usikujin");
                        player.sendMessage("2.SleetyNote17020");
                        player.sendMessage("3.maintya3");
                    }
                    if (args[1].equals("homePageURL") || args[1].equals("homepageurl") || args[1].equals("homepageURL")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                        player.sendMessage(ChatColor.GREEN + "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/283");
                    }
                }
                if (args[0].equals("title")) {
                    player.sendTitle("SV", "This is Stefano Varentino", 5, 50, 35);
                }
                if (args[0].equals("tp")) {
                    if (args[1].equals("lobby")) {
                        player.sendMessage("lobbyにテレポートします");
                        ScoreBoardUtil.removeScoreboard(player);
                        player.teleport(Config.lobby);
                        athleticTimer.stopTimer(player);
                        player.setLevel(0);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        Config.playerList.remove(player.getName());
                    }
                    if (args[1].equals("athletic")) {
                        Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                        athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                        player.openInventory(athleticInventory);
                        player.addScoreboardTag("athletic");
                    }
                    if (args[1].equals("pvpMap") || args[1].equals("pvpmap")) {
                        player.sendMessage("pvpMapにテレポートします");
                        player.teleport(Config.pvpStart);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    }
                    if (args[1].equals("svinfo")) {
                        player.sendMessage("svInfoにテレポートします");
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
