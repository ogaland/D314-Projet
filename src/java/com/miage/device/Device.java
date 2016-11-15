package com.miage.device;

import java.util.Random;

/**
 *
 * @author ko
 */
public class Device {
    //Attributs
    //Permet d'autoincrémenter idDevice
    private static int idDevices = 0;
    private int id;
    private int currentConsumption;//Consommation courante
    private String state; // on - off
    
    /**
     * Constructeur
     * L'appareil connecté est mis en marche à l'instanciation
     */
    public Device(){
        this.id = ++idDevices;
        this.state = "on";
    }
    
    //getters and setters 
    
    public String getState(){
        return this.state;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    /**
     * Retourne l'id de l'appareil
     * @return int
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * Met à jour l'id de l'appareil
     * @param id 
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * La consommation courante de l'appareil.
     * @return int
     */
    public int getCurrentConsumption(){
        return this.currentConsumption;
    }
    
    /**
     * Met à jour la consommation courante de l'appareil.
     * @param currentConsumption 
     */
    public void setCurrentConsumption(int currentConsumption){
        this.currentConsumption = currentConsumption;  
    }
    
}
