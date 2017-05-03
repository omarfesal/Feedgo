/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.Transient;
import org.springframework.social.slideshare.api.domain.*;
import org.springframework.social.slideshare.api.domain.Slideshow.Tag;

/**
 *
 * @author fesal
 */

public class slidesharePost {
    private String id;
    private String title;
    private String Description;
    private String language;
    private long numOfView;
    private List<Slideshow.RelatedSlideshow> related_slideShow;
    private List<Tag> list_of_tags ;
    private String Img_url ;
    private String Embeded ;
    private long Num_of_downloads;
    private String username;
    private String Download_url;
    private String URL; 
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getNumOfView() {
        return numOfView;
    }

    public void setNumOfView(long numOfView) {
        this.numOfView = numOfView;
    }

    public List<Slideshow.RelatedSlideshow> getRelated_slideShow() {
        return related_slideShow;
    }

    public void setRelated_slideShow(List<Slideshow.RelatedSlideshow> related_slideShow) {
        this.related_slideShow = related_slideShow;
    }

    public List<Tag> getList_of_tags() {
        return list_of_tags;
    }

    public void setList_of_tags(List<Tag> list_of_tags) {
        this.list_of_tags = list_of_tags;
    }

    public String getImg_url() {
        return Img_url;
    }

    public void setImg_url(String Img_url) {
        this.Img_url = Img_url;
    }

    public String getEmbeded() {
        return Embeded;
    }

    public void setEmbeded(String Embeded) {
        this.Embeded = Embeded;
    }

    public long getNum_of_downloads() {
        return Num_of_downloads;
    }

    public void setNum_of_downloads(long Num_of_downloads) {
        this.Num_of_downloads = Num_of_downloads;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownload_url() {
        return Download_url;
    }

    public void setDownload_url(String Download_url) {
        this.Download_url = Download_url;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    
    
    
}
