package me.cucc.ubsrweapons.impl;

import java.util.ArrayList;

public class Rifle extends Gun{

    private ArrayList<Ammo> rounds;

    private int capacity;

    public Rifle(int id, String name, String displayName, ArrayList<String> lore, int damage, int recoil, int spread, int velocity, int cooldown, int gunModelData, int scopeModelData, ArrayList<Attachment> attachments, ArrayList<FireMode> fireModes, FireType fireType, int burstBulletNumber, int capacity) {
        super(id, name, displayName, lore, damage, recoil, spread, velocity, cooldown, gunModelData, scopeModelData, attachments, fireModes, fireType, burstBulletNumber);
        this.capacity = capacity;
    }


}
