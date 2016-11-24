/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOElectricMeterSensor;
import com.miage.device.ElectricMeter;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Capteur de compteur electrique
 * @author ko
 */
public class ElectricMeterSensor extends Sensor implements Runnable{
    private ElectricMeter electricMeter;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public ElectricMeterSensor(String name, ElectricMeter device) {
        super(name, "Compteur");
        this.electricMeter = device;
        createDB(); 
        
    }
    
    //getters and setters
    /**
     * Met à jour la prise électrique du capteur
     * @param device 
     */
    public void setDevice(ElectricMeter device){
        this.electricMeter = device;
    }
    
    /**
     * Retourne la prise électrique du capteur
     * @return ElectricMeter 
     */
    @Override
    public ElectricMeter getDevice(){
        return this.electricMeter;
    }
    
    /**
     * Enregistre le comportement de l'appareil (device) : compteur
     */
    @Override
    public synchronized void  recordBehavior() {
        
        DAOElectricMeterSensor DAOSensor = new DAOElectricMeterSensor();
        int consumption = this.getDevice().getCurrentConsumption();
        DAOSensor.insert(this.getId(), consumption);
      
    }
    
    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    public final void createDB() {      
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
    public synchronized String[]  getInformations() {
        DAOElectricMeterSensor DAOSensor = new DAOElectricMeterSensor();                 
        return DAOSensor.getLastRecord(this.getId());
    }

    
     /**
     * Retourne les informations du capteur.
     * @return String
     */
    @Override
    public String toString(){
        String s; 
        s = "Capteur n° " + this.getId() 
                + " - nom : " + this.getName()
                + " - type : " + this.getType()
                + " - consommation : " + this.getDevice().getCurrentConsumption()  +" kW";     
        return s;
    }

    @Override
    public void switchPower() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while(this.getDevice().getState()=="on"){
            recordBehavior();
            try {
                sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ElectricMeterSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
