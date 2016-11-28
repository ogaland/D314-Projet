
package com.miage.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ko
 */
abstract class DAOManager {    
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
    
    abstract public void createNewTable(String dbName);
    
    abstract public String[] getLastRecord(int idSensor);

    
}
