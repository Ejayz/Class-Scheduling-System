/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Setup {
//This will be a connection requirment to connect to the database 
//See ConnectionManager class in BD_class package for method you can use in connection manager
    
    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;
//This will update the setup table in mysql if you will accept the term and condition
    public int IsAccept() {
    //Sql Query to be passed to PrepareStatement Method
        String sql = "update setup set IS_SETUP='true', IS_ACCEPT='true' where ID='1' ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
          
            result = stmt.executeUpdate();
        } catch (Exception ex) {
            //Print exception error
            ex.printStackTrace();
        }

        return result;
    }
//This will check if you already done the setup.You dont want to setup files and database always 
    //This will return 0 if false and 1 if true
    public int checkSetUp() {
        String sql = "select * from setup where IS_ACCEPT='true' AND IS_SETUP='true'";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = 1;
            }
        } catch (Exception ex) {
            return 0;
        }
        return result;
    }
}
