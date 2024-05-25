package stefano.s1.world;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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

    public static ArrayList<String> knockBackPlayerList, cannnotDamageList;

    String knockBackWhichCan = "true";

    public int flag, bedwarsFlag;
    public Boolean Playing_Game = false;
    public FileConfiguration checkpointList;
    BukkitTask Timer, BedwarsTimer;
    AthleticTimer athleticTimer;



    public stefanovarentino(S1 plugin) {
        Bukkit.getLogger().info("stefanovarentino");
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
        this.cannnotDamageList = new ArrayList<>();
        //this.checkpointList = plugin.getConfig();
        this.checkpointList = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getParent(), "checkpoint.yml"));
        this.athleticTimer = new AthleticTimer();
        this.flag = 0;
        this.bedwarsFlag = 0;
        this.knockBackPlayerList = new ArrayList<>();
        Bukkit.getLogger().info("finishStefanovarentino");
    }




    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        Bukkit.getLogger().info((player.getName()));
        World world = player.getWorld();
        Bukkit.getLogger().info(world.getName());
        System.out.println("STEFANOVARENTINO--------");


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
        player.sendMessage(ChatColor.YELLOW + "アスレチックやPVPなど、いくつかのゲームがあります!");
        player.sendMessage(ChatColor.YELLOW + "前にある金床をクリックしてゲームをプレイすることができます。");
        player.sendMessage(ChatColor.GREEN + "レッドストーンブロック…アスレチックがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "エメラルド…PVPがプレイできます。");
        player.sendMessage(ChatColor.GREEN + "赤いきのこ…ロビーの中心に戻ります。");
        player.sendMessage(ChatColor.GREEN + "/sv…コマンド一覧を表示します。");
        player.sendMessage(ChatColor.AQUA + "------------------------------");
        player.teleport(this.lobby);
        player.getInventory().clear();
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));



    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            if (block != null && block.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
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
                        player.sendMessage(ChatColor.AQUA + "自分のタイムをリセットします。");
                        PlayerScore.removePlayerTime(player, this.plugin);
                        ScoreBoardUtil.updateRanking(player);
                    }
                }
            } else if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                player.setHealth(20);
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
        }
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (Objects.requireNonNull(e.getClickedBlock()).getType() == Material.STONE_PRESSURE_PLATE) {
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticClear.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticClear.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticClear.getY())) {
                    if (player.getLevel() == 0) {
                        player.sendMessage(ChatColor.AQUA + "あなたのタイムは現在0です!");
                        player.sendMessage(ChatColor.AQUA + "もう一度アスレチックに挑戦してみましょう!");
                        return;
                    }
                    if (athleticClear.getWorld() == null) return;
                    player.sendMessage(ChatColor.YELLOW + "ゴールにつきました！おめでとうございます！");
                    athleticClear.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                    athleticClear.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチッククリア！", 20, 40, 20);
                    AthleticTimer.stopTimer(player);
                    player.sendMessage("あなたの記録は" + player.getLevel() + "でした！");
                    PlayerScore.setPlayerTime(player, player.getLevel(), this.plugin);
                    ScoreBoardUtil.updateRanking(player);
                    player.setLevel(0);
                }
                if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticStart.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticStart.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticStart.getY())) {
                    if (athleticStart.getWorld() == null) return;
                    player.sendMessage("アスレチックスタート！");
                    player.sendTitle(ChatColor.AQUA + "", ChatColor.AQUA + "アスレチックスタート！", 20, 40, 20);
                    athleticStart.getWorld().playEffect(player.getLocation(), Effect.PORTAL_TRAVEL, 0, 10);
                    athleticTimer.startTimer(player);
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null) {
//                if (CoolDownUtil.cooldown(e.getPlayer(), plugin) == 1) {
//                    return;
//                }
                ItemStack itemStack = e.getItem();
                if (itemStack.getType() == Material.RED_MUSHROOM) {
                    knockBackWhichCan = "true";
                    player.setHealth(20);
                    if (athleticTimer != null) {
                        AthleticTimer.stopTimer(player);
                    }
                    if (playerList.contains(player.getName())) {
                        playerList.remove(player.getName());
                        stefano.s1.utils.Timer.stopCountDownTimer();
                        e.getPlayer().sendMessage("pvpをキャンセルしました。");
                        textDisplayUtil.removePvpColumnText(world);
                        boolean visible = false;
                        textDisplayUtil.removePvpColumnText(world);
                        textDisplayUtil.showPvpIsStopping(Config.textLocationPvpColumn, Config.pvpIsStopping, visible);
                        if (!(Timer == null)) {
                            Timer.cancel();
                        }
                        if (playerList.size() > 0) {
                            for (String PlayerName: playerList) {
                                Player player2 = Bukkit.getPlayer(PlayerName);
                                if (player2 == null) return;
                                player2.sendMessage("pvpがキャンセルされました。");
                            }
                        }
                    }

                    if (knockBackPlayerList.contains(player.getName())) {
                        knockBackPlayerList.remove(player.getName());
                        knockBackTimerUtil.stopBedwarsCountDownTimer();
                        boolean visible = false;
                        textDisplayUtil.removeKnockBackColumnText(world);
                        textDisplayUtil.showKnockBackIsStopping(Config.textLocationKnockBackColumn, Config.knockBackIsStopping, visible);
                        e.getPlayer().sendMessage("knockbackをキャンセルしました。");
                        e.getPlayer().teleport(lobby);
                        e.getPlayer().getInventory().clear();
                        e.getPlayer().getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        knockBackWhichCan = "true";
                    }
                    if (knockBackPlayerList.size() == 1) {
                        knockBackWhichCan = "true";
                        for (String PlayerName: knockBackPlayerList) {
                            Player player2 = Bukkit.getPlayer(PlayerName);
                            if (player2 == null) return;
                        }
                    }
                    player.teleport(this.lobby);
                    ScoreBoardUtil.removeScoreboard(player);
                    if (this.checkpointList.getString(String.valueOf(player.getUniqueId())) != null) {
                        checkpointList.set(String.valueOf(player.getUniqueId()), null);
                    }
                    player.setLevel(0);
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                    playerList.remove(player.getName());
                }



                if (itemStack.getType() == Material.APPLE) {
                    if (itemStack.getItemMeta() == null) return;
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
                        AthleticTimer.stopTimer(player);
                    }
                }

                if (itemStack.getType() == Material.BOOK) {
                    if (checkpointList.getString(String.valueOf(player.getUniqueId())) == null) {
                        player.sendMessage(ChatColor.RED + "チェックポイントが設定されていないよ!");
                        player.sendMessage(ChatColor.YELLOW + "何も持たずにチェックポイントと書いてある看板をクリックしてチェックポイントを設定！");
                    } else {
                        if (checkpointList.get(String.valueOf(player.getUniqueId())) == null) return;
                        if (!(checkpointList.contains(String.valueOf(player.getUniqueId())))) return;
                        //null
                        player.teleport((Location) Objects.requireNonNull(checkpointList.get(String.valueOf(player.getUniqueId()))));
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
        Player player = e.getPlayer();
        for (String PlayerName: playerList){
            Player playerPvp = Bukkit.getPlayer(PlayerName);
            if (playerPvp == null) return;
            World world = playerPvp.getWorld();
            if (this.world != world) return;
            if (playerPvp.getLocation().getX() > Config.Range){
                double xx1 = playerPvp.getLocation().getX();
                double yy1 = playerPvp.getLocation().getY();
                double zz1 = playerPvp.getLocation().getZ();
                double gap = xx1 - Config.Range;
                double xxx1 = xx1 - gap;
                Location playerLocation = new Location(world, xxx1, yy1, zz1, 90, 0);
                playerPvp.teleport(playerLocation);
                return;
            }
            else if (playerPvp.getLocation().getZ() > Config.Range){
                double xx1 = playerPvp.getLocation().getX();
                double yy1 = playerPvp.getLocation().getY();
                double zz1 = playerPvp.getLocation().getZ();
                double gap = zz1 - Config.Range;
                double zzz1 = zz1 - gap;
                Location playerLocation = new Location(world, xx1, yy1, zzz1, -180, 0);
                playerPvp.teleport(playerLocation);
                return;
            }





            else if (playerPvp.getLocation().getX() < -Config.Range){
                double xx1 = playerPvp.getLocation().getX();
                double yy1 = playerPvp.getLocation().getY();
                double zz1 = playerPvp.getLocation().getZ();
                double gap = -Config.Range - xx1;
                gap = -gap;
                double xxx1 = xx1 - gap;
                Location playerLocation = new Location(world, xxx1, yy1, zz1, -90, 0);
                playerPvp.teleport(playerLocation);
                return;
            }
            else if (playerPvp.getLocation().getZ() < -Config.Range){
                double xx1 = playerPvp.getLocation().getX();
                double yy1 = playerPvp.getLocation().getY();
                double zz1 = playerPvp.getLocation().getZ();
                double gap = -Config.Range - zz1;
                gap = -gap;
                double zzz1 = zz1 - gap;
                Location playerLocation = new Location(world, xx1, yy1, zzz1, 0, 0);
                playerPvp.teleport(playerLocation);
                return;
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
                for (String PlayerName : playerList) {
                    Player player2 = Bukkit.getPlayer(PlayerName);
                    if (player2 == null) return;
                    player2.sendMessage("pvp終了!");
                    player2.sendMessage(PlayerName);
                    player2.sendMessage(ChatColor.GOLD + "勝ち！");
                    player2.sendTitle(ChatColor.MAGIC + "", ChatColor.DARK_PURPLE + "勝ち！", 20, 40, 40);
                    player2.sendMessage("赤いキノコをクリックしてロビーに戻る");
                    player2.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    player2.getWorld().playEffect(player.getLocation(), Effect.DRAGON_BREATH, 0, 2);
                }
            }
        } else {
            if (knockBackPlayerList.contains(player.getName())) {
            player.sendTitle(ChatColor.RED + "敗北...", "", 20, 40 , 20);
            player.sendMessage("赤いキノコをクリックしてロビーに戻る");
            player.getInventory().clear();
            player.getInventory().setItem(0, ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
            knockBackPlayerList.remove(player.getName());
            String winner = knockBackPlayerList.get(0);
            knockBackUtil.sendWinMsg(winner, knockBackPlayerList);
            knockBackPlayerList.clear();
            knockBackUtil.knockBackBlockCrear();
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
        player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
        if (this.playerList.size() != 0) {
            playerList.remove(player.getName());
        }
        if (knockBackPlayerList.contains(player.getName())) {
            knockBackPlayerList.remove(player.getName());
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        String msg = e.getMessage();
        if (this.world != world) return;
        if (msg.equals("SV")) {
            player.playSound(player.getLocation(), Sound.ENTITY_WITCH_DEATH, 1, 4);
        }

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        World world = player.getWorld();
        ItemStack itemStack = e.getCurrentItem();
        Set<String> userTag = player.getScoreboardTags();
        boolean visible = false;
        if (this.world != world) return;
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        if (userTag.contains("game")) {
            if (flag == 1) {
                player.sendMessage("クールダウン中です！お待ちください！");
                e.setCancelled(true);
            }
            //athletic--------------------------------------------------------------------------------------------
            if (itemStack.getType() == Material.REDSTONE_BLOCK && itemStack.getItemMeta().getDisplayName().equals("アスレチック")) {
                player.setHealth(20);
                String name = player.getDisplayName();
                athleticPlayerList.add(name);
                if (this.checkpointList.getString(String.valueOf(player.getUniqueId())) != null) {
                    checkpointList.set(String.valueOf(player.getUniqueId()), null);
                }
                Inventory athleticInventory = Bukkit.createInventory(null, 54, "アスレチック一覧");
                athleticInventory.setItem(0, ItemUtil.setItemMeta("シンプル", Material.PAPER));
                player.openInventory(athleticInventory);
                player.addScoreboardTag("athletic");
            }
            //knockBack--------------------------------------------------------------------------------------------
            else if (itemStack.getType() == Material.STICK && itemStack.getItemMeta().getDisplayName().equals("knockback")) {
                player.setHealth(20);
                player.sendMessage("現在開発中です!");
                player.sendMessage("まだアクセスすることができません!");

                if (knockBackWhichCan.equals("false")) {
                    player.sendMessage("2人しかできないので今プレイしている人が終わるまでお待ちください!");
                    e.setCancelled(true);
                    return;
                }
                cannnotDamageList.add(player.getName());
                player.teleport(taikijyo);
                textDisplayUtil.removeKnockBackColumnText(world);
                textDisplayUtil.showKnockBackIsWaiting(Config.textLocationKnockBackColumn, Config.knockBackIsWaiting, visible);
                player.getInventory().clear();
                player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                if (!knockBackPlayerList.contains(player.getName())) {
                    knockBackPlayerList.add(player.getName());
                }
                player.sendMessage(ChatColor.YELLOW + knockBackPlayerList.toString());
                if (knockBackPlayerList.size() == 1) {
                    player.sendMessage(ChatColor.AQUA + "人数が足りません。2人が必要です。");
                    player.sendMessage(ChatColor.AQUA + "現在1人です。");
                }
                else if (knockBackPlayerList.size() == 2) {
                    knockBackWhichCan = "false";
                    player.sendMessage("すでに1人が参加しているので、開始します。");
                    textDisplayUtil.removeKnockBackColumnText(world);
                    textDisplayUtil.showKnockBackIsPlaying(Config.textLocationKnockBackColumn, Config.knockBackIsPlaying, visible);
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (String PlayerName: knockBackPlayerList) {
                                cannnotDamageList.remove(PlayerName);
                            }
                        }
                    }, 410L);
                    this.Timer = new knockBackTimerUtil(knockBackPlayerList).runTaskTimer(this.plugin, 0L, 20L);
                    knockBackUtil.startknockBack(this.plugin, player, knockBackPlayerList);
                }
            }
            //pvp------------------------------------------------------------------------------------------
            else if (itemStack.getType() == Material.EMERALD && itemStack.getItemMeta().getDisplayName().equals("pvp")) {
                player.setHealth(20);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        flag = 0;
                    }
                }, 80L);

                if (flag == 0) {
                    this.flag = 1;
                    player.teleport(this.taikijyo);
                    textDisplayUtil.removePvpColumnText(world);
                    textDisplayUtil.showPvpIsWaiting(Config.textLocationPvpColumn, Config.pvpIsWaiting, visible);

                    cannnotDamageList.add(player.getName());
                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    if (!playerList.contains(player.getName())) {
                        playerList.add(player.getName());
                    }
                    player.sendMessage(ChatColor.YELLOW + playerList.toString());
                    pvpUtil.blockLocationAllRemove();
                    if (this.playerList.size() == 2) {
                        player.sendMessage("すでに1人が参加しているので、開始します。");
                        textDisplayUtil.removePvpColumnText(world);
                        textDisplayUtil.showPvpIsPlaying(Config.textLocationPvpColumn, Config.pvpIsPlaying, visible);
                        this.Timer = new Timer(playerList).runTaskTimer(this.plugin, 0L, 20L);
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                Playing_Game = true;
                                for (String PlayerName : playerList) {
                                    Player player = Bukkit.getPlayer(PlayerName);
                                    if (player == null) return;
                                    if (playerList.size() < 2) {
                                        LimitTimer.stopTimer();
                                        break;
                                    }
                                    cannnotDamageList.remove(PlayerName);
                                    pvpUtil.playerSettingsBeforeGame(player);
                                }
                            }
                        }, 400L);
                        new LimitTimer(playerList, pvpFinal).runTaskTimer(this.plugin, 0L, 20L);


                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                for (String PlayerName : playerList) {
                                    if (playerList.size() < 2) {
                                        LimitTimer.stopTimer();
                                        break;
                                    }
                                    Player player = Bukkit.getPlayer(PlayerName);
                                    if (player == null) return;
                                    pvpUtil.playerSettingsInGame(player);
                                }
                            }
                        }, 900);
                    } else {
                        player.sendMessage(ChatColor.AQUA + "人数が足りません。最低2人が必要です。");
                        player.sendMessage(ChatColor.AQUA + "現在1人です。");
                    }
                }
            }
        }
        if (userTag.contains("athletic")) {
            e.setCancelled(true);
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

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e) {
        HumanEntity human = e.getPlayer();
        Player player = Bukkit.getPlayer(human.getName());
        if (player == null) return;
        World world = player.getWorld();
        Inventory inventory = e.getInventory();
        ItemStack[] contents = inventory.getContents();
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
        if (e.getInventory().getLocation() == null) return;
        if (isEmpty && inventory.getType().name().equals("CHEST")) {
            e.getInventory().getLocation().getBlock().setType(Material.AIR);
            for (String PlayerName: playerList) {
                Player player2 = Bukkit.getPlayer(PlayerName);
                if (player2 == null) return;
                player2.sendMessage("チェストが空になったため消滅しました！");
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
        Location location = e.getBlock().getLocation();
        if (this.world != world) return;
        if (Playing_Game) {
            for (String PlayerName: playerList) {
                if (!playerList.contains(player.getName())) {
                    return;
                }
            }
            pvpUtil.blockLocation(location);
        } else {
            knockBackUtil.knockBackWhoBlockPlaceCheck(knockBackPlayerList, player, location);
        }
//        if (ItemUtil.canBlockPlace(player) == 1) {
//            e.setCancelled(true);
//        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Material material = e.getBlock().getType();
        if (this.world != world) return;
        if (material == null) return;
        if (knockBackWhichCan.equals("false")) {
            if (material.equals(Material.GRASS_BLOCK)) {
                player.sendMessage("地形の破壊は許可されてません!");
                e.setCancelled(true);
                return;
            }
            else if (material.equals(Material.DIRT)) {
                player.sendMessage("地形の破壊は許可されてません!");
                e.setCancelled(true);
                return;
            }
            else if(material.equals(Material.OAK_LEAVES)) {
                player.sendMessage("地形の破壊は許可されてません!");
                e.setCancelled(true);
                return;
            }
            else if(material.equals(Material.OAK_LOG)) {
                player.sendMessage("地形の破壊は許可されてません!");
                e.setCancelled(true);
                return;
            }
             else if(material.equals(Material.GOLD_BLOCK)) {
                player.sendMessage("地形の破壊は許可されてません!");
                e.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        World world = entity.getWorld();
        if (this.world != world) return;
        for (String PlayerName: cannnotDamageList) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;
        if (knockBackWhichCan.equals("false")) {
                e.setCancelled(true);
        }
    }
}
