package com.miage.sensors;

import com.miage.dao.DAOManager;
import com.miage.device.Device;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe abstraite capteur
 * @author ko
 */
public abstract class Sensor implements Runnable
{
    
     //Attributs
    public static final int SLEEP_TIME = 5000;
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
    Sensor(String name, String type)
    {
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
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Met à jour le nom du capteur.
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Retourne le type du capteur.
     * @return String
     */
    public String getType()
    {
        return this.type;
    }
    
    /**
     * Met à jour le type du capteur.
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * Retourne l'id du capteur.
     * @return int 
     */
    public int getId()
    {
        return this.id;
    }
    
    public ArrayList<String> getStats(String beginDate, String endDate){
        DAOManager DAOSensor = new DAOManager() {
            @Override
            public void createNewTable(String dbName) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        ArrayList<String> stats = DAOSensor.getStats(this.getId(),beginDate, endDate);
        return stats;
    }
    
    @Override
    public void run() {
        while(this.getDevice().getState().equals("on"))
        {
            recordBehavior();
            try 
            {
                Thread.sleep(Sensor.SLEEP_TIME);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Méthodes abstraites implémentées dans les classes filles
     */
  
    public abstract void switchPower();
    
    /**
     * Enregistre la consommation courante de l'appareil (Device) connecté au capteur
     */
   public abstract void recordBehavior();
    /**
     * Crée une base de donnée du capteur quand le capteur n'existe pas.
     * Implémentée dans les classes filles.
     */
    public abstract void createDB();
    
    /**
     * Retourne les informations enregistrées par le capteur.
     * @return String
     */
    public abstract String[] getInformations();
    
 
    /**
     * Retourne l'appareil associé au capteur
     * @return Device 
     */
    public abstract Device getDevice();

    
}
