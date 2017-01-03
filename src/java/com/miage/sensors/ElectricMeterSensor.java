/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOElectricMeterSensor;
import com.miage.device.Device;


/**
 * Capteur de compteur electrique
 * @author ko
 */
public class ElectricMeterSensor extends Sensor
{
    private Device electricMeter;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public ElectricMeterSensor(String name, Device device) 
    {
        super(name, "Compteur");
        this.electricMeter = device;
        createDB(); 
    }
    
    //getters and setters
    /**
     * Met à jour la prise électrique du capteur
     * @param device 
     */
    public void setDevice(Device device)
    {
        this.electricMeter = device;
    }
    
    /**
     * Retourne la prise électrique du capteur
     * @return ElectricMeter 
     */
    @Override
    public Device getDevice()
    {
        return this.electricMeter;
    }
    
    /**
     * Enregistre le comportement de l'appareil (device) : compteur
     */
    @Override
    public synchronized void  recordBehavior()
    {
        DAOElectricMeterSensor DAOSensor = new DAOElectricMeterSensor();
        int consumption = this.getDevice().getCurrentConsumption();
        DAOSensor.insert(this.getId(), consumption);
    }
    
    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    public final void createDB() 
    {      
        DAOElectricMeterSensor DAOSensor = new DAOElectricMeterSensor();
        DAOSensor.createNewDatabase("capteur_"+this.getId()+".db");
        DAOSensor.createNewTable("capteur_"+this.getId());          
    }
    
    /**
     * Renvoie l'information courante du capteur qui est le dernier
     * enregistrement de la table du capteur
     * @return String[]
     */
    @Override
    public synchronized String[]  getInformations()
    {
        String[] thisSensor = new String[3];
        thisSensor[0] = Integer.toString(this.getId());
        thisSensor[1] = ""; // Valeur de la date vide sinon il faut modifier la partie client ou le json ?
        thisSensor[2] = Integer.toString(this.getDevice().getCurrentConsumption());
        return thisSensor;
    }

    
     /**
     * Retourne les informations du capteur.
     * @return String
     */
    @Override
    public String toString()
    {
        String s; 
        s = "Capteur n° " + this.getId() 
                + " - nom : " + this.getName()
                + " - type : " + this.getType()
                + " - consommation : " + this.getDevice().getCurrentConsumption()  +" kW";     
        return s;
    }

    @Override
    public void switchPower() 
    {//Non implémentée car on ne coupe pas le compteur électrique
        throw new UnsupportedOperationException("Not supported yet."); 
    }   
}
