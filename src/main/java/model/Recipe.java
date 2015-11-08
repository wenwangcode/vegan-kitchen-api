package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wendywang on 2015-11-07.
 */
@XmlRootElement
public class Recipe {

    public String name;
    public int id;

    public Recipe(){} // JAXB needs this

    public Recipe(String name, int id){
        this.name = name;
        this.id = id;

    }


}