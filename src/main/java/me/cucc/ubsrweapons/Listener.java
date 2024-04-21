package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.Gun;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        Player weaponHandler = event.getPlayer();
        ItemStack itemInHand = weaponHandler.getInventory().getItemInMainHand();
        if (Gun.isWeapon(itemInHand)) {
            Gun.getWeapon(itemInHand).shoot(itemInHand, event.getPlayer());
        }
    }
}
