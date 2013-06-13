package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 26/11/2012 - 03:51:35 AM
 */
@Entity
public class MusicalArtist {

    @Id
    private String name;
    private String imageurl;
    private String imagecaption;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageURL) {
        this.imageurl = imageURL;
    }

    public String getImagecaption() {
        return imagecaption;
    }

    public void setImagecaption(String imageCaption) {
        this.imagecaption = imageCaption;
    }
}
