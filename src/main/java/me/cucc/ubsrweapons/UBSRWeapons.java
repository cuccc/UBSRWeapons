package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.Weapon;
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
        ConfigurationManager.initConfiguration();
        WeaponManager.registeredWeapons.add(new Weapon(6,5,45,3,6,3,24));
        // Eszek, tudsz csinalni egy comandot amibe bele lehet tenni egy Weapon classot,
        // es addol a playernek egy itemet aminek a weapon.ammo durabilityje van + a loreban "Weapon:IDIDE"
        this.getCommand("getweapon").setExecutor(new GetWeapon(new Weapon(6,5,6,3,6,3,24)));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

}
