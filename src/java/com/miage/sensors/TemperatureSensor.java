/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOTemperatureSensor;
import com.miage.device.TemperatureDevice;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Capteur de température
 * @author ko
 */
public class TemperatureSensor extends Sensor implements Runnable{
    TemperatureDevice temperatureDevice;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public TemperatureSensor(String name, TemperatureDevice device) {
        super(name, "temperature");
        temperatureDevice = device;
        createDB(); 
    }
    
    //getters and setters
    /**
     * Met à jour l'appareil de chauffage du capteur
     * @param device 
     */
    public void setDevice(TemperatureDevice device){
        this.temperatureDevice = device;
    }
    
    /**
     * Retourne l'appareil de chauffage du capteur
     * @return LightBulb 
     */
    public TemperatureDevice getDevice(){
        return this.temperatureDevice;
    }

    @Override
    public void recordBehavior() {
        DAOTemperatureSensor DAOSensor = new DAOTemperatureSensor();    
        String state = this.getDevice().getState();
        int temperature = this.getDevice().getTemperature();
        int consumption = this.getDevice().getCurrentConsumption();
        DAOSensor.insert(this.getId(),state, temperature, consumption);
    }

    @Override
    public final void createDB() {
        DAOTemperatureSensor DAOSensor = new DAOTemperatureSensor();
        DAOSensor.createNewDatabase("capteur_"+this.getId()+".db");
        DAOSensor.createNewTable("capteur_"+this.getId());    
    }

    @Override
    public String [] getInformations() {
        DAOTemperatureSensor DAOSensor = new DAOTemperatureSensor();                 
        return DAOSensor.getLastRecord(this.getId());
    }

    @Override
    public void switchPower() {
        if(this.temperatureDevice.getState()=="on"){
            this.temperatureDevice.setState("off");
        }else{
            this.temperatureDevice.setState("on");
        } 
    }
    
    @Override
    public String toString(){
        String s; 
        s = "Capteur n° " + this.getId() 
                + "\n - Nom : " + this.getName()
                + "\n - Type : " + this.getType()
                + "\n - Etat : " + this.getDevice().getState()
                + "\n - Temperature : " + this.getDevice().getTemperature()
                + "\n - consommation : " + this.getDevice().getCurrentConsumption()  +" kW";     
        return s;
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
