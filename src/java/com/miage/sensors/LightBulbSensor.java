/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOLightBulbSensor;
import com.miage.device.LightBulb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Capteur d'ampoule
 * @author ko
 */
public class LightBulbSensor extends Sensor{
    private LightBulb lightBulb;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public LightBulbSensor(String name, LightBulb device) {
        super(name, "Ampoule");
        this.lightBulb = device; 
        createDB(); 
    }
    
    //getters and setters
    /**
     * Met à jour l'ampoule du capteur
     * @param device 
     */
    public void setDevice(LightBulb device){
        this.lightBulb = device;
    }
    
    /**
     * Retourne l'ampoule du capteur
     * @return LightBulb 
     */
    public LightBulb getDevice(){
        return this.lightBulb;
    }
       
    /**
     * Enregistre le comportement de l'appareil (device) : Ampoule
     */
    @Override
    public void recordBehavior() {
        DAOLightBulbSensor DAOSensor = new DAOLightBulbSensor();    
        String state = this.getDevice().getState();
        String color = this.getDevice().getColor();
        int brightness = this.getDevice().getBrightness();
        int consumption = this.getDevice().getCurrentConsumption();
        DAOSensor.insert(this.getId(),state, color, brightness, consumption);
    }

    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    public final void createDB() {
        DAOLightBulbSensor DAOSensor = new DAOLightBulbSensor();
        DAOSensor.createNewDatabase("capteur_"+this.getId()+".db");
        DAOSensor.createNewTable("capteur_"+this.getId());   
    }

    @Override
    public String[] getInformations() {
        DAOLightBulbSensor DAOSensor = new DAOLightBulbSensor();                 
        return DAOSensor.getLastRecord(this.getId());
    }

    /**
     * Change l'état de l'appareil : on ou off selon l'état courant
     */
    @Override
    public void switchPower() {
        if(this.lightBulb.getState()=="on"){
            this.lightBulb.setState("off");
        }else{
            this.lightBulb.setState("on");
        }      
    }
    
     /**
     * Retourne les informations du capteur.
     * @return String
     */
    @Override
    public String toString(){
        String s; 
        s = "Capteur n° " + this.getId() 
                + "\n - Nom : " + this.getName()
                + "\n - Type : " + this.getType()
                + "\n - Etat : " + this.getDevice().getState()
                + "\n - Couleur : " + this.getDevice().getColor()
                + "\n - Luminosité : " + this.getDevice().getBrightness()
                + "\n - consommation : " + this.getDevice().getCurrentConsumption()  +" kW";     
        return s;
    }
    
}
