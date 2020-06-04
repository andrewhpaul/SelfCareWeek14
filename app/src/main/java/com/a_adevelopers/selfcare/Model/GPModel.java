package com.a_adevelopers.selfcare.Model;

public class GPModel {
    public GPModel(String id, String image, String name,String g_id) {
        this.name = name;
        this.id=id;
        this.image=image;
        this.g_id=g_id;
    }

    String name;
    String g_id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
