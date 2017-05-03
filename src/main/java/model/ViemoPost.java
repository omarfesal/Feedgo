/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author fesal
 */
public class ViemoPost {
    private String id;
    private String name;
    private String description;
    private String embeded;
    private String created_time;
    private String mid_pic;
    private String large_pic;
    private String URL;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmbeded() {
        return embeded;
    }

    public void setEmbeded(String embeded) {
        this.embeded = embeded;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getMid_pic() {
        return mid_pic;
    }

    public void setMid_pic(String mid_pic) {
        this.mid_pic = mid_pic;
    }

    public String getLarge_pic() {
        return large_pic;
    }

    public void setLarge_pic(String large_pic) {
        this.large_pic = large_pic;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    
}
