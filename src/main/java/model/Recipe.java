package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wendywang on 2015-11-07.
 */
@XmlRootElement
public class Recipe {

    private String dishName;
    private int id;
    private String imageURL;
    private String servingSize;
    private String summary;
    private int authorUserID;

    public Recipe(){} // JAXB needs this

    public Recipe(String dishName, int id, String imageURL, String servingSize, String summary, int authorUserID){
        this.dishName = dishName;
        this.id = id;
        this.imageURL = imageURL;
        this.servingSize = servingSize;
        this.summary = summary;
        this.authorUserID = authorUserID;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getAuthorUserID(){
        return authorUserID;
    }

    public void setAuthorUserID(int authorUserID){
        this.authorUserID = authorUserID;
    }
}