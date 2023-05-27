package stefano.s1;

import org.bukkit.plugin.java.JavaPlugin;
import stefano.s1.world.stefanovarentino;

public final class S1 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new stefanovarentino(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
