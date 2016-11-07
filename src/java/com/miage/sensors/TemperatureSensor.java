/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.device.TemperatureDevice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Capteur de température
 * @author ko
 */
public class TemperatureSensor extends Sensor{
    TemperatureDevice temperatureDevice;
    
    /**
     * Constructeur
     * @param name
     * @param type
     * @param device 
     */
    public TemperatureSensor(String name, String type, TemperatureDevice device) {
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
        try {
            //Connection à la base de donnée du capteur
            Connection connection;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("capteur_"+this.getId()+".sqlite"));
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            //Récupération de la consommation courante de l'appareil
            int consumption;
            if(this.getDevice().getState() == "on"){
                consumption = this.getDevice().getCurrentConsumption();
            }
            else{
                consumption = 0;
            }        
            //Enregistrement de la consommation dans la base de donnée du capteur.
            String requete = "INSERT INTO consumption_"+this.getId()+" VALUES(null,'"+this.getDevice().getState()
                    + "'," + this.getDevice().getTemperature()
                    + "," + consumption+")";
            statement.execute(requete);
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public final void createDB() {
        Connection connection;
        try {
            //Création de la base de donnée du capteur
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("capteur_"+this.getId()+".sqlite"));
            Statement statement = connection.createStatement();
            connection.setAutoCommit(true);
            //Création de la table des enregistrements du capteur
            String requete = "CREATE TABLE IF NOT EXISTS consumption_"+ this.getId() + "("
                    + " id_reg INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " state TEXT NOT NULL,"
                    + " temperature int NOT NULL,"
                    + " current_power INTEGER)";
            statement.execute(requete);
            statement.close();       
            connection.close();
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    public String[] getInformations() {
        String[] infos = new String[3];
        try {
            Connection connection;
            //Connection à la base de donnée du capteur
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("capteur_"+this.getId()+".sqlite"));
            Statement statement = connection.createStatement();
            connection.setAutoCommit(true);
            //Selection du dernier enregistrement
            String requete = "SELECT MAX(id_reg) as last_reg, state, temperature, current_power FROM consumption_" + this.getId();
            ResultSet resultat = statement.executeQuery(requete);       
            while(resultat.next()){
                for(int i = 0; i<infos.length ; i++){
                    infos[i] = resultat.getObject(i+1).toString();
                } 
            }
            statement.close();       
            connection.close();
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }             
        return infos;
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
    
}
