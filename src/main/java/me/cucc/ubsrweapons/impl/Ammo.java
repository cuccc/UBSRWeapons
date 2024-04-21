package me.cucc.ubsrweapons.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public final class Ammo {

    private final String name;

    private final int bulletModelData;

    private final int casingModelData;

    private final int ammoItemModelData;

    private final String gunName;

    private final String displayName;

    private final ArrayList<String> lore;

    public Ammo(String name, int bulletModelData, int casingModelData, int ammoItemModelData, String gunName, String displayName, ArrayList<String> lore) {
        this.name = name;
        this.bulletModelData = bulletModelData;
        this.casingModelData = casingModelData;
        this.ammoItemModelData = ammoItemModelData;
        this.gunName = gunName;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(ammoItemModelData);
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public int getBulletModelData() {
        return bulletModelData;
    }

    public int getCasingModelData() {
        return casingModelData;
    }

    public int getAmmoItemModelData() {
        return ammoItemModelData;
    }

    public String getGunName() {
        return gunName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<String> getLore() {
        return lore;
    }
}
