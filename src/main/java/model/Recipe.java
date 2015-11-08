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
    private int servingSize;
    private String summary;

    public Recipe(){} // JAXB needs this

    public Recipe(String dishName, int id, String imageURL, int servingSize, String summary){
        this.dishName = dishName;
        this.id = id;
        this.imageURL = imageURL;
        this.servingSize = servingSize;
        this.summary = summary;
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

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}