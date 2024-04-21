package me.cucc.ubsrweapons.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public final class Magazine {

    private final String name;

    private final int capacity;

    private final String gunName;

    private final ArrayList<String> compatibleAmmoNames;

    private ArrayList<Ammo> rounds;

    private final int itemModelData;

    private final String displayName;

    private ArrayList<String> lore;

    public Magazine(String name,int capacity, String gunName, ArrayList<String> compatibleAmmoNames, int itemModelData, String displayName, ArrayList<String> lore) {
        this.name = name;
        this.capacity = capacity;
        this.gunName = gunName;
        this.compatibleAmmoNames = compatibleAmmoNames;
        this.itemModelData = itemModelData;
        this.displayName = displayName;
        this.lore = lore;
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setCustomModelData(itemModelData);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getGunName() {
        return gunName;
    }

    public ArrayList<String> getCompatibleAmmoNames() {
        return compatibleAmmoNames;
    }

    public ArrayList<Ammo> getRounds() {
        return rounds;
    }

    public int getItemModelData() {
        return itemModelData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public void setRounds(ArrayList<Ammo> rounds) {
        this.rounds = rounds;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public String getName() {
        return name;
    }
}
