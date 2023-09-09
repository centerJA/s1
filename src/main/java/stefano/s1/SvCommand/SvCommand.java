package stefano.s1.SvCommand;

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
                if (args[0].equals("command")) {
                    if (args[1].equals("list")) {
                        player.sendMessage("Stefano Varentino command list");
                        player.sendMessage(ChatColor.RED + "Color:RED" + ChatColor.WHITE + "は開発中のコマンド");
                        player.sendMessage("/sv command list       コマンドの一覧を表示します");
                        player.sendMessage("/sv tell origin        名前の由来を表示します");
                        player.sendMessage("/sv tell cooperative   このワールドの共同製作者を表示します");
                        player.sendMessage("/sv tell homePageURL   ホームページのURLを表示します");
                        player.sendMessage("/sv tp lobby           ロビーにテレポートします");
                        player.sendMessage(ChatColor.RED + "/sv tp athletic        アスレチックにテレポートします");
                        player.sendMessage(ChatColor.RED + "/sv tp pvpMap          pvpの時に使用するマップにテレポートします");
                        player.sendMessage("/sv tp svinfo          情報センターにテレポートします");
                    }
                }
                if (args[0].equals("tell")) {
                    if (args[1].equals("origin")) {
                        player.sendMessage("Stefano Varentinoの由来はハンカチです。");
                        player.sendMessage("ハンカチの名前から来てるとは思いませんね…");
                    }
                    if (args[1].equals("cooperative")) {
                        player.sendMessage("Cooperative Player List");
                        player.sendMessage("1.usikuzin");
                    }
                    if (args[1].equals("homePageURL")) {
//                        player.sendMessage(ChatColor.YELLOW + ">" + ChatColor.RED + ">" + ChatColor.DARK_RED + ">" + ChatColor.WHITE + "Click URL" + ChatColor.DARK_RED + "<" + ChatColor.RED + "<" + ChatColor.YELLOW + "<");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                        player.sendMessage(ChatColor.GREEN + "https://pretty-work-prod-ibldvcwaka-an.a.run.app/w/215");
                    }
                }
                if (args[0].equals("tp")) {
                    if (args[1].equals("lobby")) {
                        if (Config.playerList.contains(player.getName())) {
                            Config.playerList.remove(player.getName());
                        }
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
                        if (Config.playerList.contains(player.getName())) {
                            Config.playerList.remove(player.getName());
                        }
                        Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                        athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                        player.getPlayer().openInventory(athleticInventory);
                        player.addScoreboardTag("athletic");
                    }
                    if (args[1].equals("pvpMap")) {
                        if (!Config.playerList.contains(player.getName())) {
                            Config.playerList.add(player.getName());
                        }

                        player.sendMessage("pvpMapにテレポートします");
                        player.teleport(Config.pvpStart);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    }
                    if (args[1].equals("svinfo")) {
                        if (Config.playerList.contains(player.getName())) {
                            Config.playerList.remove(player.getName());
                        }
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

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e){
        for (String PlayerName: Config.playerList){
            Player player = Bukkit.getPlayer(PlayerName);
            World world = player.getWorld();
            if (this.world != world) return;
            if (player.getLocation().getX() > Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = xx1 - Config.Range;
                double xxx1 = xx1 - gap;
                Location playerlocation = new Location(world, xxx1, yy1, zz1, 90, 0);
                player.teleport(playerlocation);
            }
            if (player.getLocation().getZ() > Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = zz1 - Config.Range;
                double zzz1 = zz1 - gap;
                Location playerlocation = new Location(world, xx1, yy1, zzz1, -180, 0);
                player.teleport(playerlocation);
            }





            if (player.getLocation().getX() < -Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = -Config.Range - xx1;
                gap = -gap;
                double xxx1 = xx1 - gap;
                player.sendMessage(String.valueOf(gap));
                player.sendMessage(String.valueOf(xxx1));

                Location playerlocation = new Location(world, xxx1, yy1, zz1, -90, 0);
                player.teleport(playerlocation);
            }
            if (player.getLocation().getZ() < -Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = -Config.Range - zz1;
                gap = -gap;
                double zzz1 = zz1 - gap;
                Location playerlocation = new Location(world, xx1, yy1, zzz1, 0, 0);
                player.teleport(playerlocation);
            }
        }
    }
}
