package br.edu.ufam.icomp.lab_encapsulamento;


public class Posicao{
    private double latitude;
    private double longitude;
    private double altitude;


    public Posicao(double latitude, double longitude, double altitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getAltitude(){
        return this.altitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setAltitude(double altitude){
        this.altitude = altitude;
    }
    public String toString(){
//Posição: -3.089242, -59.964874, 88.374
        return "Posição: " + this.latitude + ", " + this.longitude + ", " + this.altitude;
    }

}