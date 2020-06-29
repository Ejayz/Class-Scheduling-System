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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author monica hancock
 */
public class Add_Teacher {

    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;

    String Start;
    String End;
    String Room;

    public boolean LoadThings(JTextField SECTION, JTextField TUNITS, JTextField SUNITS, String DAY, String START, String END, String ROOM) {
        String sql = "select * from room where DAY=? AND START=? AND END=? AND ROOM=? IS_EXIST='true' AND IS_USED_TEACHER='false' ";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, DAY);
            stmt.setString(2, START);
            stmt.setString(3, END);
            stmt.setString(4, ROOM);
            rs = stmt.executeQuery();
            while (rs.next()) {
                SECTION.setText(rs.getString("SECTION"));
                TUNITS.setText(rs.getString("TUNITS"));
                SUNITS.setText(rs.getString("SUNITS"));
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean Load_Teacher(JComboBox Teacher) {
        String sql = "select DISTINCT TEACHER from room where IS_EXIST='true' AND IS_USED_TEACHER='false' ";
        boolean result = false;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            Teacher.removeAllItems();
            Teacher.insertItemAt("--TEACHER NAME--", 0);
            Teacher.setSelectedIndex(0);
            while (rs.next()) {
                Teacher.addItem(rs.getString("TEACHER"));
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public void LoadData(JTextField SECTION, JTextField COURSE_TITLE, String DAY, String START, String END, String ROOM) {
        String sql = "select * from room where DAY=? and START=? and END=?  AND ROOM=? AND IS_EXIST='true' and IS_USED_TEACHER='false'";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, DAY);
            stmt.setString(2, START);
            stmt.setString(3, END);
            stmt.setString(4, ROOM);
            rs = stmt.executeQuery();

            if (rs.next()) {
                SECTION.setText(rs.getString("SECTION"));
                COURSE_TITLE.setText(rs.getString("COURSE_TITLE"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Load schedule in specific date selected
    public int LoadScheds(JComboBox sched, String Day, String TEACHER) {
        String sql = "select * from room where DAY=? AND IS_EXIST=? AND IS_USED_TEACHER=? and TEACHER=? ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Day);
            stmt.setString(2, "true");
            stmt.setString(3, "false");
            stmt.setString(4, TEACHER);
            rs = stmt.executeQuery();

            while (rs.next()) {
                sched.addItem(rs.getString("START") + "-" + rs.getString("END") + "-" + rs.getString("ROOM"));
                result = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Display data to jtable
    public void DisplayData(JTable table) {
        String sql = "Select * from teacher where IS_EXIST='true'";
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {
                dm.addRow(new Object[]{id++, rs.getString("TEACHER_NAME"), rs.getString("PROGRAM"), rs.getString("SECTION"), rs.getString("PSCS_CODE"), rs.getString("COURSE_TITLE"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("CONTRACT_HOURS"), rs.getString("DAYS"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("REMARKS")});

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

//THIS WILL INSERT DATA TO DATABASE
    public int InsertData(String teacher, String program, String section, String pscs_code, String course_title, String department, String sunits, String tunits, String contract_hours, String days, String start, String end, String room, String remarks) {
        String sql = "insert into teacher (TEACHER_NAME,PROGRAM,SECTION,PSCS_CODE,COURSE_TITLE,DEPARTMENT,SUNITS,TUNITS,CONTRACT_HOURS,DAYS,START,END,ROOM,REMARKS,IS_EXIST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, teacher.toUpperCase());
            stmt.setString(2, program.toUpperCase());
            stmt.setString(3, section.toUpperCase());
            stmt.setString(4, pscs_code.toUpperCase());
            stmt.setString(5, course_title.toUpperCase());
            stmt.setString(6, department.toUpperCase());
            stmt.setString(7, sunits);
            stmt.setString(8, tunits);
            stmt.setString(9, contract_hours);
            stmt.setString(10, days.toUpperCase());
            stmt.setString(11, start.toUpperCase());
            stmt.setString(12, end.toUpperCase());
            stmt.setString(13, room.toUpperCase());
            stmt.setString(14, remarks.toUpperCase());
            stmt.setString(15, "true");

            result = stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
//Update Room tabl IS_USED_TEACHER to true

    public int UpdateRoom(String day, String Is_Exist) {
        String sql = "update room set IS_USED_TEACHER =? where DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
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
            e.printStackTrace();
        }
        return result;
    }

    public int UpdateRoomBack(String day, String start, String end, String room, String Is_Exist) {
        String sql = "update room set IS_USED_TEACHER =? where DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setString(2, day);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, room);
            stmt.setString(6, "true");
            System.out.println(day);
            System.out.println(start);
            System.out.println(end);
            System.out.println(room);
            result = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Split data to 3 and assing to its variable 
    public void Splitter(String data) {
        Scanner sc = new Scanner(data);
        sc.useDelimiter("-");
        Start = sc.next();
        End = sc.next();
        Room = sc.next();
    }
//Update Data in the table 

    public int UpdateData(String teacher, String program, String section, String pscs_code, String course_title, String department, String sunits, String tunits, String contract_hours, String days, String start, String end, String room, String remarks, String days1, String start1, String end1, String room1) {
        String sql = "update  teacher set TEACHER_NAME=?,PROGRAM=?,SECTION=?,PSCS_CODE=?,COURSE_TITLE=?,DEPARTMENT=?,SUNITS=?,TUNITS=?,CONTRACT_HOURS=?,DAYS=?,START=?,END=?,ROOM=?,REMARKS=?,IS_EXIST=? WHERE DAYS=? AND START=? AND END =? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, teacher);
            stmt.setString(2, program);
            stmt.setString(3, section);
            stmt.setString(4, pscs_code);
            stmt.setString(5, course_title);
            stmt.setString(6, department);
            stmt.setString(7, sunits);
            stmt.setString(8, tunits);
            stmt.setString(9, contract_hours);
            stmt.setString(10, days);
            stmt.setString(11, Start);
            stmt.setString(12, End);
            stmt.setString(13, Room);
            stmt.setString(14, remarks);
            stmt.setString(15, "true");
            stmt.setString(16, days1);
            stmt.setString(17, start1);
            stmt.setString(18, end1);
            stmt.setString(19, room1);
            stmt.setString(20, "true");

            result = stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int BackRoom(String Day) {

        String sql = " update room set IS_USED_TEACHER=? where DAY=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";

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
            e.printStackTrace();
        }

        return result;

    }

    public int DeleteData(String Day) {
        String sql = "update teacher set IS_EXIST=? where DAYS=? AND START=? AND END=? AND ROOM=? AND IS_EXIST=?";

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
            e.printStackTrace();
        }

        return result;

    }

    public int LoadTeacherList(JComboBox combo) {
        String sql = "select DISTINCT TEACHER_NAME from teacher where IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            combo.removeAllItems();
            combo.insertItemAt("Choose Teacher", 0);
            combo.setSelectedIndex(0);
            while (rs.next()) {
                combo.addItem(rs.getString("TEACHER_NAME"));
                result = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void DisplayPrintData(JTable table, String name) {
        String sql = "Select * from teacher where TEACHER_NAME=? AND IS_EXIST='true'";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {
                dm.addRow(new Object[]{id++, rs.getString("TEACHER_NAME"), rs.getString("PROGRAM"), rs.getString("SECTION"), rs.getString("PSCS_CODE"), rs.getString("COURSE_TITLE"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("CONTRACT_HOURS"), rs.getString("DAYS"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("REMARKS")});

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean SearchData(JTable table, String search) {
        String sql = "Select * from teacher where IS_EXIST='true'";
        boolean result=false;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            DefaultTableModel dm = (DefaultTableModel) table.getModel();
            dm.setRowCount(0);
            int id = 0;
            while (rs.next()) {
                if (rs.getString("TEACHER_NAME").contains(search.toUpperCase())
                        || rs.getString("PROGRAM").contains(search.toUpperCase())
                        || rs.getString("SECTION").contains(search.toUpperCase())
                        || rs.getString("PSCS_CODE").contains(search.toUpperCase())
                        || rs.getString("COURSE_TITLE").contains(search.toUpperCase())
                        || rs.getString("DEPARTMENT").contains(search.toUpperCase())
                        || rs.getString("SUNITS").contains(search.toUpperCase())
                        || rs.getString("TUNITS").contains(search.toUpperCase())
                        || rs.getString("CONTRACT_HOURS").contains(search.toUpperCase())
                        || rs.getString("DAYS").contains(search.toUpperCase())
                        || rs.getString("START").contains(search.toUpperCase())
                        || rs.getString("END").contains(search.toUpperCase())
                        || rs.getString("ROOM").contains(search.toUpperCase())
                        || rs.getString("REMARKS").contains(search.toUpperCase())) {
                    dm.addRow(new Object[]{id++, rs.getString("TEACHER_NAME"), rs.getString("PROGRAM"), rs.getString("SECTION"), rs.getString("PSCS_CODE"), rs.getString("COURSE_TITLE"), rs.getString("DEPARTMENT"), rs.getString("SUNITS"), rs.getString("TUNITS"), rs.getString("CONTRACT_HOURS"), rs.getString("DAYS"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM"), rs.getString("REMARKS")});
                result=true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
return result;
    }

}
