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
    
    /**
     * Constructeur
     */
    public TemperatureDevice(){
        super();
        this.temperature = 20;
    }
    
    public int getTemperature(){
        return this.temperature;
    }
    
    public void setTemperature(int temperature){
        this.temperature = temperature;
    }
    
}
