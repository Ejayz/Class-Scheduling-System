/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_system.DB_class;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Administrator
 */
public class Import {

    public boolean Import_File(String path) {
        boolean result = false;
        //This will create the import command for importing backup sql file
        try {

            File imp = new File("C:\\cs_system\\bin\\import\\input.txt");
            PrintWriter writer = new PrintWriter(imp);
            writer.print("");
            writer.close();

            FileWriter fw = new FileWriter(imp, false);
            fw.write("cd C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\n"
                    + "mysqldump -uroot -p1234 cs_system> C:\\cs_system\\bin\\import\\backup\\exported.sql\n"
                    + "\n"
                    + "mysql -u root -p1234\n"
                    + "CREATE DATABASE cs_system;\n"
                    + "\n"
                    + "mysql -u root -p1234 cs_system< " + path + "\n"
                    + "\n"
                    + "exit");
            fw.flush();
            fw.close();
            if (imp.exists()) {
                result = true;
            }

        } catch (Exception e) {

        }
        return result;
    }
}
