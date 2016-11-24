/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gère les accès en base de données des capteurs de type Compteur électrique
 * @author ko
 */
public class DAOElectricMeterSensor extends DAOManager {


    @Override
    public void createNewTable(String dbName) {
        
        
        // requete sql de creation de la table
        String requete = "CREATE TABLE IF NOT EXISTS consumption_"+ dbName + "("
                    + " id_reg INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " date TEXT NOT NULL,"
                    + " current_power INTEGER NOT NULL);";
        
        try (Connection conn = this.connect(dbName+".db");
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(requete);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int idSensor, int consumption) {
        String dbFile ="capteur_"+idSensor+".db";
        String dbName ="consumption_capteur_"+idSensor;
        
        
        String requete = "INSERT INTO "+dbName+"(date, current_power)"
                + "VALUES(datetime('now','localtime'),?)";
        try (Connection conn = this.connect(dbFile);
                PreparedStatement pstmt = conn.prepareStatement(requete)) {
            pstmt.setInt(1, consumption);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public String[] getLastRecord(int idSensor){
        String[] lastRecord = new String[3];
        
        String dbFile ="capteur_"+idSensor+".db";
        String tableName ="consumption_capteur_"+idSensor;

        String requete  = "SELECT MAX(id_reg), date, current_power FROM "+ tableName;
        
        try (Connection conn = this.connect(dbFile);
             Statement stmt  = conn.createStatement();
                ResultSet rs  = stmt.executeQuery(requete);){
            
            while(rs.next()){
                for(int i = 0; i<lastRecord.length ; i++){
                    lastRecord[i] = rs.getObject(i+1).toString();
                }
            }      
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lastRecord;
    }
    
}
