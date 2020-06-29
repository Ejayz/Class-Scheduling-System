/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import FRAMES.ROOM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
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

    public boolean SearchRoom(JTable tb, String search) {
        String sql = "select * from room where IS_EXIST=?";
        boolean result = false;
        try {
            stmt = conn.prepareCall(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel tm = (DefaultTableModel) tb.getModel();
            int id = 0;
            tm.setRowCount(0);
            while (rs.next()) {

                if (rs.getString("SECTION").contains(search)
                        || rs.getString("COURSE_TITLE").contains(search)
                        || rs.getString("TEACHER").contains(search)
                        || rs.getString("DAY").contains(search)
                        || rs.getString("START").contains(search)
                        || rs.getString("END").contains(search)
                        || rs.getString("ROOM").contains(search)) {
                    result = true;
                    tm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("COURSE_TITLE"), rs.getString("TEACHER"), rs.getString("DAY"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM")});
                } else {
                    result = false;
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int InsertRoom(String section, String course, String teacher, String day, String start, String end, String room) {
        String sql = "INSERT INTO `cs_system`.`room` (`SECTION`, `COURSE_TITLE`, `TEACHER`, `DAY`, `START`, `END`, `ROOM`, `IS_EXIST`,IS_USED_ROOM,IS_USED_TEACHER,IS_USED_FACULTY,IS_USED_SECTION) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        int Result = 0;
        try {
            stmt = conn.prepareCall(sql);
            stmt.setString(1, section);
            stmt.setString(2, course);
            stmt.setString(3, teacher);
            stmt.setString(4, day);
            stmt.setString(5, start);
            stmt.setString(6, end);
            stmt.setString(7, room);
            stmt.setString(8, "true");
            stmt.setString(9, "false");
            stmt.setString(10, "false");
            stmt.setString(11, "false");
            stmt.setString(12, "false");

            Result = stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Result;
    }

    public boolean CheckRoom(String day, String room, String start, String end) {
        String sql = "select * from room where IS_EXIST=? AND DAY=? AND ROOM=?";
        boolean Result = false;
        try {
            stmt = conn.prepareCall(sql);
            stmt.setString(1, "true");
            stmt.setString(2, day);
            stmt.setString(3, room);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Date START = new SimpleDateFormat("HH:MM").parse(start);
                Date END = new SimpleDateFormat("HH:MM").parse(end);
                Date STARTDB = new SimpleDateFormat("HH:MM").parse(rs.getString("START"));
                Date ENDDB = new SimpleDateFormat("HH:MM").parse(rs.getString("END"));

                if (STARTDB.before(START) && START.before(ENDDB) || STARTDB.before(END) && END.before(ENDDB)||START.before(STARTDB) && STARTDB.before(END) || START.before(ENDDB) && ENDDB.before(END)) {
                    Result = true;
                    System.out.println();
                } else {
                    Result = false;
                    System.out.println(STARTDB.before(START) + "" + START.before(ENDDB));
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Result;
    }

    public void DisplayData(JTable table) {
        String sql = "Select * from room where IS_EXIST=?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "true");
            rs = stmt.executeQuery();
            DefaultTableModel tm = (DefaultTableModel) table.getModel();
            tm.setRowCount(0);
            int id = 0;
            while (rs.next()) {
                tm.addRow(new Object[]{id++, rs.getString("SECTION"), rs.getString("COURSE_TITLE"), rs.getString("TEACHER"), rs.getString("DAY"), rs.getString("START"), rs.getString("END"), rs.getString("ROOM")});
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int UpdateData(String section, String course, String Teacher, String day, String start, String end, String room, String day1, String start1, String end1, String room1) {
        String sql = "update room set SECTION=? ,COURSE_TITLE=?, TEACHER=?, DAY=? , START=? , END=? , ROOM=? , IS_EXIST=? WHERE DAY=? AND START=? AND END =? AND ROOM=? AND IS_EXIST=?";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, section);
            stmt.setString(2, course);
            stmt.setString(3, Teacher);
            stmt.setString(4, day);
            stmt.setString(5, start);
            stmt.setString(6, end);
            stmt.setString(7, room);
            stmt.setString(8, "true");
            stmt.setString(9, day1);
            stmt.setString(10, start1);
            stmt.setString(11, end1);
            stmt.setString(12, room1);
            stmt.setString(13, "true");
            result = stmt.executeUpdate();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

    public int DeleteData(String day, String start, String end, String room) {
        String sql = "update room set IS_EXIST=? WHERE DAY=? AND START=? AND END =? AND ROOM=?";
        int result = 0;
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "false");
            stmt.setString(2, day);
            stmt.setString(3, start);
            stmt.setString(4, end);
            stmt.setString(5, room);
            result = stmt.executeUpdate();

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

    public boolean ValidateDelete(String password) {
        boolean Result = false;
        String sql = "select * from credintials ";
        try {
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("PASSWORD").equals(password)) {
                    Result = true;
                }

            } else {
                Result = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "Delete");
        }
        return Result;
    }

    //end of line
}
