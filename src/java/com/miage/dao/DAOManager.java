
package com.miage.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ko
 */
public abstract class DAOManager {    
    /**
     * Connect to a sample database
     * @param dbName
     */
    protected Connection connect(String dbName) {
        // SQLite connection string
        String url = "jdbc:sqlite:"+dbName;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    /**
     * Create a new data base
     * @param fileName nom du fichier
     */
    public void createNewDatabase(String fileName) {
 
        try (Connection conn = this.connect(fileName)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Récupère les consommations entre deux dates données
     * @param idSensor
     * @param beginDate
     * @param endDate
     * @return 
     */
    public ArrayList<String> getStats(int idSensor, String beginDate, String endDate){
        
        String dbFile ="capteur_"+idSensor+".db";
        String tableName ="consumption_capteur_"+idSensor;
        ArrayList<String> stats;
        stats = new ArrayList<String>();
         
        String requete  = "SELECT current_power FROM "
                + tableName+" WHERE date >= '"+beginDate+"' AND date <= '"+endDate+"'";
        
        try (Connection conn = this.connect(dbFile);
            Statement stmt  = conn.createStatement();
            ResultSet rs  = stmt.executeQuery(requete);){
            
            while(rs.next()){
                stats.add(String.valueOf(rs.getInt("current_power")));
            }      
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        return stats;
    }
    
    abstract public void createNewTable(String dbName);

    
}