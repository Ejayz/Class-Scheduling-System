/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class Add_Section {

    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;

    String Start;
    String End;
    String Room;

    public String Load_Data(String Sched, String Day) {
        String[] sched = Sched.split("-");
        String Remark = "";
        String sql = "select * from room where DAY=? AND START =? AND END =? AND ROOM=? AND IS_EXIST=? ";
        System.out.println(sched[0]);
        System.out.println(sched[1]);
        System.out.println(sched[2]);
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Day);
            stmt.setString(2, sched[0]);
            stmt.setString(3, sched[1]);
            stmt.setString(4, sched[2]);
            stmt.setString(5, "true");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Remark = rs.getString("TEACHER");
            }
        } catch (Exception e) {
          Remark="";
        }
        return Remark;
    }

    public boolean Section_Loader(JComboBox section) {
        boolean result = false;
        String sql = "Select DISTINCT SECTION from room where IS_USED_ROOM='false' and IS_EXIST='true'";
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            section.removeAllItems();
            section.insertItemAt("--SECTION--", 0);
            section.setSelectedIndex(0);
            while (rs.next()) {
                section.addItem(rs.getString("SECTION"));
                result = true;
            }

        } catch (Exception e) {
            System.out.println("This is an error on Section Loader");
            e.printStackTrace();
        }

        return result;
    }

    public boolean ChoiceLoad(String day, JComboBox end, String section) {
        String sql = "Select * from room where  DAY=? AND IS_USED_ROOM=? AND IS_EXIST=? AND SECTION=? ";
        boolean result = false;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, day);
            stmt.setString(2, "false");
            stmt.setString(3, "true");
            stmt.setString(4, section);
            rs = stmt.executeQuery();

            end.removeAllItems();

            while (rs.next()) {
                end.addItem(rs.getString("START") + "-" + rs.getString("END") + "-" + rs.getString("ROOM"));
                result = true;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void Splitter(String Data) {
        Scanner sc = new Scanner(Data);
        sc.useDelimiter("-");
        Start = sc.next();
        End = sc.next();
        Room = sc.next();

    }

    public int AddData(String section, String SubjectCode, String Description, String Department, String SUnit, String Tunits, String Days, String numberstudent, String Remark) {
        String sql = "INSERT INTO section (SECTION,SUBJECT_CODE,DESCRIPTION,DEPARTMENT,SUNITS,TUNITS,DAY,START,END,ROOM,STUDENT_NUMBR,REMARK,IS_EXIST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, section);
            stmt.setString(2, SubjectCode);
            stmt.setString(3, Description);
            stmt.setString(4, Department);
            stmt.setString(5, SUnit);
            stmt.setString(6, Tunits);
            stmt.setString(7, Days);
            stmt.setString(8, this.Start);
            stmt.setString(9, this.End);
            stmt.setString(10, this.Room);
            stmt.setString(11, numberstudent);
            stmt.setString(12, Remark);
            stmt.setString(13, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean ValidateInsert(String Day, String Start, String End, String Room) {
        String sql = "select * from section where DAY=? AND START =? AND END =? AND ROOM=? AND IS_EXIST=? ";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Day);
            stmt.setString(2, this.Start);
            stmt.setString(3, this.End);
            stmt.setString(4, this.Room);
            stmt.setString(5, "true");
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = true;

            } else {
                result = false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int UpdateRoom(String day, String Is_Exist) {
        String sql = "update room set IS_USED_ROOM =? where DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            stmt.setString(2, day);
            stmt.setString(3, Start);
            stmt.setString(4, End);
            stmt.setString(5, Room);
            stmt.setString(6, Is_Exist);

            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void DisplayData(JTable table) {
        String sql = "select * from section where IS_EXIST=?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {

                dm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("SUBJECT_CODE"), rs.getString("DESCRIPTION"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("DAY"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("STUDENT_NUMBR"), rs.getString("REMARK")});

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public int UpdateData(String section, String SubjectCode, String Description, String Department, String SUnit, String Tunits, String Days, String numberstudent, String Remark, String Day1, String Start1, String End1, String Room1) {
        String sql = "update section set SECTION=?,SUBJECT_CODE=?,DESCRIPTION=?,DEPARTMENT=?,SUNITS=?,TUNITS=?,DAY=?,START=?,END=?,ROOM=?,STUDENT_NUMBR=?,REMARK=?,IS_EXIST=? where DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, section);
            stmt.setString(2, SubjectCode);
            stmt.setString(3, Description);
            stmt.setString(4, Department);
            stmt.setString(5, SUnit);
            stmt.setString(6, Tunits);
            stmt.setString(7, Days);
            stmt.setString(8, this.Start);
            stmt.setString(9, this.End);
            stmt.setString(10, this.Room);
            stmt.setString(11, numberstudent);
            stmt.setString(12, Remark);
            stmt.setString(13, "true");
            stmt.setString(14, Day1);
            stmt.setString(15, Start1);
            stmt.setString(16, End1);
            stmt.setString(17, Room1);
            stmt.setString(18, "true");
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;

    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getRoom() {
        return Room;
    }

    public int updateRoom(String Day, String Start, String End, String Room) {
        String sql = "update room set IS_USED_ROOM=? WHERE DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=? ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setString(2, Day);
            stmt.setString(3, Start);
            stmt.setString(4, End);
            stmt.setString(5, Room);
            stmt.setString(6, "true");
            System.out.println(Day);
            System.out.println(Start);
            System.out.println(End);
            System.out.println(Room);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int DeleteData(String Day, String Start, String End, String Room) {
        String sql = "update section set IS_EXIST=? WHERE DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=? ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setString(2, Day);
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

    public boolean SearchSection(JTable table, String search) {
        String sql = "select * from section where IS_EXIST=?";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;

            while (rs.next()) {
                if (rs.getString("SECTION").contains(search.toUpperCase())
                        || rs.getString("SUBJECT_CODE").contains(search.toUpperCase())
                        || rs.getString("DESCRIPTION").contains(search.toUpperCase())
                        || rs.getString("DEPARTMENT").contains(search.toUpperCase())
                        || rs.getString("SUNITS").contains(search.toUpperCase())
                        || rs.getString("TUNITS").contains(search.toUpperCase())
                        || rs.getString("DAY").contains(search.toUpperCase())
                        || rs.getString("START").contains(search.toUpperCase())
                        || rs.getString("END").contains(search.toUpperCase())
                        || rs.getString("ROOM").contains(search.toUpperCase())
                        || rs.getString("STUDENT_NUMBR").contains(search.toUpperCase())
                        || rs.getString("REMARK").contains(search.toUpperCase())) {
                    
                    dm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("SUBJECT_CODE"), rs.getString("DESCRIPTION"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("DAY"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("STUDENT_NUMBR"), rs.getString("REMARK")});
result = true;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int LoadDataExport(JComboBox combo) {
        String sql = "SELECT DISTINCT SECTION FROM section WHERE IS_EXIST='true'";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            combo.insertItemAt("Choose Section", 0);
            combo.setSelectedIndex(0);
            while (rs.next()) {
                combo.addItem(rs.getString("SECTION"));
                result = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void DisplayPrintableData(JTable table, String section) {
        String sql = "select * from section where SECTION=? AND IS_EXIST=?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, section);
            stmt.setString(2, "true");
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {

                dm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("SUBJECT_CODE"), rs.getString("DESCRIPTION"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("DAY"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("STUDENT_NUMBR"), rs.getString("REMARK")});

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
