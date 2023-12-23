package stefano.s1.world;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.Config;
import stefano.s1.S1;
import stefano.s1.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;


public class stefanovarentino implements Listener {
    S1 plugin;
    World world;
    Location lobby, taikijyo, athletic1, checkpoint1, athleticClear, athleticStart, pvpStart, pvpFinal, taikijyof;

    public  ArrayList<String> playerList, deathPlayerList, athleticPlayerList;

    String stetasu = "taiki";

    private int tof;

    public int flag;
    public Boolean Playing_Game = false;
    public FileConfiguration checkpointList;
    File PlayerAthleticTime;
    BukkitTask Timer;
    AthleticTimer athleticTimer;


    public stefanovarentino(S1 plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.world = Bukkit.getWorld("stefanovarentino");
        this.lobby = new Location(world, 25.500, 223, 25.500, -90, 0);
        this.taikijyo = new Location(world, 31.500, 240, 25.500, -90, 0);
        this.athletic1 = new Location(world, 50.500, 239, 25.500, -90, 0);
        this.checkpoint1 = new Location(world, 76.500, 244, 80.500, 0, 0);
        this.pvpFinal = new Location(world, 15.999, 248.500, 17.999, -90, 0);
        this.taikijyof = new Location(world, 41.500, 247, 25.500);
        this.athleticClear = new Location(world, 16, 239, 41);
        this.athleticStart = new Location(world, 55, 239, 25);
        this.pvpStart = new Location(world, 25, 70, 24, -90, 0);
        this.playerList = new ArrayList<>();
        this.deathPlayerList = new ArrayList<>();
        this.athleticPlayerList = new ArrayList<>();
        this.checkpointList = plugin.getConfig();
        this.athleticTimer = new AthleticTimer();
        this.tof = 0;
        this.flag = 0;
    }

