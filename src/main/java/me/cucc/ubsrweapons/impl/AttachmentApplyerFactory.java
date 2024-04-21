package me.cucc.ubsrweapons.impl;

import me.cucc.ubsrweapons.ConfigurationManager;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public final class AttachmentApplyerFactory {

    public static ArrayList<AttachmentApplyer> fromJSONObjcet(JSONObject jsonObject) throws IllegalArgumentException{
        ArrayList<AttachmentApplyer> attachmentApplyrs = new ArrayList<>();
        Set<String> toModify = jsonObject.keySet();
        for(String key : toModify){
            AttachmentApplyer attachmentApplyer = null;
            switch (key){
                case "damage" -> attachmentApplyer = (weapon) -> weapon.setDamage(resolveIntValue(jsonObject.get("damage"), weapon.getDamage()));
                case "spread" -> attachmentApplyer = weapon -> weapon.setSpread(resolveIntValue(jsonObject.get("spread"), weapon.getSpread()));
                case "recoil" -> attachmentApplyer = weapon -> weapon.setRecoil(resolveIntValue(jsonObject.get("recoil"), weapon.getRecoil()));
                case "velocity" -> attachmentApplyer = weapon -> weapon.setVelocity(resolveIntValue(jsonObject.get("velocity"), weapon.getVelocity()));
                case "bullets_per_second" -> attachmentApplyer = weapon -> weapon.setCooldown(resolveIntValue(jsonObject.get("bullets_per_second"), weapon.getCooldown()));
                case "gun_model_data" -> attachmentApplyer = weapon -> weapon.setGunModelData(resolveIntValue(jsonObject.get("gun_model_data"), weapon.getGunModelData()));
                case "scope_model_data" -> attachmentApplyer = weapon -> weapon.setScopeModelData(resolveIntValue(jsonObject.get("scope_model_data"), weapon.getGunModelData()));
                case "display_name" -> attachmentApplyer = weapon -> weapon.setDisplayName(ConfigurationManager.resolveStringValue(jsonObject.get("display_name")));
                case "lore" -> attachmentApplyer = weapon -> weapon.setLore(ConfigurationManager.resolveStringArray(jsonObject.get("lore")));
            }
            if(attachmentApplyer != null){
                attachmentApplyrs.add(attachmentApplyer);
            }else {
                throw new IllegalArgumentException();
            }
        }
        return attachmentApplyrs;
    }

    private static <T> int resolveIntValue(T value,int currentValue) throws IllegalArgumentException{
        int result;
        if(value instanceof String){
            String stringValue = (String) value;
            if(stringValue.substring(0,1).equalsIgnoreCase("+")){
                if(stringValue.substring(1).matches("\\d+")){
                    result = currentValue + Integer.parseInt(stringValue.substring(1));
                    return result;
                }else {
                    throw new IllegalArgumentException(stringValue+"is illegal value in attachment modfiy blocks next to integer keys, value must only contains digits");
                }
            } else if (stringValue.substring(0,1).equalsIgnoreCase("-")) {
                if(stringValue.substring(1).matches("\\d+")){
                    result = currentValue - Integer.parseInt(stringValue.substring(1));
                    return result;
                }else {
                    throw new IllegalArgumentException(stringValue+"is illegal value in attachment modfiy blocks next to integer keys, value must only contains digits");
                }
            }else {
                throw new IllegalArgumentException(stringValue+" is illegal value type in attachment modify blocks next to integer keys," +
                        "in this case string values most only represent addition or subtraction, which are executed on the actual weapon parameter values, are represented by keys," +
                        "they must begin with + or - sign");
            }
        } else if (value instanceof Integer) {
            result = (Integer) value;
            return result;
        }else {
            throw new IllegalArgumentException(value.getClass().toString()+" is illegal value type in attachment modify blocks, required types: integer, string");
        }
    }

}
