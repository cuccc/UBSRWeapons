package me.cucc.ubsrweapons;

import me.cucc.ubsrweapons.impl.*;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigurationManager {

    private static final File pluginFolder = UBSRWeapons.getPlugin().getDataFolder();

    private static ArrayList<Attachment> attachments;

    private static final Logger logger = UBSRWeapons.getPlugin().getLogger();

    private static final String weaponInitErrorMessage = "Something went wrong while initializing weapons from json configuration";

    public static void initConfiguration() {
            if (!(pluginFolder).exists()) {
                pluginFolder.mkdirs();
            }
            File gunsFolder = new File(pluginFolder, "guns");
            if (!(gunsFolder.exists())) {
                gunsFolder.mkdirs();
            }
            File savedGunsFolder = new File(pluginFolder,"saved-guns");
            if(!(savedGunsFolder.exists())){
                savedGunsFolder.mkdirs();
            }
            attachments = initAttachments();
    }

    public static ArrayList<Gun> getWeapons(){
        ArrayList<Gun> guns = new ArrayList<>();
        File gunFolder = new File(pluginFolder+"\\guns");
        File savedGunFolder = new File(pluginFolder+"\\saved-guns");
        for(File save : savedGunFolder.listFiles()) {
            JSONParser jsonParser = new JSONParser();
            String gunFileName = null;
            Integer id = null;
            JSONArray attachments;
            ArrayList<Attachment> gunAttachments = null;
            try(FileReader reader = new FileReader(save)){
                JSONObject savedGun = (JSONObject) jsonParser.parse(reader);
                gunFileName =(String) savedGun.get("gun_file");
                id = (int) (long) savedGun.get("id");
                attachments = (JSONArray) savedGun.get("attachments");
                gunAttachments = new ArrayList<>();
                for(Object i : attachments){
                    if(!(i instanceof String)){
                        throw new IllegalArgumentException();
                    }else {
                        String s = (String) i;
                        for(Attachment attachment : getAttachments()){
                            if(attachment.getGunName().equalsIgnoreCase(gunFileName.substring(0,gunFileName.length()-5))){
                                if(attachment.getName().equalsIgnoreCase(s)){
                                    gunAttachments.add(attachment);
                                }
                            }
                        }
                    }
                }
            }catch (IOException | ParseException e){
                printError(e);
            }
            int counter = 0;
            for (File file : gunFolder.listFiles()) {
                if (gunFileName != null) {
                    if (file.getName().equalsIgnoreCase(gunFileName)) {
                        try (FileReader fileReader = new FileReader(file)) {
                            String name = file.getName().substring(0, file.getName().length() - 5);
                            JSONObject gun = (JSONObject) jsonParser.parse(fileReader);
                            String displayName = formatDisplayName((JSONObject) gun.get("display_name"));
                            JSONArray gunLore = (JSONArray) gun.get("lore");
                            ArrayList<String> lore = formatLore(gunLore);
                            JSONObject attributes = (JSONObject) gun.get("attributes");
                            int damage = (int) (long) attributes.get("damage");
                            int recoil = (int) (long) attributes.get("recoil");
                            int spread = (int) (long) attributes.get("spread");
                            int velocity = (int) (long) attributes.get("velocity");
                            int cooldown = (int) (long) attributes.get("cooldown");
                            JSONObject modelDatas = (JSONObject) gun.get("custom_model_datas");
                            int gunModelData = (int) (long) modelDatas.get("gun_model_data");
                            int scopeModelData = (int) (long) modelDatas.get("scope_model_data");
                            boolean hasMagazine = (boolean) attributes.get("has_magazine");
                            int ammoCapacity = (int) (long) attributes.get("ammo_capacity");
                            String s = (String) attributes.get("fire_type");
                            FireType fireType = FireType.valueOf(s.toUpperCase());
                            ArrayList<String> s1 = formatArrayList((JSONArray) attributes.get("modes"),String.class);
                            ArrayList<FireMode> fireModes = new ArrayList<>();
                            for(String i : s1){
                                fireModes.add(FireMode.valueOf(i.toUpperCase()));
                            }
                            int bulletNumber = (int) (long) attributes.get("burst_number_of_bullets");
                            if(hasMagazine) {
                                guns.add(new AutomaticGun(id, name, displayName, lore, damage, recoil, spread, velocity, cooldown, gunModelData, scopeModelData, gunAttachments, fireModes, fireType, bulletNumber));
                            }else {
                                guns.add(new Rifle(id, name, displayName, lore, damage, recoil, spread, velocity, cooldown, gunModelData, scopeModelData, gunAttachments, fireModes, fireType, bulletNumber,ammoCapacity));
                            }
                        } catch (IOException | ParseException e) {
                            printError(e);
                        }
                        counter = counter+1;
                        break;
                    }
                }
            }
            if(counter == 0){
                logger.warning("Could not find gun configuration file with name: "+gunFileName);
            }
            for(Gun gun : guns){
                for(Attachment attachment : gun.getAttachments()){
                    attachment.applyAttachment(gun);
                }
            }
        }
        return guns;
    }

    public static ArrayList<Attachment>  getAttachments(){
        return attachments;
    }

    private static ArrayList<Attachment>  initAttachments(){
        ArrayList<Attachment> attachments = new ArrayList<>();
        File gunFolder = new File(pluginFolder+"\\guns");
        if(Objects.requireNonNull(gunFolder.listFiles()).length != 0) {
            for(File gunFile : gunFolder.listFiles()){
                JSONParser jsonParser = new JSONParser();
                try(FileReader reader = new FileReader(gunFile)){
                    JSONObject gun =(JSONObject) jsonParser.parse(reader);
                    JSONArray gunAttachments = (JSONArray) gun.get("attachments");
                    String gunName = gunFile.getName().substring(0,gunFile.getName().length()-5);
                    for(Object i : gunAttachments){
                        JSONObject attachment = (JSONObject) i;
                        logger.warning(attachment.toString());
                        String name =(String) attachment.get("name");
                        String displayName = formatDisplayName((JSONObject) attachment.get("display_name"));
                        ArrayList<String> lore = formatLore(attachment.get("lore"));
                        long itemModelData =(long) attachment.get("item_custom_model_data");
                        boolean hasCraftingRecipe = (boolean) attachment.get("has_crafting_recipe");
                        JSONObject modify = (JSONObject) attachment.get("modify");
                        ArrayList<AttachmentApplyer> attachmentApplyers = AttachmentApplyerFactory.fromJSONObjcet(modify);
                        attachments.add(new Attachment(name,displayName,lore,(int) itemModelData,hasCraftingRecipe,attachmentApplyers,gunName));
                    }
                }catch (IOException | ParseException e){
                    printError(e);
                }
            }
        }
        return attachments;
    }

    public static ArrayList<Magazine> initMagazines(){
        ArrayList<Magazine> magazines = new ArrayList<>();
        File gunFolder = new File(pluginFolder+"\\guns");
        if(Objects.requireNonNull(gunFolder.listFiles()).length != 0) {
            for (File gunFile : gunFolder.listFiles()) {
                JSONParser jsonParser = new JSONParser();
                try(FileReader reader = new FileReader(gunFile)){
                    JSONObject gun =(JSONObject) jsonParser.parse(reader);
                    JSONArray jsonMagazines = (JSONArray) gun.get("magazines");
                    String gunName = (String) gun.get("name");
                    for(Object o : jsonMagazines){
                        JSONObject magazine = (JSONObject) o;
                        String name = (String) magazine.get("name");
                        String displayName = formatDisplayName((JSONObject) magazine.get("display_name"));
                        ArrayList<String> lore = formatLore((JSONArray) magazine.get("lore"));
                        ArrayList<String> compatibleAmmos = formatArrayList((JSONArray) magazine.get("compatible_ammos"),String.class);
                        int capacity = (int) (long) magazine.get("capacity");
                        int itemModelData = (int) (long) magazine.get("item_model_data");
                        magazines.add(new Magazine(name,capacity,gunName,compatibleAmmos,itemModelData,displayName,lore));
                    }
                }catch (IOException | ParseException | ClassCastException e){
                    printError(e);
                }
            }
        }
        return magazines;
    }

    public static ArrayList<Ammo> initAmmos(){
        ArrayList<Ammo> ammos = new ArrayList<>();
        File gunFolder = new File(pluginFolder+"\\guns");
        if(Objects.requireNonNull(gunFolder.listFiles()).length != 0) {
            for (File gunFile : gunFolder.listFiles()) {
                JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader(gunFile)) {
                    JSONObject gun =(JSONObject) jsonParser.parse(reader);
                    JSONArray jsonAmmos = (JSONArray) gun.get("ammos");
                    String gunName = (String) gun.get("name");
                    for(Object o : jsonAmmos) {
                        JSONObject ammo = (JSONObject) o;
                        String name = (String) ammo.get("name");
                        String displayName = formatDisplayName((JSONObject) ammo.get("display_name"));
                        ArrayList<String> lore = formatLore((JSONArray) ammo.get("lore"));
                        int itemModelData = (int) (long) ammo.get("ammo_item_model_data");
                        int casingModeldata = (int) (long) ammo.get("casing_model_data");
                        int bulletModelData = (int) (long) ammo.get("bullet_model_data");
                        ammos.add(new Ammo(name,bulletModelData,casingModeldata,itemModelData,gunName,displayName,lore));
                    }
                }catch (IOException | ParseException | ClassCastException e){
                    printError(e);
                }
            }
        }
        return ammos;
    }

    private static void printError(Exception e){
        logger.warning(weaponInitErrorMessage);
        for(StackTraceElement s : e.getStackTrace()){
            logger.severe(s.toString());
        }
    }

    public static String formatDisplayName(JSONObject jsonObject){
        String displayName;
        if(jsonObject.get("text") != null) {
            displayName = (String) jsonObject.get("text");
            if (jsonObject.get("color") != null) {
                displayName =displayName + ChatColor.valueOf((String) jsonObject.get("color"));
                if (jsonObject.get("style") != null) {
                    displayName = ChatColor.valueOf((String) jsonObject.get("style")) + displayName;
                }
            }
            return displayName;
        }
        throw new IllegalArgumentException();
    }

    public static ArrayList<String> formatLore(Object object){
        ArrayList<String> lore = new ArrayList<>();
        if(object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (Object i : jsonArray) {
                if (i instanceof JSONObject) {
                    JSONObject gunLoreElement = (JSONObject) i;
                    if (gunLoreElement.get("text") != null) {
                        if (gunLoreElement.get("color") != null) {
                            String loreElement = (String) gunLoreElement.get("text");
                            loreElement = loreElement + ChatColor.valueOf((String) gunLoreElement.get("color"));
                            if (gunLoreElement.get("style") != null) {
                                loreElement = loreElement + ChatColor.valueOf((String) gunLoreElement.get("style"));
                            }
                            lore.add(loreElement);
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }else if (i instanceof String) {
                    String s = (String) i;
                    if(s.equalsIgnoreCase("${stats}")){

                    }else {
                        throw new IllegalArgumentException();
                    }
                }else {
                    throw new IllegalArgumentException();
                }
            }
        }
        return lore;
    }

    public static String resolveStringValue(Object o){
        JSONObject object;
        if(o instanceof JSONObject){
            object = (JSONObject) o;
        }else {
            throw new IllegalArgumentException();
        }
        String[] keys = {"color","text","style"};
        for(String key : keys){
            if(!(object.containsKey(key))){
                throw new IllegalArgumentException();
            }
        }
        return formatDisplayName(object);
    }

    public static ArrayList<String> resolveStringArray(Object o){
        JSONArray array;
        if(o instanceof JSONArray){
            array = (JSONArray) o;
        }else{
            throw new IllegalArgumentException();
        }
        for(Object i : array){
            if(!(i instanceof JSONObject)){
                throw new IllegalArgumentException();
            } else {
                String[] keys = {"color","text","style"};
                for(String key : keys){
                    if(!(((JSONObject) i).containsKey(key))){
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return formatLore(array);
    }

    public static <T> ArrayList<T> formatArrayList(JSONArray jsonArray, Class<T> elementClass){
        ArrayList<T> result = new ArrayList<>();
        for(Object o : jsonArray){

                T element = (T) o;
                result.add(element);
        }
        return result;
    }
}
