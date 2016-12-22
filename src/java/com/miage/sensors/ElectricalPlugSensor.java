/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOElectricalPlugSensor;
import com.miage.device.ElectricalPlug;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Capteur de prise électrique
 * @author ko
 */
public class ElectricalPlugSensor extends Sensor implements Runnable
{
    private ElectricalPlug electricalPlug;
    
    /**
     * Constructeur
     * @param name
     * @param device 
     */
    public ElectricalPlugSensor(String name, ElectricalPlug device)
    {
        super(name,"Prise");
        this.electricalPlug = device;
        createDB();       
    }
    
    //getters and setters
    /**
     * Met à jour la prise électrique du capteur
     * @param device 
     */
    public void setDevice(ElectricalPlug device)
    {
        this.electricalPlug = device;
    }
    
    /**
     * Retourne la prise électrique du capteur
     * @return ElectricalPlug 
     */
    @Override
    public ElectricalPlug getDevice()
    {
        return this.electricalPlug;
    }
    
    /**
     * Enregistre le comportement de l'appareil (device) : prise
     */
    @Override
    public void recordBehavior() 
    {    
        DAOElectricalPlugSensor DAOSensor = new DAOElectricalPlugSensor();
        int consumption = this.getDevice().getCurrentConsumption();
        String state = this.getDevice().getState();
        DAOSensor.insert(this.getId(),state, consumption);
    }

    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    public final void createDB() 
    {
        DAOElectricalPlugSensor DAOSensor = new DAOElectricalPlugSensor();
        DAOSensor.createNewDatabase("capteur_"+this.getId()+".db");
        DAOSensor.createNewTable("capteur_"+this.getId());
    }
    
    /**
     * Renvoie l'information courante du capteur qui est le dernier
     * enregistrement de la table du capteur
     * @return String[]
     */
    @Override
    public String[] getInformations() 
    {
        DAOElectricalPlugSensor DAOSensor = new DAOElectricalPlugSensor();                 
        return DAOSensor.getLastRecord(this.getId());
    }
    
    /**
     * Change l'état de l'appareil : on ou off selon l'état courant
     */
    @Override
    public void switchPower() 
    {
        if(this.electricalPlug.getState().equals("on"))
        {
            this.electricalPlug.setState("off");
        }
        else
        {
            this.electricalPlug.setState("on");
        }      
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
                + "\n - nom : " + this.getName()
                + "\n - type : " + this.getType()
                + "\n - State : " + this.getDevice().getState()
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
