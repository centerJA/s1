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
                player.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                player.sendMessage("Stefano Varentino");
            }
            else {
                if (args[0].equals("help")) {
                    ComponentBuilder msg1 = new ComponentBuilder("・コマンドの一覧を表示するには、ここをクリックします");
                    ComponentBuilder msg2 = new ComponentBuilder("・ホームページにアクセスするには、ここをクリックします");
                    BaseComponent[] message1 = msg1.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sv tell command list")).create();
                    BaseComponent[] message2 = msg2.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/215")).create();
                    player.sendMessage("ヘルプが必要ですか?");
                    player.spigot().sendMessage(message1);
                    player.spigot().sendMessage(message2);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITCH_DEATH, 2, 10);
                }
                if (args[0].equals("tell")) {
                    if (args[1].equals("origin")) {
                        player.sendMessage(ChatColor.WHITE + "Stefano Varentinoの由来は" + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "です。");
                        player.sendMessage("ハンカチの名前から来てるとは思いませんね…");
                    }
                    if (args[1].equals("cooperative")) {
                        player.sendMessage("Cooperative Player List");
                        player.sendMessage("1.usikuzin");
                        player.sendMessage("2.SleetyNote17020");
                        player.sendMessage("3.maintya3");
                    }
                    if (args[1].equals("homePageURL")) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                        player.sendMessage(ChatColor.GREEN + "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/215");
                    }
                    if (args[1].equals("command")) {
                        if (args[2].equals("list")) {
                            player.sendMessage(ChatColor.AQUA + "----------Stefano Varentino command list----------");
                            player.sendMessage(ChatColor.RED + "Color:RED" + ChatColor.WHITE + "は開発中のコマンドです");
                            player.sendMessage("開発中のコマンドは使えない場合があります");
                            player.sendMessage("/sv tell command list       コマンドの一覧を表示します");
                            player.sendMessage("/sv tell origin       名前の由来を表示します");
                            player.sendMessage("/sv tell cooperative       このワールドの共同製作者を表示します");
                            player.sendMessage(ChatColor.RED + "/sv tell homePageURL       ホームページのURLを表示します");
                            player.sendMessage("/sv tp lobby       ロビーにテレポートします");
                            player.sendMessage("/sv tp athletic       アスレチックにテレポートします");
                            player.sendMessage("/sv tp pvpMap       pvpの時に使用するマップにテレポートします");
                            player.sendMessage("/sv tp svinfo       情報センターにテレポートします");
                            player.sendMessage(ChatColor.RED + "/sv help       ヘルプを表示します");
                            player.sendMessage(ChatColor.AQUA + "-------------------------------------------------");
                        }
                    }
                }
                if (args[0].equals("tp")) {
                    if (args[1].equals("lobby")) {
                        player.sendMessage("lobbyにテレポートします");
                        ScoreBoardUtil.removeScoreboard(player);
                        player.teleport(Config.lobby);
                        athleticTimer.stopTimer(player);
                        player.setLevel(0);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("pvp", Material.EMERALD));
                        player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        Config.playerList.remove(player.getName());
                    }
                    if (args[1].equals("athletic")) {
                        Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                        athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                        player.getPlayer().openInventory(athleticInventory);
                        player.addScoreboardTag("athletic");
                    }
                    if (args[1].equals("pvpMap")) {
                        player.sendMessage("pvpMapにテレポートします");
                        player.teleport(Config.pvpStart);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                        player.sendMessage(String.valueOf(Config.playerList));
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
