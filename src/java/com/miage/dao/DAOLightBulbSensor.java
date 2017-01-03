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
 *
 * @author User
 */
public class DAOLightBulbSensor extends DAOManager
{

    @Override
    public void createNewTable(String dbName)
    {
        
        // requete sql de creation de la table
        String requete = "CREATE TABLE IF NOT EXISTS consumption_"+ dbName + "("
                    + " id_reg INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " date TEXT NOT NULL,"
                    + " state TEXT NOT NULL,"
                    + " color TEXT NOT NULL,"
                    + " brightness int NOT NULL,"
                    + " current_power INTEGER)";       
        try (Connection conn = this.connect(dbName+".db");
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(requete);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public void insert(int idSensor, String state, String color, int brightness, int consumption) 
    {
        String dbFile ="capteur_"+idSensor+".db";
        
        String dbName ="consumption_capteur_"+idSensor;
        
        String requete = "INSERT INTO "+dbName+"(date, state, color, brightness, current_power)"
                + " VALUES(datetime('now','localtime'), ?, ?, ?, ?)";

        try (Connection conn = this.connect(dbFile);
                PreparedStatement pstmt = conn.prepareStatement(requete)) {
            pstmt.setString(1, state);
            pstmt.setString(2, color);
            pstmt.setInt(3, brightness);
            pstmt.setInt(4, consumption);
            pstmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
      
}
