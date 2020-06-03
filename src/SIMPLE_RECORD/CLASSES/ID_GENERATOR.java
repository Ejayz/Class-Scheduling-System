/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIMPLE_RECORD.CLASSES;

/**
 *
 * @author Administrator
 */
public class ID_GENERATOR {
    
    
 private String Chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
 private String Id="";
 


 
 
 
 public String GenerateId( ){
     char[] characters=Chars.toCharArray();
    
     for(int i=0;i<=10;i++){
         int generator=(int)(Math.random()*((36-1)+1))+1;
       Id=Id+""+characters[generator];
       
     }
     
     return Id;
 }
 
}
