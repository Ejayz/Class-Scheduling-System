/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIMPLE_RECORD.CLASSES;

import cs_system.DB_class.ConnectionManager;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class Add_Room {
    
    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;
    
    public int InserData(String UID, String Room, String Floor) {
        boolean res = false;
        int result=0;
        String sql = "INSERT into room_list (UNIQUE_ID,ROOM_NUMBER,FLOOR_NUMBER) values (?,?,?)";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, UID);
            stmt.setString(2, Room);
            stmt.setString(3, Floor);
            result = stmt.executeUpdate();
          
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void DisplayRoom(JTable table) {
        String sql = "select * from room_list";
        try {            
            int id = 1;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) table.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                dt.addRow(new Object[]{id++,rs.getString("ROOM_NUMBER"),rs.getString("FLOOR_NUMBER")});
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    public String getId(String room){
        String sql="Select * from room_list where ROOM_NUMBER=?";
      String UID="";
        try{
        stmt=conn.prepareStatement(sql);
       stmt.setString(1,room);
       rs=stmt.executeQuery();
       if (rs.next()){
           UID=rs.getString("UNIQUE_ID");
       }
       }
       catch(Exception e){
           System.out.println(e.getMessage());
       }
       return UID;
    }
}












