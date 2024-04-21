package me.cucc.ubsrweapons.impl;

import java.util.ArrayList;

public final class Attachment {

    private final String  name;

    private final String gunName;

    private final String displayName;

    private final ArrayList<String> lore;

    private final int itemModelData;

    private final boolean hasCraftingRecipe;

    private final ArrayList<AttachmentApplyer> attachmentApplyers;

    public Attachment(String name, String displayName,ArrayList<String> lore, int itemModelData, boolean hasCraftingRecipe, ArrayList<AttachmentApplyer> attachmentApplyers, String gunName) {
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
        this.itemModelData = itemModelData;
        this.hasCraftingRecipe = hasCraftingRecipe;
        this.attachmentApplyers = attachmentApplyers;
        this.gunName = gunName;
    }

    public void applyAttachment(Gun gun){
        for(AttachmentApplyer a : attachmentApplyers){
            a.apply(gun);
            attachmentApplyers.remove(a);
        }
    }

    public String getName() {
        return name;
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

    public int getItemModelData() {
        return itemModelData;
    }

    public boolean isHasCraftingRecipe() {
        return hasCraftingRecipe;
    }

    public ArrayList<AttachmentApplyer> getAttachmentApplyers() {
        return attachmentApplyers;
    }
}
