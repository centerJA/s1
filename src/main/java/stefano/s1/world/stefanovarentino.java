package stefano.s1.world;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import stefano.Config;
import stefano.s1.S1;
import stefano.utils.AthleticTimer;
import stefano.utils.ItemUtil;
import stefano.utils.LimitTimer;
import stefano.utils.Timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static stefano.utils.AthleticTimer.AthleticTime;

public class stefanovarentino implements Listener {
    S1 plugin;
    World world;
    Location lobby, taikijyo, athletic, checkpoint1, athleticClear, athleticStart, pvpStart;

    ArrayList<String> playerList, deathPlayerList, athleticPlayerList;

    String stetasu = "taiki";
    Boolean Playing_Game = false;
    public FileConfiguration checkpointList;

    private BukkitTask athleticTimer;

    public stefanovarentino(S1 plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.world = Bukkit.getWorld("stefanovarentino");
        this.lobby = new Location(world, 25.500, 223, 25.500, -90, 0);
        this.taikijyo = new Location(world, 31.500, 240, 25.500, -90, 0);
        this.athletic = new Location(world, 50.500, 239, 25.500, -90, 0);
        this.checkpoint1 = new Location(world, 76.500, 244, 80.500, 0, 0);
        this.athleticClear = new Location(world, 16, 239, 41);
        this.athleticStart = new Location(world, 55, 239, 25);
        this.pvpStart = new Location(world, 25, 70, 24, -90, 0);
        this.playerList = new ArrayList<>();
        this.deathPlayerList = new ArrayList<>();
        this.athleticPlayerList = new ArrayList<>();
        this.checkpointList = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.setGameMode(GameMode.ADVENTURE);
                    }
                }, 20L);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.sendTitle(player.getName() + ChatColor.AQUA + "さん",ChatColor.AQUA + "こんにちは！", 20,40,20);
        player.sendMessage(ChatColor.AQUA + "-----Stefano Varentinoへようこそ!-----");
        player.sendMessage(ChatColor.YELLOW + "アスレチックやPVPなど、いくつかのゲームがあります");
        player.sendMessage(ChatColor.GREEN + "アスレチック…レッドストーンブロックをクリック!");
        player.sendMessage(ChatColor.GREEN + "ロビー…赤いきのこをクリック!");
        player.sendMessage(ChatColor.GREEN + "PVP…エメラルドをクリック!");
        player.sendMessage(ChatColor.AQUA + "------------------------------");
        player.teleport(this.lobby);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
        player.getInventory().addItem(ItemUtil.setItemMeta("PVP", Material.EMERALD));
        player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));


    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;

