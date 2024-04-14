package me.cucc.ubsrweapons.impl;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getWorld;

public class Weapon {
    int id;
    int damage;
    int ammo;
    int recoil;
    int spread;
    int velocity;
    int model;

    public Weapon(int id, int damage, int ammo, int recoil, int spread, int velocity, int model) {
        this.id = id;
        this.damage = damage;
        this.ammo = ammo;
        this.recoil = recoil;
        this.spread = spread;
        this.velocity = velocity;
        this.model = model;
    }

    public void shoot(ItemStack weaponItem, Player handler) {
        short weaponDurability = weaponItem.getDurability();
        if (weaponDurability <= 1) return; // Means that it's not reloaded.

        weaponItem.setDurability((short) (weaponDurability - 1));

        Snowball bullet = handler.getWorld().spawn(handler.getLocation(), Snowball.class);
        bullet.setVelocity(handler.getLocation().getDirection().multiply(2));
    }

    public static boolean isWeapon(ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        if (!itemMeta.hasLore()) return false;

        //noinspection DataFlowIssue
        return itemMeta.lore().toString().contains("Weapon:");
    }

    public static Weapon getWeapon(ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        if (!itemMeta.hasLore()) return null;

        for (Weapon weapon : WeaponManager.registeredWeapons) {
            if (weapon.id == Integer.parseInt(((TextComponent) itemMeta.lore().get(0)).content().split(":")[0])) return weapon;         }

        return null;
    }

    public int getId() {
        return id;
    }

    public int getAmmo() {
        return ammo;
    }
}
