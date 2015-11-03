package com.innovation.tencent.botany_cultivate.domain;

/**
 * Created by Mr.Jadyn on 15/10/24.
 */
public class TodaySeed {
    private int id;
    private String name;
    private String type;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
