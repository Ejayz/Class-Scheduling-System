/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import cs_system.DB_class.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Administrator
 */
public class LoginClass {

    private String username;
    private String password;
    private Connection connection = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private boolean result;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean ValidateLogin() {

        try {

            connection = ConnectionManager.getConnection();
            stmt = connection.prepareStatement("select * from credintials where USERNAME=? && PASSWORD=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.result = true;
             
             connection.close();
                
            } else {
                this.result = false;
                connection.close();
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }

}
