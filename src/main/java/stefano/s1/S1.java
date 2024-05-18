package stefano.s1;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import stefano.s1.SvCommand.SvCommand;
import stefano.s1.utils.AthleticTimer;
import stefano.s1.world.stefanovarentino;
import stefano.s1.utils.textDisplayUtil;

public final class S1 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new stefanovarentino(this);
        getLogger().info("pluginS1");
        AthleticTimer athleticTimer = new AthleticTimer();
        getCommand("sv").setExecutor(new SvCommand(athleticTimer));
        World world = Bukkit.getWorld("stefanovarentino");
        if (world == null) return;
        boolean visible = false;
        textDisplayUtil.removeMainText(world);
        textDisplayUtil.showMainArmorStand();
        textDisplayUtil.removeKnockBackColumnText(world);
        textDisplayUtil.removePvpColumnText(world);
        textDisplayUtil.showPvpIsStopping(Config.textLocationPvpColumn, Config.pvpIsStopping, visible);
        textDisplayUtil.showKnockBackIsStopping(Config.textLocationKnockBackColumn, Config.knockBackIsStopping, visible);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
