package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.WeaponManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class UBSRWeapons extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin(){
        return plugin;
    }

    public static final Logger logger=Logger.getLogger(UBSRWeapons.class.getCanonicalName());

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = Bukkit.getPluginManager().getPlugin("UBSRWeapons");
        this.getServer().getPluginManager().registerEvents(new Listener(),this);
        this.getCommand("getweapon").setExecutor(new GetWeapon());
        ConfigurationManager.initConfiguration();
        logger.warning(WeaponManager.registeredGuns.toString());
        logger.warning(String.valueOf(WeaponManager.magazines.size()));
        logger.warning(String.valueOf(WeaponManager.ammos.size()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

}
