/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FRAMES.PrintFrame;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class FileMaker {

    public int FileMake(String path) {
       //istantiate file path throwed from your process at frame
        File file = new File(path.replace("\\", "/").replace(":", ":/"));
// instantiate result to 0 for false
        int result = 0;
        try {
            //condition to create file will return 1 if true
            if (file.createNewFile()) {
                result = 1;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
           // print Exception error if occured
            e.printStackTrace();

        }
// return result
        return result;
    }
}
