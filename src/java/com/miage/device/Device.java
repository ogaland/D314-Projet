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
        this.currentConsumption = rand();
    }
    
    //getters and setters    
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
    
    public final int rand(){
        Random r = new Random();
        return (1 + r.nextInt(1000 - 1));
    }
    
    /**
     * Simule le comportement de l'appareil.
     * Enregistre les valeurs quelque part
     * NON TERMINEE thread / timer ?
     */
    public void simulateDevice(){
        rand();
    }
}
