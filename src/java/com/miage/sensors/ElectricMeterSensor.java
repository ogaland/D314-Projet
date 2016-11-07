/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.device.ElectricMeter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Capteur de compteur electrique
 * @author ko
 */
public class ElectricMeterSensor extends Sensor{
    private ElectricMeter electricMeter;
    
    /**
     * Constructeur
     * @param name
     * @param type
     * @param device 
     */
    public ElectricMeterSensor(String name, String type, ElectricMeter device) {
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
    public ElectricMeter getDevice(){
        return this.electricMeter;
    }
    
    /**
     * Enregistre le comportement de l'appareil (device) : compteur
     */
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
            consumption = this.getDevice().getCurrentConsumption();        
            //Enregistrement de la consommation dans la base de donnée du capteur.
            String requete = "INSERT INTO consumption_"+this.getId()+" VALUES(null,"+consumption+")";
            statement.execute(requete);
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    public void createDB() {
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
                    + " current_power INTEGER)";
            statement.execute(requete);
            statement.close();       
            connection.close();
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    /**
     * Renvoie l'information courante du capteur qui est le dernier
     * enregistrement de la table du capteur
     * @return String[]
     */
    @Override
    public String[] getInformations() {
        String[] infos = new String[1];
        try {
            Connection connection;
            //Connection à la base de donnée du capteur
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("capteur_"+this.getId()+".sqlite"));
            Statement statement = connection.createStatement();
            connection.setAutoCommit(true);
            //Selection du dernier enregistrement
            String requete = "SELECT MAX(id_reg) as last_reg, current_power FROM consumption_"+ this.getId();
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
    
    
}
