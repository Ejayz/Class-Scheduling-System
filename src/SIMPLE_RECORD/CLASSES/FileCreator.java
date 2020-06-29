/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIMPLE_RECORD.CLASSES;

import java.io.File;

/**
 *
 * @author Administrator
 */
public class FileCreator {

    public void CreateDir() {
        
        try {
            File file = new File("C://cs_system/");
            if (file.mkdir()) {
                System.out.println("folder Created");
            }
        else  {
                System.out.println("Folder cannot be created");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       
    }
    
 public boolean CreateFile(String name) {
        boolean res = false;
        try {
            File file = new File("C://cs_system/"+name);
            if (file.createNewFile()) {
                res = true;
            }
            {
                res = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }
    public boolean DirChecker(){
     
        boolean res=false;
      try{
        File file = new File("C://cs_system/");
       if(!file.exists()){
        res=true;
       }
       else{
           res=false;
       }
      }catch(Exception e){
          System.out.println(e);
      }
        return res;
    }
    
}
