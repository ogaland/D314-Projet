package com.miage.sensors;

import com.miage.device.Device;

/**
 * Classe abstraite capteur
 * @author ko
 */
public abstract class Sensor {
    
     //Attributs
    private static int idSensors = 0;
    private final int id;
    private String name;
    private Device device;
    private String type;
    
    /**
     * Instancie le capteur avec un nom et un appareil.
     * @param name
     * @param type
     * @param device
     */
    Sensor(String name, String type, Device device){
        //À chaque instanciation d'un capteur on lui attribut un id par auto-incrémentation
        this.id = ++idSensors;
        this.name = name;
        this.device = device;
        this.type = type; // prise, ampoule, chauffage, compteur.
    }
    
    //Getters and setters   
    /**
     * Retourne le nom du capteur.
     * @return String
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Met à jour le nom du capteur.
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Retourne le type du capteur.
     * @return String
     */
    public String getType(){
        return this.type;
    }
    
    /**
     * Met à jour le type du capteur.
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Retourne l'id du capteur.
     * @return int 
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * Retourne l'appareil associé au capteur.
     * @return Device
     */
    public Device getDevice(){
        return this.device;
    }
    
    /**
     * Met à jour l'appareil du capteur
     * @param device 
     */
    public void setDevice(Device device){
        this.device = device;
    }
    
    public void switchPower(){
        this.device.setState("off");
    }
    
    /**
     * Retourne les informations du capteur.
     * @return String
     */
    @Override
    public String toString(){
        String s; 
        s = " - Consommation : "
            + "Capteur n° " + this.getId() 
                + " - nom : " + this.getName()
                + " - type : " + this.getType()
                + " - State : " + this.getDevice().getState()  +"\n" + this.getDevice().getCurrentConsumption()  +" kW";     
        return s;
    }
    
    //Méthodes abstraites implémentées dans les classes filles
    /**
     * Enregistre la consommation courante de l'appareil (Device) connecté au capteur
     */
    abstract void recordBehavior();
    /**
     * Crée une base de donnée du capteur.
     * Implémentée dans les classes filles.
     * @param name : nom du capteur
     */
    abstract void createDB(String name);
    
    /**
     * Retourne les informations enregistrées par le capteur.
     * (à revoir le type de retour et s'il faut pas mettre des paramètres)
     * @return String
     */
    abstract String[] getInformations();  
    
}
