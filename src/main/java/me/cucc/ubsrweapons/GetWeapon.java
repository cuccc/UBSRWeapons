package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.WeaponManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetWeapon implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            player.getInventory().addItem(WeaponManager.registeredGuns.get(0).getItemStack());
            player.getInventory().addItem(WeaponManager.magazines.get(0).getItemStack());
            player.getInventory().addItem(WeaponManager.ammos.get(0).getItemStack());
        }
        return true;
    }
}
