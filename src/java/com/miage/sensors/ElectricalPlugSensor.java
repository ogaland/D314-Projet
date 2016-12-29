/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.dao.DAOElectricalPlugSensor;
import com.miage.device.ElectricalPlug;
import com.miage.device.SimulateDevice;
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
    public synchronized void recordBehavior() 
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
        String[] thisSensor = new String[4];
        thisSensor[0] = Integer.toString(this.getId());
        thisSensor[1] = ""; // Valeur de la date vide sinon il faut modifier la partie client ou le json ?
        thisSensor[2] = this.getDevice().getState();
        thisSensor[3] = Integer.toString(this.getDevice().getCurrentConsumption());
        return thisSensor;
    }
    
    /**
     * Change l'état de l'appareil : on ou off selon l'état courant
     */
    @Override
    public void switchPower() 
    {
       
        if(this.getDevice().getState().equals("on"))
        { 
            this.getDevice().setState("off");
            this.getDevice().setCurrentConsumption(0);
        }
        else if(this.getDevice().getState().equals("off"))
        {   
            this.getDevice().setState("on");
            /*Quand on rallume un capteur il faut relancer un Thread 
            pour la simulation et pour l'enregistrement en BD*/
            Thread t1record = new Thread(this);
            Thread t2Simulate = new Thread(new SimulateDevice(this.getDevice()));
            t2Simulate.start();
            t1record.start();                     
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
        while(this.getDevice().getState()=="on"){
            System.out.println("okRecord");
            recordBehavior();
            
            try 
            {   
                Thread.sleep(Sensor.SLEEP_TIME);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(ElectricMeterSensor.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }                
    }
           
}
   
