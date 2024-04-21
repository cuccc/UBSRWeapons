package me.cucc.ubsrweapons.impl;

import me.cucc.ubsrweapons.ConfigurationManager;

import java.util.ArrayList;

public class WeaponManager {
    public static ArrayList<Gun> registeredGuns = ConfigurationManager.getWeapons();
    // director -> ./Weapons/x.json
    public static ArrayList<Magazine> magazines = ConfigurationManager.initMagazines();

    public static ArrayList<Ammo> ammos = ConfigurationManager.initAmmos();

}
