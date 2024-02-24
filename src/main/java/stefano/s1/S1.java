package stefano.s1;

import org.bukkit.plugin.java.JavaPlugin;
import stefano.s1.SvCommand.SvCommand;
import stefano.s1.utils.AthleticTimer;
import stefano.s1.world.stefanovarentino;

public final class S1 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new stefanovarentino(this);
        getLogger().info("pluginS1");
        AthleticTimer athleticTimer = new AthleticTimer();
        getCommand("sv").setExecutor(new SvCommand(athleticTimer));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
