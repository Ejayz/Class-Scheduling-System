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
public class StringFormater {
    
    String Hours;
    String Minutes;
    String Period;

  String FinalTime;
  public String FinalTime(){
      this.FinalTime=Hours+":"+Minutes+" "+Period;
      return FinalTime;
  }

    public void setHours(String Hours) {
        this.Hours = Hours;
    }

    public void setMinutes(String Minutes) {
        this.Minutes = Minutes;
    }
    
    public void setPeriod(String Period){
        this.Period=Period;
    }
    
}
