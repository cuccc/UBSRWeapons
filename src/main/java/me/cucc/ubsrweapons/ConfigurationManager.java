package me.cucc.ubsrweapons;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationManager {

    private static final File pluginFolder = UBSRWeapons.getPlugin().getDataFolder();

    public static void initConfiguration() {
        try {
            if (!(pluginFolder).exists()) {
                pluginFolder.mkdirs();
            }
            File gunsFolder = new File(pluginFolder, "guns");
            if (!(gunsFolder.exists())) {
                gunsFolder.mkdirs();
            }
            //Create an example json file
            JSONObject jsonObject = new JSONObject();
            int id;
            int damage;
            int ammo;
            int recoil;
            int spread;
            int velocity;
            int model;
            int[] values = {6, 5, 8, 7, 5, 9, 24};
            String[] keys = {"id", "damage", "ammo", "recoil", "spread", "velocity", "model"};
            for (int i = 0; i < keys.length; i++) {
                jsonObject.put(keys[i], values[i]);
            }
            new PrintWriter(new FileWriter(pluginFolder + "\\guns\\gun_example.json")).write(jsonObject.toString());
        } catch (IOException e) {
            for(StackTraceElement el : e.getStackTrace()){
                UBSRWeapons.logger.severe(el.toString());
            }
        }
    }
}
