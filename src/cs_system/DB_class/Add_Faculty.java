/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class Add_Faculty {

    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;

    String ClassStart;
    String ClassEnd;
    String ClassRoom;

    //Load Schedules to Available Scheds combo box
    public boolean LoadScheds(JComboBox dropdown, String Day) {
        String sql = "select * from room where IS_EXIST =? AND IS_USED_FACULTY=? AND DAY=? ";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            stmt.setString(2, "false");
            stmt.setString(3, Day);
            rs = stmt.executeQuery();
            dropdown.removeAllItems();
            while (rs.next()) {
                dropdown.addItem(rs.getString("START") + "-" + rs.getString("END") + "-" + rs.getString("ROOM"));
                result = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
//This will split schedule into 3 data the START,END ROOM which is using - as seperator on original string

    public void Splitter(String sched) {
        try {
            Scanner scann = new Scanner(sched);
            scann.useDelimiter("-");
            this.ClassStart = scann.next();
            this.ClassEnd = scann.next();
            this.ClassRoom = scann.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//Add data to database Uses parameter to get data from frame to this class Return 0 if failed other wise 1

    public int Add_Data(String SECTION, String DEPARTMENT, String DAY, String COURSE_TITLE) {
        String sql = "insert into faculty (SECTION,COURSE_TITLE,DEPARTMENT,DAYS,START,END,ROOM,IS_EXIST) VALUES (?,?,?,?,?,?,?,?)";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, SECTION.toUpperCase());
            stmt.setString(2, COURSE_TITLE.toUpperCase());
            stmt.setString(3, DEPARTMENT.toUpperCase());
            stmt.setString(4, DAY);
            stmt.setString(5, this.ClassStart);
            stmt.setString(6, this.ClassEnd);
            stmt.setString(7, this.ClassRoom);
            stmt.setString(8, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Udate IS_USED_Faculty when adding data to true returns 0 if fail other whise 1
    public int UpdateIs_Used_Faculty(String DAY, String Used) {
        String sql = "update room set IS_USED_FACULTY=? WHERE DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Used);
            stmt.setString(2, DAY);
            stmt.setString(3, this.ClassStart);
            stmt.setString(4, this.ClassEnd);
            stmt.setString(5, this.ClassRoom);
            stmt.setString(6, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    //Display Data through table when ever new action is done (ADD UPDATE DELETE OR JUST OPENING THE PAGE)
    public void DisplayData(JTable table) {
        String sql = "select * from faculty where IS_EXIST=?";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {
                dm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("COURSE_TITLE"), rs.getString("DEPARTMENT"), rs.getString("DAYS"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM")});

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Update existing data to data base return 0 if false and 1 if true
    public int Update_Data(String SECTION, String DEPARTMENT, String DAY, String COURSE_TITLE, String Days, String Start, String End, String Room) {
        String sql = "update faculty set SECTION=?, COURSE_TITLE=?,DEPARTMENT=?,DAYS=?,START=?,END=?,ROOM=?,IS_EXIST=? Where DAYS=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=? ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, SECTION.toUpperCase());
            stmt.setString(2, COURSE_TITLE.toUpperCase());
            stmt.setString(3, DEPARTMENT.toUpperCase());
            stmt.setString(4, DAY);
            stmt.setString(5, this.ClassStart);
            stmt.setString(6, this.ClassEnd);
            stmt.setString(7, this.ClassRoom);
            stmt.setString(8, "true");
            stmt.setString(9, Days);
            stmt.setString(10, Start);
            stmt.setString(11, End);
            stmt.setString(12, Room);
            stmt.setString(13, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //Udate IS_USED_Faculty back to false if data sched is changed to true returns 0 if fail other whise 1
    public int UpdateIs_Used_Faculty_Changed(String DAY, String Start, String End, String Room, String Used) {
        String sql = "update room set IS_USED_FACULTY=? WHERE DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Used);
            stmt.setString(2, DAY);
            stmt.setString(3, Start);
            stmt.setString(4, End);
            stmt.setString(5, Room);
            stmt.setString(6, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    //This will delete data though the application but replacing IS_EXIST to false so it wont be displayed return 0 if false otherwise 1
    public int DeleteData(String Day) {
        String sql = "update faculty set IS_EXIST =? where DAYS=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setString(2, Day);
            stmt.setString(3, this.ClassStart);
            stmt.setString(4, this.ClassEnd);
            stmt.setString(5, this.ClassRoom);
            stmt.setString(6, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int SearchData(String search, JTable table) {
        String sql = "select * from faculty where IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            int id = 0;
            dm.setRowCount(0);
            while (rs.next()) {
                if (rs.getString("SECTION").contains(search)
                        || rs.getString("COURSE_TITLE").contains(search)
                        || rs.getString("DEPARTMENT").contains(search)
                        || rs.getString("DAYS").contains(search)
                        || rs.getString("START").contains(search)
                        || rs.getString("END").contains(search)
                        || rs.getString("ROOM").contains(search)) {

                    dm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("COURSE_TITLE"), rs.getString("DEPARTMENT"), rs.getString("DAYS"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM")});
                    result = 1;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
