package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.Weapon;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GetWeapon implements CommandExecutor {

    private Weapon weapon;

    public GetWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            ItemStack im = new ItemStack(Material.CARROT_ON_A_STICK);
            ItemMeta itemMeta = im.getItemMeta();
            itemMeta.setCustomModelData(24);
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Weapon:"+weapon.getId());
            itemMeta.setLore(lore);
            im.setItemMeta(itemMeta);
            im.setDurability((short) weapon.getAmmo());
            player.getInventory().addItem(im);
        }
        return true;
    }
}
