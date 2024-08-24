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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import stefano.s1.Config;
import stefano.s1.S1;
import stefano.s1.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;


public class stefanovarentino implements Listener {
    S1 plugin;

    private World world;
    Location lobby, taikijyo, athletic1, checkpoint1, athleticClear, athleticStart, pvpStart, pvpFinal, taikijyof, freePvpSpace;

    public  ArrayList<String> playerList, deathPlayerList, athleticPlayerList;

    public static ArrayList<String> knockBackPlayerList, cannotDamageList, playerPvpStatusIsPlayingList, playerCanPlayEffectInPvpList;

    String knockBackWhichCan = "true";

    public static int flag, bedwarsFlag, knockBackPlayerCounter;
    public Boolean Playing_Game = false;
    public FileConfiguration checkpointList;
    BukkitTask Timer, BedwarsTimer;
    AthleticTimer athleticTimer;

    Random random;



    public stefanovarentino(S1 plugin) {
        Bukkit.getLogger().info("StefanoVarentino Plugin loaded");
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                world = Bukkit.getWorld("stefanovarentino");
                lobby = new Location(world, 25.500, 223, 25.500, -90, 0);
                taikijyo = new Location(world, 31.500, 240, 25.500, -90, 0);
                athletic1 = new Location(world, 50.500, 239, 25.500, -90, 0);
                checkpoint1 = new Location(world, 76.500, 244, 80.500, 0, 0);
                pvpFinal = new Location(world, 15.999, 248.500, 17.999, -90, 0);
                taikijyof = new Location(world, 41.500, 247, 25.500);
                athleticClear = new Location(world, 16, 239, 41);
                athleticStart = new Location(world, 55, 239, 25);
                pvpStart = new Location(world, 25, 70, 24, -90, 0);
                freePvpSpace = new Location(world, 6.500, 237.500, 25.500, 90, 0);
            }
        }, 10L);
        this.playerList = new ArrayList<>();
        this.deathPlayerList = new ArrayList<>();
        this.athleticPlayerList = new ArrayList<>();
        this.cannotDamageList = new ArrayList<>();
        this.checkpointList = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder().getParent(), "checkpoint.yml"));
        this.athleticTimer = new AthleticTimer();
        this.flag = 0;
        this.bedwarsFlag = 0;
        this.knockBackPlayerList = new ArrayList<>();
        knockBackPlayerCounter = 0;
        playerPvpStatusIsPlayingList = new ArrayList<>();
        this.playerCanPlayEffectInPvpList = new ArrayList<>();
        this.random = new Random();
    }





    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (this.world != world) return;

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.setGameMode(GameMode.ADVENTURE);
            }
        }, 20L);
        worldSettings.firstSettings(player, lobby, cannotDamageList);
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
                if (sign == null) return;
                String[] lines = sign.getLines();
                int ramdomInt = random.nextInt(5);
                pvpUtil.makeEffectsList(Config.effectList);
                worldSettings.signClick(player, lines, checkpointList, athleticClear, plugin, knockBackPlayerList, playerList, playerCanPlayEffectInPvpList, ramdomInt, Config.effectList);

            } else if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
                player.setHealth(20);
                worldSettings.anvilClickAction(player, plugin, e);
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
                    if (athleticClear.getWorld() == null) {
                        worldSettings.sendErrorMessageToPlayer(player, 140, "location null", "stefanovarentino");
                        return;
                    }
                    AthleticUtil.afterAthleticAction(player, plugin, athleticClear);
                }
                else if (Math.floor(e.getClickedBlock().getLocation().getX()) == Math.floor(athleticStart.getX()) && Math.floor(e.getClickedBlock().getLocation().getY()) == Math.floor(athleticStart.getY()) && Math.floor(e.getClickedBlock().getY()) == Math.floor(athleticStart.getY())) {
                    if (athleticStart.getWorld() == null) {
                        worldSettings.sendErrorMessageToPlayer(player, 147, "location null", "stefanovarentino");
                        return;
                    }
                    AthleticUtil.beforeAthleticAction(player, athleticStart, athleticTimer);
                }

                else if (Math.floor(e.getClickedBlock().getLocation().getX()) == -6 && Math.floor(e.getClickedBlock().getLocation().getY()) == 233 && Math.floor(e.getClickedBlock().getLocation().getZ()) == 25) {
                    if (cannotDamageList.contains(player.getName())) {
                        player.sendMessage("You joined " + ChatColor.AQUA + "Free PVP space");
                        player.getInventory().addItem(ItemUtil.setItemMeta("聖なる剣", Material.STONE_SWORD));
                        player.teleport(freePvpSpace);
                        player.setHealth(20);
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                cannotDamageList.remove(player.getName());
                            }
                        }, 60L);
                        player.setHealth(20);
                    }
                    else if (!cannotDamageList.contains(player.getName())) {
                        player.sendMessage("問題が発生しました");
                        player.sendMessage("ワールドを一度入り直してください");
                        return;
                    }
                }

                else if (Math.floor(e.getClickedBlock().getLocation().getX()) == 7 && Math.floor(e.getClickedBlock().getLocation().getY()) == 243 && Math.floor(e.getClickedBlock().getLocation().getZ()) == 25) {
                    if (!cannotDamageList.contains(player.getName())) {
                        if (playerPvpStatusIsPlayingList.contains(player.getName())) {
                            player.sendMessage("まだ試合中です!");
                            player.sendMessage("もう少ししたらもう一度試してみてください");
                            return;
                        }
                        else if (!playerPvpStatusIsPlayingList.contains(player.getName())) {
                            player.sendMessage("You left " + ChatColor.AQUA + "Free PVP space");
                            player.getInventory().clear();
                            player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                            cannotDamageList.add(player.getName());
                            player.setHealth(20);
                            Location frontOfFreePvp = new Location(world, -3, 232, 25);
                            player.teleport(frontOfFreePvp);
                        }
                    }
                    else if (cannotDamageList.contains(player.getName())) {
                        player.sendMessage("問題が発生しました");
                        player.sendMessage("ワールドを一度入り直してください");
                        return;
                    }
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null) {
                ItemStack itemStack = e.getItem();
                if (itemStack.getType() == Material.RED_MUSHROOM) {
                    if (cannotDamageList.contains(player.getName())) {
                        cannotDamageList.add(player.getName());
                    }
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
                        textDisplayUtil.removeKnockBackColumnText(world);
                        boolean visible = false;
                        textDisplayUtil.showKnockBackIsStopping(Config.textLocationKnockBackColumn, Config.knockBackIsStopping, visible);
                        e.getPlayer().sendMessage("knockbackをキャンセルしました。");
                        e.getPlayer().teleport(lobby);
                        e.getPlayer().getInventory().clear();
                        e.getPlayer().getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        knockBackWhichCan = "true";
                        knockBackPlayerCounter = knockBackPlayerCounter - 1;

                    }
                    if (knockBackPlayerList.size() == 1) {
                        knockBackWhichCan = "true";
                        knockBackPlayerCounter = knockBackPlayerCounter - 1;
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
                    if (itemStack.getItemMeta() == null) {
                        worldSettings.sendErrorMessageToPlayer(player, 268, "itemStack itemMeta null", "stefanovarentino");
                        return;
                    }
                    if (itemStack.getItemMeta().getDisplayName().equals("最初に戻る(athletic1)")) {
                        player.teleport(this.athletic1);
                    }
                    player.getInventory().clear();
                    AthleticUtil.athletic1UtilityItem(player);
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
    public void onPlayerMoveEvent(PlayerMoveEvent e) {
        if (Playing_Game) {
            Player player = e.getPlayer();
            for (String PlayerName: playerList) {
                Player playerPvp = Bukkit.getPlayer(PlayerName);
                if (playerPvp == null) return;
                World world = playerPvp.getWorld();
                if (this.world != world) return;

                if (playerPvp.getLocation().getX() > Config.Range) {
                    pvpUtil.borderSettings1(playerPvp, world);
                    return;
                } else if (playerPvp.getLocation().getZ() > Config.Range) {
                    pvpUtil.borderSettings2(playerPvp, world);
                    return;
                } else if (playerPvp.getLocation().getX() < -Config.Range) {
                    pvpUtil.borderSettings3(playerPvp, world);
                    return;
                } else if (playerPvp.getLocation().getZ() < -Config.Range) {
                    pvpUtil.borderSettings4(playerPvp, world);
                    return;
                }
            }

        } else if (knockBackWhichCan.equals("false")) {
            Player player2 = e.getPlayer();
            Location playerLocation = player2.getLocation();
            if (playerLocation.getY() < 200 && playerLocation.getY()  > 180) {
                for (String PlayerName: knockBackPlayerList) {
                    Player playerName = Bukkit.getPlayer(PlayerName);
                    if (playerName != null) {
                        if (playerName != player2) {
                            boolean visible = false;
                            knockBackUtil.knockBackLoserAction(player2, knockBackPlayerList, world, visible, cannotDamageList, plugin, lobby);
                            knockBackUtil.knockBackBlockCrear();
                            knockBackWhichCan = "true";
                            worldSettings.runTaskRater(plugin, 30L, player2, lobby, "teleport");
                            player2.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
                        }
                    } else return;
                }
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
                    pvpUtil.pvpWinnerAction(player2, cannotDamageList, playerCanPlayEffectInPvpList, plugin, lobby);
                }
            }
        } {
            if (knockBackPlayerList.contains(player.getName())) {
                boolean visible = false;
                playerCanPlayEffectInPvpList.clear();
                knockBackUtil.knockBackLoserAction(player, knockBackPlayerList, world, visible, cannotDamageList, plugin, lobby);
                knockBackWhichCan = "true";
                knockBackPlayerList.remove(player.getName());
                knockBackUtil.knockBackBlockCrear();
                worldSettings.runTaskRater(plugin, 30L, player, lobby, "teleport");
                player.getInventory().addItem(ItemUtil.setItemMeta("ロビーの中心に戻る", Material.RED_MUSHROOM));
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
        if (!cannotDamageList.contains(player.getName())) {
            cannotDamageList.add(player.getName());
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
        if (itemStack == null || itemStack.getItemMeta() == null) {
            return;
        }
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
                if (knockBackWhichCan.equals("false")) {
                    player.sendMessage("2人しかできないので今プレイしている人が終わるまでお待ちください!");
                    e.setCancelled(true);
                    return;
                } else if (knockBackWhichCan.equals("true")) {
                    knockBackPlayerCounter = knockBackPlayerCounter + 1;
                    if (knockBackPlayerCounter == 2) {
                        knockBackWhichCan = "false";
                    }
                    playerCanPlayEffectInPvpList.clear();
                    for (String PlayerName: knockBackPlayerList) {
                        player.sendMessage(String.valueOf(knockBackPlayerList));
                        playerCanPlayEffectInPvpList.add(PlayerName);
                        player.sendMessage(String.valueOf(knockBackPlayerList));
                    }
                    knockBackUtil.knockBackSetUp(player, taikijyo, knockBackPlayerList, visible, cannotDamageList, e, world, plugin);
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

                    player.getInventory().clear();
                    player.getInventory().addItem(ItemUtil.setItemMeta("ロビーに戻る", Material.RED_MUSHROOM));
                    if (!playerList.contains(player.getName())) {
                        playerList.add(player.getName());
                        player.sendMessage(String.valueOf(playerList));
                    }
                    player.sendMessage(ChatColor.YELLOW + playerList.toString());
                    pvpUtil.blockLocationAllRemove();
                    if (this.playerList.size() == 2) {
                        player.sendMessage("すでに1人が参加しているので、開始します。");
                        textDisplayUtil.removePvpColumnText(world);
                        textDisplayUtil.showPvpIsPlaying(Config.textLocationPvpColumn, Config.pvpIsPlaying, visible);
                        this.Timer = new Timer(playerList).runTaskTimer(this.plugin, 0L, 20L);
                        pvpUtil.makeEffectsList(Config.effectList);
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
                                    cannotDamageList.remove(PlayerName);
                                    pvpUtil.playerSettingsBeforeGame(player, playerCanPlayEffectInPvpList, playerList);
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
                AthleticTimer.settingsAthleticSimple(player);
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
        Material material = e.getBlock().getType();
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
            knockBackUtil.knockBackWhoBlockPlaceCheck(knockBackPlayerList, player, location, e);
        }

        if (material == Material.RED_MUSHROOM) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        Material material = e.getBlock().getType();
        if (this.world != world) return;
        if (material == null) {
            worldSettings.sendErrorMessageToPlayer(player, 617, "material null", "stefanovarentino");
            return;
        }
        if (knockBackWhichCan.equals("false")) {
            knockBackUtil.knockBackBlockMaterialCheck(material, player, e);
        }
    }
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        World world = entity.getWorld();
        if (this.world != world) return;
        for (String PlayerName: cannotDamageList) {
            e.setCancelled(true);
        }
        if (entity instanceof Player) {
            playerPvpStatusIsPlayingList.add(String.valueOf(entity));
            Player player = Bukkit.getPlayer(String.valueOf(entity));
            if (player == null) return;
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    playerPvpStatusIsPlayingList.remove(String.valueOf(entity));
                }
            }, 100L);
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
