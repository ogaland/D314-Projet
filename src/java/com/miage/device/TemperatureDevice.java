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
public class TemperatureDevice extends Device{
    private int temperature;
    private String state;
    
    /**
     * Constructeur
     */
    public TemperatureDevice(){
        super();
        this.state = "on";
        this.temperature = 20;
    }
    
    public int getTemperature(){
        return this.temperature;
    }
    
    public void setTemperature(int temperature){
        this.temperature = temperature;
    }
    
    public String getState(){
        return this.state;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
}
