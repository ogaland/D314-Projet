/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.sensors;

import com.miage.device.Device;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ko
 */
public class ElectricalPlugSensor extends Sensor {

    public ElectricalPlugSensor(String name, String type, Device device) {
        super(name,"prise",device);
        createDB(name);       
    }

    @Override
    void recordBehavior() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Créer une table associée au capteur dans la base de donnée
     */
    @Override
    final void createDB(String name) {
        //String chemin = this.getClass().getResource("").getPath();
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.getClass().getResource("database.sqlite"));
            Statement statement = connection.createStatement();
            String requete = "CREATE TABLE IF NOT EXISTS "+ name + "("        
                    + " id_sensor INTEGER NOT NULL,"
                    + " sensor_name TEXT NOT NULL,"
                    + " sensor_type TEXT NOT NULL,"       
                    + " current_power INTEGER)";
            statement.execute(requete);
            System.out.println(requete);
            statement.close();            
            connection.close();
        }catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ElectricalPlugSensor.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @Override
    String[] getInformations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
