package me.cucc.ubsrweapons.impl;

import java.util.ArrayList;

public class AutomaticGun extends Gun{

    private Magazine magazine;

    public AutomaticGun(int id, String name, String displayName, ArrayList<String> lore, int damage, int recoil, int spread, int velocity, int cooldown, int gunModelData, int scopeModelData, ArrayList<Attachment> attachments, ArrayList<FireMode> fireModes, FireType fireType, int burstBulletNumber) {
        super(id, name, displayName, lore, damage, recoil, spread, velocity, cooldown, gunModelData, scopeModelData, attachments, fireModes, fireType, burstBulletNumber);

    }


}