    public static void removePlayerList(Player player, ArrayList playerList) {
        playerList.remove(player.getName());
    }

    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) {
            ScoreBoardUtil.removeScoreboard(player);
            AthleticTimer.stopTimer(player);
            e.getPlayer().setLevel(0);
            player.getInventory().clear();

            return;
        }
        if (playerList.contains(player.getName())) {
            removePlayerList(player, playerList);
        }
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.setGameMode(GameMode.ADVENTURE);
            }
        }, 20L);
        player.setFoodLevel(20);
        player.setHealth(20);
        player.sendTitle(player.getName() + ChatColor.AQUA + "さん", ChatColor.AQUA + "こんにちは！", 20, 40, 20);
        player.sendMessage(ChatColor.AQUA + "-----Stefano Varentinoへようこそ!-----");
        player.sendMessage(ChatColor.YELLOW + "アスレチックやPVPなど、いくつかのゲームがあります");
        player.sendMessage(ChatColor.GREEN + "レッドストーンブロック…アスレチックがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "エメラルド…PVPがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "赤いきのこ…ロビーの中心に戻ります。");
        player.sendMessage(ChatColor.GREEN + "/sv tell command list…コマンド一覧を表示します。");
        player.sendMessage(ChatColor.AQUA + "------------------------------");
        player.teleport(this.lobby);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
        player.getInventory().addItem(ItemUtil.setItemMeta("PVP", Material.EMERALD));
        player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
        player.getInventory().addItem(ItemUtil.setItemMeta("Bedwars", Material.BED));



    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET);
        ItemStack ironChestPlate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
        ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS);
        if (this.world != world) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            if (block != null && block.getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
                if (Objects.equals(lines[0], "tyekkupoinnto") || Objects.equals(lines[0], "チェックポイント")) {
                    player.sendMessage(ChatColor.DARK_GREEN + "無事にチェックポイントを設定しました!");
                    checkpointList.set(String.valueOf(player.getUniqueId()), player.getLocation());
                    player.getInventory().setHeldItemSlot(3);
                }
                if (Objects.equals(lines[0], "アスレの王者")) {
                    player.getWorld().playEffect(player.getLocation(), Effect.WITHER_SHOOT, 0, 1);
                }
                if (Objects.equals(lines[0], "stefanovarentino")) {
                    athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 1);
                }
                if (Objects.equals(lines[0], "この世界の名前は")) {
                    player.sendMessage("答えはなんと...." + ChatColor.AQUA + "ハンカチ" + ChatColor.WHITE + "!");
                }
                if (Objects.equals(lines[0], "この看板をクリックし") || Objects.equals(lines[0], "opathletic")) {
                    if (Objects.equals(lines[1], "てタイムをリセット") || Objects.equals(lines[1], "remove")) {
                        player.sendMessage(ChatColor.AQUA + "自分のタイムをリセットします。");
                        PlayerScore.removePlayerTime(player, this.plugin);
                        ScoreBoardUtil.updateRanking(player);
                    }
                }
                if (Objects.equals(lines[0], "9832h39n")) {
                    player.sendMessage(ChatColor.AQUA + "全員のタイムをリセットします。");
                    PlayerScore.removePlayerTimeAll(player, this.plugin);
                    ScoreBoardUtil.updateRanking(player);
                }
            }
        }
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.STONE_PLATE) {
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticClear.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticClear.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticClear.getY())) {
                    if (player.getLevel() == 0) {
                        player.sendMessage(ChatColor.AQUA + "あなたのタイムは現在0です!");
                        player.sendMessage(ChatColor.AQUA + "もう一度アスレチックに挑戦してみましょう!");
                        return;
                    }
                    player.sendMessage(ChatColor.YELLOW + "ゴールにつきました！おめでとうございます！");
                    athleticClear.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                    athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチッククリア！", 20, 40, 20);
                    athleticTimer.stopTimer(player);
                    player.sendMessage("あなたの記録は" + player.getLevel() + "でした！");
                    PlayerScore.setPlayerTime(player, player.getLevel(), this.plugin);
                    ScoreBoardUtil.updateRanking(player);
                    player.setLevel(0);
                }
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticStart.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticStart.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticStart.getY())) {
                    player.sendMessage("アスレチックスタート！");
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチックスタート！", 20, 40, 20);
                    athleticStart.getWorld().playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 0, 10);
                    athleticTimer.startTimer(player);
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null) {
                ItemStack itemStack = e.getItem();
                String playerName = player.getName();
                if (itemStack.getType() == Material.BED) {
                    player.sendMessage("現在開発中です!");
                    player.sendMessage("まだアクセスすることができません!");
                    return;
//                    player.teleport(taikijyo);
//                    this.Timer = new Timer(playerList).runTaskTimer(this.plugin, 0L, 20L);

                }
                if (itemStack.getType() == Material.RED_MUSHROOM) {
                    if (athleticTimer != null) {
                        athleticTimer.stopTimer(player);
                    }
                    if (playerList.contains(player.getName())) {
                        playerList.remove(player.getName());
                        stefano.s1.utils.Timer.stopCountDownTimer();
                        if (tof == 0) {
                         e.getPlayer().sendMessage("pvpをキャンセルしました。");
                        }
                        if (playerList.size() > 0) {
                            for (String PlayerName: playerList) {
                                Bukkit.getPlayer(PlayerName).sendMessage("pvpがキャンセルされました。");
                            }
                        }
                    }
                    player.teleport(this.lobby);
                    ScoreBoardUtil.removeScoreboard(player);
                    if (this.checkpointList.getString(String.valueOf(player.getUniqueId())) != null) {
                        checkpointList.set(String.valueOf(player.getUniqueId()), null);
                    }
                    player.setLevel(0);
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("pvp", Material.EMERALD));
                    player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                    playerList.remove(player.getName());
                }
                if (itemStack.getType() == Material.APPLE) {
                    if (itemStack.getItemMeta().getDisplayName().equals("最初に戻る(athletic1)")) {
                        player.teleport(this.athletic1);
                    }
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る(athletic1)", Material.APPLE));
                    player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
                    if (this.checkpointList.getString(String.valueOf(player.getUniqueId())) != null) {
                        checkpointList.set(String.valueOf(player.getUniqueId()), null);
                    }
                    if (athleticTimer != null) {
                        athleticTimer.stopTimer(player);
                    }
                }
                if (itemStack.getType() == Material.EMERALD) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            flag = 0;
                        }
                    }, 40L);

                    if (flag == 0) {
                        this.flag = 1;
                        player.teleport(this.taikijyo);
                        player.getInventory().clear();
                        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                        if (!playerList.contains(player.getName())) {
                            playerList.add(player.getName());
                        }
                        player.sendMessage(ChatColor.YELLOW + playerList.toString());
                        pvpUtil.blockLocationAllRemove();
                        if (this.playerList.size() == 2) {
                            player.sendMessage("すでに1人が参加しているので、開始します。");
                            tof = 0;
                            this.Timer = new Timer(playerList).runTaskTimer(this.plugin, 0L, 20L);
                            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Playing_Game = true;
                                    for (String PlayerName : playerList) {
                                        if (playerList.size() < 2) {
                                            LimitTimer.stopTimer();
                                            break;
                                        }
                                        Bukkit.getPlayer(PlayerName).teleport(pvpStart);
                                        Bukkit.getPlayer(PlayerName).setGameMode(GameMode.SURVIVAL);
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
                            new LimitTimer(playerList, pvpFinal).runTaskTimer(this.plugin, 0L, 20L);


                            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    for (String PlayerName : playerList) {
                                        if (playerList.size() < 2) {
                                            break;
                                        }
                                        Bukkit.getPlayer(PlayerName).setFoodLevel(20);
                                        Bukkit.getPlayer(PlayerName).setSprinting(true);
                                        ItemStack pvpsword = new ItemStack(Material.IRON_SWORD, 1);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(0, pvpsword);
                                        ItemStack pvpfood = new ItemStack(Material.COOKED_BEEF, 64);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(1, pvpfood);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(2, ItemUtil.setCustomPotionMeta(PotionEffectType.INVISIBILITY, Material.SPLASH_POTION, "透明化"));
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(3, ItemUtil.setCustomPotionMeta(PotionEffectType.REGENERATION, Material.SPLASH_POTION, "再生する"));
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(4, ItemUtil.setCustomPotionMeta(PotionEffectType.POISON, Material.LINGERING_POTION, "毒"));
                                        ItemStack pvpbow = new ItemStack(Material.BOW, 1);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(5, pvpbow);
                                        ItemStack pvparrow = new ItemStack(Material.ARROW, 64);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(34, pvparrow);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(35, pvparrow);
                                        Bukkit.getPlayer(PlayerName).getInventory().setHelmet(ironHelmet);
                                        Bukkit.getPlayer(PlayerName).getInventory().setChestplate(ironChestPlate);
                                        Bukkit.getPlayer(PlayerName).getInventory().setBoots(ironBoots);
                                        Bukkit.getPlayer(PlayerName).getInventory().setLeggings(ironLeggings);
                                        ItemStack pvpBlock = new ItemStack(Material.STONE, 64);
                                        Bukkit.getPlayer(PlayerName).getInventory().setItem(6, pvpBlock);
                                        Bukkit.getPlayer(PlayerName).sendTitle(ChatColor.AQUA + "", ChatColor.RED + "最後まで生き残れ！", 20, 40, 20);
                                    }
                                }
                            }, 900);
                        } else {
                            player.sendMessage(ChatColor.AQUA + "人数が足りません。最低2人が必要です。");
                            player.sendMessage(ChatColor.AQUA + "現在1人です。");
                        }
                    } else {
                        player.sendMessage("少しお待ちください!");
                    }
                }


                if (itemStack.getType() == Material.REDSTONE_BLOCK) {
                    String name = player.getDisplayName();
                    athleticPlayerList.add(name);
                    if (this.checkpointList.getString(String.valueOf(player.getUniqueId())) != null) {
                        checkpointList.set(String.valueOf(player.getUniqueId()), null);
                    }
                    Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                    athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                    player.getPlayer().openInventory(athleticInventory);
                    player.addScoreboardTag("athletic");
                }
                if (itemStack.getType() == Material.BOOK) {
                    if (checkpointList.getString(String.valueOf(player.getUniqueId())) == null) {
                        player.sendMessage(ChatColor.RED + "チェックポイントが設定されていないよ!");
                        player.sendMessage(ChatColor.YELLOW + "何も持たずにチェックポイントと書いてある看板をクリックしてチェックポイントを設定！");
                    } else {
                        player.teleport((Location) checkpointList.get(String.valueOf(player.getUniqueId())));
                    }
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
            Location playerNewLocation = player.getLocation();
            if (playerNewLocation == null) return;
            if (this.world != world) return;
            if (player.getLocation().getX() > Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = xx1 - Config.Range;
                double xxx1 = xx1 - gap;
                Location playerLocation = new Location(world, xxx1, yy1, zz1, 90, 0);
                player.teleport(playerLocation);
            }
            if (player.getLocation().getZ() > Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = zz1 - Config.Range;
                double zzz1 = zz1 - gap;
                Location playerLocation = new Location(world, xx1, yy1, zzz1, -180, 0);
                player.teleport(playerLocation);
            }





            if (player.getLocation().getX() < -Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = -Config.Range - xx1;
                gap = -gap;
                double xxx1 = xx1 - gap;
                Location playerLocation = new Location(world, xxx1, yy1, zz1, -90, 0);
                player.teleport(playerLocation);
            }
            if (player.getLocation().getZ() < -Config.Range){
                double xx1 = player.getLocation().getX();
                double yy1 = player.getLocation().getY();
                double zz1 = player.getLocation().getZ();
                double gap = -Config.Range - zz1;
                gap = -gap;
                double zzz1 = zz1 - gap;
                Location playerLocation = new Location(world, xx1, yy1, zzz1, 0, 0);
                player.teleport(playerLocation);
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        Player player = e.getEntity();
        World world = player.getWorld();
        if (this.world != world) return;
        if (Playing_Game) {
            playerList.remove(player.getName());
            if (playerList.size() == 1) {
                pvpUtil.blockBreak();
                LimitTimer.stopTimer();
                tof = 1;
                for (String PlayerName : playerList) {
                    Bukkit.getPlayer(PlayerName).sendMessage("pvp終了!");
                    Bukkit.getPlayer(PlayerName).sendMessage(String.valueOf(PlayerName));
                    Bukkit.getPlayer(PlayerName).sendMessage(ChatColor.GOLD + "勝ち！");
                    Bukkit.getPlayer(PlayerName).sendTitle(ChatColor.MAGIC + "", ChatColor.DARK_PURPLE + "勝ち！", 20, 40, 40);
                    Bukkit.getPlayer(PlayerName).sendMessage("赤いキノコをクリックしてロビーに戻る");
                    Bukkit.getPlayer(PlayerName).getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    Bukkit.getPlayer(PlayerName).getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                }
            }
        }


    }


    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(lobby);
            }
        }, 10L);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("pvp", Material.EMERALD));
        player.getInventory().addItem(ItemUtil.setItemMeta("アスレチック", Material.REDSTONE_BLOCK));
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
        if (this.playerList.size() != 0) {
            playerList.remove(player.getName());
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        String msg = e.getMessage();
        if (this.world != world) return;
        if (msg.equals("SV")) {
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_SHOOT, 1, 4);
        }

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        HumanEntity human = e.getWhoClicked();
        Player player = Bukkit.getPlayer(human.getName());
        World world = player.getWorld();
        ItemStack itemStack = e.getCurrentItem();
        Set<String> userTag = human.getScoreboardTags();
        if (this.world != world) return;
        if (userTag.contains("athletic")) {
            e.setCancelled(true);
            if (itemStack.getType() != null) {
                if (itemStack.getType() == Material.PAPER && itemStack.getItemMeta().getDisplayName().equals("シンプル")) {
                    player.teleport(this.athletic1);
                    ScoreBoardUtil.updateRanking(player);
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    player.getInventory().addItem(ItemUtil.setItemMeta("最初に戻る(athletic1)", Material.APPLE));
                    player.getInventory().addItem(ItemUtil.setItemMeta("チェックポイントに戻る", Material.BOOK));
                    player.sendMessage(ChatColor.AQUA + "チェックポイントの設定は、押した時にいた場所がチェックポイントに設定されます。");
                    player.sendMessage(ChatColor.AQUA + "チェックポイントの設定方法は、何も持っていない状態でチェックポイントと書いてある看板をクリックします。");
                    player.sendMessage(ChatColor.YELLOW + "速さ重視なら、もちろんチェックポイントを設定しなくてもok！");
                    player.sendMessage(ChatColor.YELLOW + "石の感圧板を踏んだらスタートするよ！");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        HumanEntity human = e.getPlayer();
        Player player = Bukkit.getPlayer(human.getName());
        World world = player.getWorld();
        Inventory inventory = e.getInventory();
        ItemStack[] contents = inventory.getContents();
        PlayerInventory playerInventory = player.getInventory();
        boolean isEmpty = true;
        Set<String> userTag = e.getPlayer().getScoreboardTags();
        if (this.world != world) return;

        if (userTag.contains("athletic")) {
            e.getPlayer().removeScoreboardTag("athletic");
        }
        for (ItemStack item: contents) {
            if (item != null && !item.getType().isBlock()) {
                isEmpty = false;
                break;
            }
        }

        if (isEmpty && inventory.getType().name().equals("CHEST")) {
            e.getInventory().getLocation().getBlock().setType(Material.AIR);
            for (String PlayerName: playerList) {
                Bukkit.getPlayer(PlayerName).sendMessage("チェストが空になったため消滅しました！");
            }
        }
    }
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        String command = e.getMessage();
        String[] args = command.split(" ");
        if (args.length > 2) {
            String commandContents = args[2];
            if (commandContents.equalsIgnoreCase("pvpMap")) {
                if (!playerList.contains(player.getName())) {
                    playerList.add(player.getName());
                }
            }
            if (commandContents.equalsIgnoreCase("creative")) {
                if (!e.getPlayer().getName().equals("markcs11")) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            e.getPlayer().setGameMode(GameMode.ADVENTURE);
                            e.getPlayer().sendMessage("権限がありません!");
                        }
                    }, 27L);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        player.sendMessage("aaaaa");
        if (Playing_Game) {
            player.sendMessage("u8w9e83e39eu93id");
            for (String PlayerName: playerList) {
                player.sendMessage("111111111");
                if (!playerList.contains(player.getName())) {
                    player.sendMessage("82828218u4");
                    return;
                }
            }
            player.sendMessage("93399u1343782432472347238347324278");
            Location location = e.getBlock().getLocation();
            pvpUtil.blockLocation(location);
            player.sendMessage("iwuhuialksmdlsajdjssjdajdjwad");
        }

        else {
            player.sendMessage("プレイイングじゃありません");
        }
    }
}
