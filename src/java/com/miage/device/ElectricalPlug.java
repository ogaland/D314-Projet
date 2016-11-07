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
public class ElectricalPlug extends Device{
    private String state;
    
    /**
     * Constructeur
     */
    public ElectricalPlug(){
        super();
        this.state = "on";
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
