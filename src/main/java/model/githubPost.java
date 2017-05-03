/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;


public class githubPost {
    
    private int id;
    private String title;
    private String Description;
    private long numOfViews;
    private long forksNum;
    private String repoUrl;
    private String OwnerName;
    private String created_date;
    private String last_updated;
    private String Download_url;
    private String language;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public long getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(long numOfViews) {
        this.numOfViews = numOfViews;
    }

    public long getForksNum() {
        return forksNum;
    }

    public void setForksNum(long forksNum) {
        this.forksNum = forksNum;
    }


    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String OwnerName) {
        this.OwnerName = OwnerName;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public String getDownload_url() {
        return Download_url;
    }

    public void setDownload_url(String Download_url) {
        this.Download_url = Download_url;
    }
    
    
}
