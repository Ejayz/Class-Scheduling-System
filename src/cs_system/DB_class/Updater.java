/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Administrator
 */
public class Updater {
    private String username;
    private String password;
    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private int result;
    private String query;
    private String error;
    
    
    public int UpdatePassword(){
      
        query="update `cs_system`.`credintials` set `PASSWORD` =? where `ID` = 1";
     try{
        
        connection=  ConnectionManager.getConnection();
      stmt=connection.prepareStatement(query);
      stmt.setString(1,password );
      
     int result=stmt.executeUpdate();
      
     
     }catch(Exception e){
         error=e.toString();
     }
     
    
     return result;
     
     
    }

   
    
    
    public int UpdateUsername(){
      
        query="update `cs_system`.`credintials` set `USERNAME` =? where `ID` = 1";
     try{
        
        connection=  ConnectionManager.getConnection();
      stmt=connection.prepareStatement(query);
      stmt.setString(1,username );
      
     int result=stmt.executeUpdate();
      
     
     }catch(Exception e){
         error=e.toString();
     }
     System.out.print(username);
    
     return result;
     
     
    }

    
    
    
    
    //This will enable to get data from this class to other class
     public String getError() {
        return error;
    }
    
    //Setter  for variables
    //Enable to  set data for other class
    
    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
