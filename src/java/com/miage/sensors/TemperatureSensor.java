/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOTemperatureSensor;
import com.miage.device.SimulateDevice;
import com.miage.device.TemperatureDevice;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Capteur de température
 * @author ko
 */
public class TemperatureSensor extends Sensor implements Runnable
{
    TemperatureDevice temperatureDevice;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public TemperatureSensor(String name, TemperatureDevice device) 
    {
        super(name, "Temperature");
        temperatureDevice = device;
        createDB(); 
    }
    
    //getters and setters
    /**
     * Met à jour l'appareil de chauffage du capteur
     * @param device 
     */
    public void setDevice(TemperatureDevice device)
    {
        this.temperatureDevice = device;
    }
    
    /**
     * Retourne l'appareil de chauffage du capteur
     * @return LightBulb 
     */
    @Override
    public TemperatureDevice getDevice()
    {
        return this.temperatureDevice;
    }

    @Override
    public void recordBehavior() 
    {
        DAOTemperatureSensor DAOSensor = new DAOTemperatureSensor();    
        String state = this.getDevice().getState();
        int temperature = this.getDevice().getTemperature();
        int consumption = this.getDevice().getCurrentConsumption();
        DAOSensor.insert(this.getId(),state, temperature, consumption);
    }

    @Override
    public final void createDB() 
    {
        DAOTemperatureSensor DAOSensor = new DAOTemperatureSensor();
        DAOSensor.createNewDatabase("capteur_"+this.getId()+".db");
        DAOSensor.createNewTable("capteur_"+this.getId());    
    }

    @Override
    public String [] getInformations() 
    {   
        String[] thisSensor = new String[5];
        thisSensor[0] = Integer.toString(this.getId());
        thisSensor[1] = ""; // Valeur de la date vide sinon il faut modifier la partie client ou le json ?
        thisSensor[2] = this.getDevice().getState();
        thisSensor[3] = Integer.toString(this.getDevice().getTemperature());
        thisSensor[4] = Integer.toString(this.getDevice().getCurrentConsumption());
        return thisSensor;
    }

    @Override
    public void switchPower() 
    {
        if(this.temperatureDevice.getState().equals("on"))
        {
            System.out.print("Temperature Device switching ");
            this.temperatureDevice.setState("off");
            this.getDevice().setCurrentConsumption(0);
        }
        else
        {
            System.out.print("Temperature Device switching ");
            this.temperatureDevice.setState("on");
            Thread t = new Thread(new SimulateDevice(this.getDevice()));
            t.start();
            this.recordBehavior();
        } 
    }
    
    @Override
    public String toString()
    {
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
    public void run() 
    {
        while(this.getDevice().getState().equals("on"))
        {
            recordBehavior();
            try 
            {
                Thread.sleep(Sensor.SLEEP_TIME);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(ElectricMeterSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
