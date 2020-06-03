/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIMPLE_RECORD.CLASSES;

import cs_system.DB_class.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrator
 */
public class Add_Teachers {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement stmt;
    
    public int AddTeacher(String UnId, String fname, String mname, String lname, String dept) {
        int res = 0;
        String query = "INSERT INTO `cs_system`.`teachers_list` ( `UNIQ_ID`, `FNAME`, `MNAME`, `LNAME`, `DEPT`, `IS_ACTIVE`) VALUES (?,?,?,?,?, 'yes') ";
        try {
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, UnId);
            stmt.setString(2, fname);
            stmt.setString(3, mname);
            stmt.setString(4, lname);
            stmt.setString(5, dept);
            
            res = stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.print(e);
        }        
        
        return res;
        
    }
    
    public void DisplayData(JTable tb) {
        String sql = "select * from teachers_list";
        try {
            
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            DefaultTableModel dt = (DefaultTableModel) tb.getModel();
            dt.setRowCount(0);
            while (rs.next()) {
                String FullName = rs.getString("LNAME") + ", " + rs.getString("FNAME") + " " + rs.getString("MNAME");
                dt.addRow(new Object[]{rs.getInt(1), FullName});
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public String getID(String name) {
        String ID = "";
        String sql = "Select * from teachers_list";
        try {
            
            conn = ConnectionManager.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String FullName = rs.getString("LNAME") + ", " + rs.getString("FNAME") + " " + rs.getString("MNAME");
                
                if (FullName.equals(name)) {
                    ID = rs.getString("UNIQ_ID");
                    break;
                } else {
                    ID = "";
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ID;
    }
}
