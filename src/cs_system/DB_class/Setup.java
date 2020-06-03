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

    Connection conn = ConnectionManager.getConnection();
    PreparedStatement stmt;
    ResultSet rs;

    public int IsAccept() {
        String sql = "update setup set IS_SETUP='true', IS_ACCEPT='true' where ID='1' ";
        int result = 0;
        try {
            stmt = conn.prepareStatement(sql);
            result = stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

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
