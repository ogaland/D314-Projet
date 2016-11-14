/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.device;

/**
 *
 * @author ko
 */
public class LightBulb extends Device {
    private String state;
    private String color;
    private int brightness;// de 1 à 3
    
    /**
     * Constructeur
     */
    public LightBulb(){
        super();
        this.state = "on";
        this.color = "blanc";
        this.brightness = 2;
    }
    
    // getters and setters
    /**
     * Retourne la couleur de la lumière de l'ampoule
     * @return String
     */
    public String getColor(){
        return this.color;
    }
    
    /**
     * Met à jour la couleur de la lumière de l'ampoule
     * @param color 
     */
    public void setColor(String color){
        this.color = color;
    }
    
    /**
     * Retourne la luminosité de l'ampoule
     * @return int
     */
    public int getBrightness(){
        return this.brightness;
    }
    
    /**
     * Met à jour la luminosité de l'ampoule
     * @param brightness 
     */
    public void setBrightness(int brightness){
        this.brightness = brightness;
    }
    
    /**
     * Retourne l'état de l'appareil on ou off.
     * @return String
     */
    public String getState(){
        return this.state;
    }
    
    /**
     * Met à jour l'état de l'appareil on ou off
     * @param state
     */
    public void setState(String state){
        this.state = state;
    }
    
}