//        if (e.getItem() == null) return;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = e.getClickedBlock();
            if(block != null && block.getType() == Material.WALL_SIGN){
                Sign sign = (Sign)block.getState();
                String[] lines = sign.getLines();
                if(Objects.equals(lines[0], "tyekkupoinnto") || Objects.equals(lines[0], "チェックポイント")){
                    player.sendMessage(ChatColor.DARK_GREEN + "無事にチェックポイントを設定しました!");
                    checkpointList.set(String.valueOf(player.getUniqueId()), player.getLocation());
                }
            }
        }
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.STONE_PLATE) {
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticClear.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticClear.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticClear.getY())) {
                    player.sendMessage(ChatColor.YELLOW + "ゴールにつきました！おめでとうございます！");
                    athleticClear.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                    athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチッククリア！", 20,40,20);
                    player.sendMessage("あなたの記録は" + String.valueOf(AthleticTime - 1) + "でした！");
                    if (this.athleticTimer != null) {
                        this.athleticTimer.cancel();
                    }
                    player.setLevel(0);
                }
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticStart.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticStart.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticStart.getY())) {
                    player.sendMessage("アスレチックスタート！");
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチックスタート！", 20, 40, 20);
                    athleticStart.getWorld().playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 0, 10);
                    if (this.athleticTimer != null) {
                        this.athleticTimer.cancel();
                    }
                    this.athleticTimer = new AthleticTimer(player).runTaskTimer(this.plugin, 0L, 20L);
                    player.setLevel(0);
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack itemStack = e.getItem();
            if (itemStack.getType() == Material.RED_MUSHROOM) {

                player.teleport(this.lobby);
                player.getInventory().clear();
                player.getInventory().addItem(ItemUtil.setItemMeta("かくれんぼ", Material.EMERALD));
                player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
                player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                playerList.remove(player.getName());
            }
            if (itemStack.getType() == Material.EMERALD) {
                player.teleport(this.taikijyo);
                player.getInventory().clear();
                player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                if (!playerList.contains(player.getName())) {
                    playerList.add(player.getName());
                }
                player.sendMessage(ChatColor.AQUA + playerList.toString());
                if (this.playerList.size() > 1) {
                    float originalSpeed = player.getWalkSpeed();
                    new Timer(player).runTaskTimer(this.plugin, 0L, 20L);

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (String PlayerName: playerList){
                                Bukkit.getPlayer(PlayerName).teleport(pvpStart);
                                Bukkit.getPlayer(PlayerName).setGameMode(GameMode.ADVENTURE);
                                Bukkit.getPlayer(PlayerName).setFoodLevel(6);
                                Bukkit.getPlayer(PlayerName).setSprinting(false);
                                Bukkit.getPlayer(PlayerName).setNoDamageTicks(600);
                                Bukkit.getPlayer(PlayerName).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1));
                                Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.YELLOW + "範囲は、x=-125~125、z=-125~125です");
                                Bukkit.getPlayer(PlayerName).getInventory().clear();
                                ItemStack lobby = new ItemStack(Material.RED_MUSHROOM, 1);
                                ItemMeta lobbyMeta = lobby.getItemMeta();
                                lobbyMeta.setDisplayName("ロビーに戻る");
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(9, lobby);
                            }
                        }
                    }, 400L);
                    new LimitTimer().runTaskTimer(this.plugin, 0L, 20L);


                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (String PlayerName: playerList){
                                Bukkit.getPlayer(PlayerName).setFoodLevel(20);
                                Bukkit.getPlayer(PlayerName).setSprinting(true);
                                ItemStack pvpsword = new ItemStack(Material.IRON_SWORD, 1);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(0, pvpsword);
                                ItemStack pvpfood = new ItemStack(Material.COOKED_BEEF, 1);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(1, pvpfood);
                                ItemStack pvppotion1 = new ItemStack(Material.POTION, 1, (short) 14);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(2, pvppotion1);
                                ItemStack pvppotion2 = new ItemStack(Material.POTION, 1, (short) 10);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(3, pvppotion2);
                                ItemStack pvppotion3 = new ItemStack(Material.POTION, 1, (short) 6);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(4, pvppotion3);
                                ItemStack pvpbow = new ItemStack(Material.BOW, 1);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(5, pvpbow);
                                ItemStack pvparrow = new ItemStack(Material.ARROW, 64);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(34, pvparrow);
                                Bukkit.getPlayer(PlayerName).getInventory().setItem(35, pvparrow);


                            }
                        }
                    }, 600L);
                    player.sendTitle(ChatColor.AQUA + "",ChatColor.RED + "最後まで生き残れ！", 20,40,20);

                }
                else {
                    player.sendMessage(ChatColor.AQUA + "<かくれんぼ> 人数が足りません。最低2人が必要です。");
                }
            }
            if (itemStack.getType() == Material.REDSTONE_BLOCK) {
                String name = player.getDisplayName();
                athleticPlayerList.add(name);
                for (String PlayerName: athleticPlayerList){
                    player.teleport(this.athletic);
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る", Material.REDSTONE_BLOCK));
                    player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
                    player.sendMessage(ChatColor.AQUA + "チェックポイントの設定は、押した時にいた場所がチェックポイントに設定されます。");
                    player.sendMessage(ChatColor.AQUA + "チェックポイントの設定方法は、何も持っていない状態でチェックポイントと書いてある看板をクリックします。");
                    player.sendMessage(ChatColor.YELLOW + "速さ重視なら、もちろんチェックポイントを設定しなくてもok！");
                    player.sendMessage(ChatColor.YELLOW + "石の感圧板を踏んだらスタートするよ！");

                }

            }

            if (itemStack.getType() == Material.BOOK) {
                if (checkpointList.getString(String.valueOf(player.getUniqueId())) == null) {
                    player.sendMessage(ChatColor.RED + "チェックポイントが設定されていないよ!");
                    player.sendMessage(ChatColor.YELLOW + "何も持たずにチェックポイントと書いてある看板をクリックしてチェックポイントを設定！");
                }
                else {
                    player.teleport((Location) checkpointList.get(String.valueOf(player.getUniqueId())));
                }
            }



        }
    }
    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e){
        Player player = (Player) e.getEntity();
        World world = player.getWorld();
        if (this.world != world) return;
        e.setCancelled(true);

    }
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent e){
        for (String PlayerName: playerList){
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
                double xxx1 = xx1 + (-gap);
                Location playerlocation = new Location(world, xxx1, yy1, zz1, -90, 0);
                player.teleport(playerlocation);
            }
            if (player.getLocation().getZ() < -Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = -Config.Range - zz1;
                double zzz1 = zz1 + (-gap);
                Location playerlocation = new Location(world, xx1, yy1, zzz1, 0, 0);
                player.teleport(playerlocation);
            }

        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player player = e.getEntity();
        World world = player.getWorld();
        if (this.world != world) return;
        if (Playing_Game) {
            player.sendTitle("死んでしまった！", "", 20, 40, 20);
            player.sendMessage("赤いキノコをクリックしてロビーに戻る");
            player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));

        }
        if (!Playing_Game) {

        }

    }

}
