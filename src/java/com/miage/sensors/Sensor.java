package com.miage.sensors;

/**
 * Classe abstraite capteur
 * @author ko
 */
public abstract class Sensor {
    
     //Attributs
    private static int idSensors = 0;
    private final int id;
    private String name;
    private String type;
    
    /**
     * Instancie le capteur avec un nom et un appareil.
     * @param name
     * @param type
     * @param device
     */
    Sensor(String name, String type){
        //À chaque instanciation d'un capteur on lui attribut un id par auto-incrémentation
        this.id = ++idSensors;
        this.name = name;
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
    
    

    
    //Méthodes abstraites implémentées dans les classes filles
    
    public abstract void switchPower();
    
    /**
     * Enregistre la consommation courante de l'appareil (Device) connecté au capteur
     */
   public abstract void recordBehavior();
    /**
     * Crée une base de donnée du capteur.
     * Implémentée dans les classes filles.
     */
    public abstract void createDB();
    
    /**
     * Retourne les informations enregistrées par le capteur.
     * @return String
     */
    public abstract String[] getInformations();  
    
}
