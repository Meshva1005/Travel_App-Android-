package com.example.tempauthentication;

public class travel {
    private int image;
    private String location;
    private String name;

    public travel(int image, String location, String name){
        this.image = image;
        this.name = name;
        this.location = location;
    }

    public travel(){

    }

    public int getImage(){ return image;}
    public void setImage(int image){this.image = image;}

    public String getName(){ return name;}
    public void setName(String name){this.name = name;}

    public String getLocation(){ return location;}
    public void setLocation(String location){this.location = location;}

}
