package stefano.s1.SvCommand;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitTask;
import stefano.s1.Config;
import stefano.s1.S1;
import stefano.s1.utils.AthleticTimer;
import stefano.s1.utils.ItemUtil;


public class SvCommand implements CommandExecutor {
    private static final S1 plugin = S1.getPlugin(S1.class);
    private AthleticTimer athleticTimer;

    public SvCommand(AthleticTimer athleticTimer) {
        this.athleticTimer = athleticTimer;
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
                        player.sendMessage("/sv command list…コマンドの一覧を表示します");
                        player.sendMessage("/sv tell origin…名前の由来を表示します");
                        player.sendMessage("/sv tell cooperative…このワールドの共同製作者を表示します");
                        player.sendMessage("/sv tp lobby…ロビーにテレポートします");
                        player.sendMessage("/sv tp athletic…アスレチックにテレポートします");
                    }
                }
                if (args[0].equals("tell")) {
                    if (args[1].equals("origin")) {
                        player.sendMessage("Stefano Varentinoの由来はハンカチです。");
                        player.sendMessage("ハンカチの名前から来てるとは思いませんね…");
                    }
                    if (args[1].equals("cooperative")) {
                        player.sendMessage("Cooperative Player");
                        player.sendMessage("1.usikuzin");
                    }
                }
                if (args[0].equals("tp")) {
                    if (args[1].equals("lobby")) {
                        player.sendMessage("lobbyにテレポートします");
                        player.teleport(Config.lobby);
                        athleticTimer.stopTimer(player);
                        AthleticTimer.getTaskId(player).cancel();
//                        if (athleticTimer != null) {
//                            BukkitTask athleticTimer1 = new AthleticTimer(player).runTaskTimer(plugin, 0L, 20L);
//                            athleticTimer1.cancel();
//                            athleticTimer.stopStopwatch();
//                            athleticTimer = null;
//                        }
//                        player.sendMessage(Config.checkpointList.get(0));
//                        for (int i=0; i<=Config.checkpointList.size(); i++) {
//
//                        }
//                        if (Config.checkpointList.!= null) {
//                            Config.checkpointList.set(String.valueOf(player.getUniqueId()), null);
//                        }
                        player.setLevel(0);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("pvp", Material.EMERALD));
                        player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        Config.playerList.remove(player.getName());
                    }
                    if (args[1].equals("athletic")) {
                        player.sendMessage("athleticにテレポートします");
                        player.teleport(Config.athletic);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                        player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る", Material.APPLE));
                        player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
                        player.sendMessage(ChatColor.AQUA + "チェックポイントの設定は、押した時にいた場所がチェックポイントに設定されます。");
                        player.sendMessage(ChatColor.AQUA + "チェックポイントの設定方法は、何も持っていない状態でチェックポイントと書いてある看板をクリックします。");
                        player.sendMessage(ChatColor.YELLOW + "速さ重視なら、もちろんチェックポイントを設定しなくてもok！");
                        player.sendMessage(ChatColor.YELLOW + "石の感圧板を踏んだらスタートするよ！");
                    }
                }
            }

        }
        return false;
    }
}
