package me.cucc.ubsrweapons.impl;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getWorld;

public abstract class Gun {
    private int id;
    private final String name;
    private String displayName;
    private ArrayList<String> lore;
    private int damage;
    private int ammo;
    private int recoil;
    private int spread;
    private int velocity;
    private int cooldown;
    private int gunModelData;
    private int scopeModelData;
    private ArrayList<Attachment> attachments;
    private ArrayList<FireMode> fireModes;
    private int burstBulletNumber;
    private FireType fireType;

    public Gun(int id, String name, String displayName, ArrayList<String> lore, int damage, int recoil, int spread, int velocity,
               int cooldown, int gunModelData, int scopeModelData, ArrayList<Attachment> attachments, ArrayList<FireMode> fireModes,
               FireType fireType,int burstBulletNumber) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
        this.damage = damage;
        this.recoil = recoil;
        this.spread = spread;
        this.velocity = velocity;
        this.cooldown = cooldown;
        this.gunModelData = gunModelData;
        this.scopeModelData = scopeModelData;
        this.attachments = attachments;
        this.fireModes = fireModes;
        this.fireType = fireType;
        this.burstBulletNumber = burstBulletNumber;
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
        return itemMeta.lore().toString().contains("Gun:");
    }

    public static Gun getWeapon(ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        if (!itemMeta.hasLore()) return null;

        for (Gun gun : WeaponManager.registeredGuns) {
            if (gun.id == Integer.parseInt(((TextComponent) itemMeta.lore().get(0)).content().split(":")[0])) return gun;         }

        return null;
    }

    public ItemStack getItemStack(){
        ItemStack weaponItem = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta itemMeta = weaponItem.getItemMeta();
        lore.add(String.valueOf(damage));
        lore.add(String.valueOf(recoil));
        lore.add(String.valueOf(spread));
        lore.add(String.valueOf(velocity));
        lore.add(String.valueOf(cooldown));
        lore.add(String.valueOf(scopeModelData));
        lore.add(ChatColor.DARK_GRAY+String.valueOf(id));
        itemMeta.setLore(this.lore);
        itemMeta.setCustomModelData(gunModelData);
        itemMeta.setDisplayName(this.displayName);
        weaponItem.setItemMeta(itemMeta);
        return weaponItem;
    }

    public int getId() {
        return id;
    }

    public int getAmmo() {
        return ammo;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public int getDamage() {
        return damage;
    }

    public int getRecoil() {
        return recoil;
    }

    public int getSpread() {
        return spread;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getGunModelData() {
        return gunModelData;
    }

    public int getScopeModelData() {
        return scopeModelData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setRecoil(int recoil) {
        this.recoil = recoil;
    }

    public void setSpread(int spread) {
        this.spread = spread;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setGunModelData(int gunModelData) {
        this.gunModelData = gunModelData;
    }

    public void setScopeModelData(int scopeModelData) {
        this.scopeModelData = scopeModelData;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachment(Attachment attachment){
        attachments.add(attachment);
    }


}
