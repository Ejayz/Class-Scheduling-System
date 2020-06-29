/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FRAMES.CLASSES;

/**
 *
 * @author Administrator
 */
public class RegexChecker {

    
    public String string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public boolean Checkdata(String data) {
        boolean Check = false;
        char[] Data = string.toCharArray();
        for (int i = 0; i < string.length(); i++) {
            if (data.contains(Character.toString(Data[i]))) {
                Check = true;
                break;
            } else {
                Check = false;
            }
        }
        return Check;
    }

   
}
